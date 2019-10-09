package com.davis.a9concept;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.io.File;
import java.util.ArrayList;

public class LandingActivity extends AppCompatActivity {
    TextView title;
    private ArrayList<String> fileList = new ArrayList<String>();
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    int                   PERMISSION_ALL = 1;

    int[] images = {R.drawable.dummy_1, R.drawable.dummy_2, R.drawable.dummy_3};


    int image_counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        title = findViewById(R.id.title);
        Spannable wordtoSpan = new SpannableString("9Concept brings your \ncherished memories at your doorstep");

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        else {
           // doStuff();
        }

        wordtoSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent)), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        title.setText(wordtoSpan);

        TextView go = findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, SelectSizeActivity.class);
                startActivity(intent);
            }
        });


        final ImageView forward,back,product;
        product = findViewById(R.id.product_view);
        back = findViewById(R.id.back);
        forward = findViewById(R.id.forward);
        product.setImageResource(images[image_counter]);

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image_counter==images.length-1){
                    return;
                }
                product.setImageResource(images[++image_counter]);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image_counter==0){
                    return;
                }


                product.setImageResource(images[--image_counter]);
            }
        });
    }


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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //  doStuff();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                finish();
            }

        }

    }


}
