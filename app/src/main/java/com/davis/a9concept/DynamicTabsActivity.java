package com.davis.a9concept;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class DynamicTabsActivity extends AppCompatActivity {
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    int PERMISSION_ALL = 1;
    TextView continueImages;
    ArrayList<String> selectedImages = new ArrayList<>();

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_tabs);
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {
            // doStuff();
        }

        continueImages = findViewById(R.id.go_selected_images);


        SectionsPagerAdapter adapter = new SectionsPagerAdapter(
                getSupportFragmentManager(), getApplicationContext(), new OnImageSelected() {
            @Override
            public void onSelected(String path, RecyclerViewAdapter.OnAdapterImageAdded listener) {
                if (selectedImages.contains(String.valueOf(path))) {
                    selectedImages.remove(String.valueOf(path));
                    listener.imageAdded(path);
                    if (selectedImages.size() == 0) {
                        continueImages.setBackgroundResource(R.drawable.continue_greyed);
                        continueImages.setText("Select some photos to continue");
                    } else if (selectedImages.size() == 1) {
                        continueImages.setText("Continue with " + selectedImages.size() + " photo");
                    } else {
                        continueImages.setText("Continue with " + selectedImages.size() + " photos");
                    }
                } else {
                    if (selectedImages.size() == 3) {
                        Toast.makeText(getApplicationContext(), "You can select a maximum of three pictures", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    selectedImages.add(String.valueOf(path));
                    listener.imageAdded(path);
                    if (selectedImages.size() == 1) {
                        continueImages.setText("Continue with " + selectedImages.size() + " photo");
                    } else {
                        continueImages.setText("Continue with " + selectedImages.size() + " photos");
                    }
                    continueImages.setBackgroundResource(R.drawable.continue_btn_success);
                }
            }
        }, selectedImages);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        if (tabs.getTabCount() == 2) {
            tabs.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        continueImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StylePhotoActivity.class);
                intent.putExtra("selectedImages", selectedImages);
                startActivity(intent);
            }
        });
    }

    public interface OnImageSelected {
        void onSelected(String path, RecyclerViewAdapter.OnAdapterImageAdded listener);
    }

}