package com.davis.a9concept;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SelectSizeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);


        RelativeLayout frame_one = findViewById(R.id.frame_rl_one);
        RelativeLayout frame_two = findViewById(R.id.frame_rl_two);
        RelativeLayout frame_three = findViewById(R.id.frame_rl_three);
        final ImageView backInventory = findViewById(R.id.backInventory);
        backInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        frame_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectSizeActivity.this, DynamicTabsActivity.class);
                intent.putExtra("size", "1");
                startActivity(intent);
            }
        });
        frame_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectSizeActivity.this, DynamicTabsActivity.class);
                intent.putExtra("size", "2");
                startActivity(intent);
            }
        });
        frame_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectSizeActivity.this, DynamicTabsActivity.class);
                intent.putExtra("size", "3");
                startActivity(intent);
            }
        });
    }


    private void updateSizeInfo() {

        RelativeLayout rl_header = findViewById(R.id.add_shop_header);


        RelativeLayout frame_one = findViewById(R.id.frame_rl_one);
        RelativeLayout frame_two = findViewById(R.id.frame_rl_two);
        RelativeLayout frame_three = findViewById(R.id.frame_rl_three);


        frame_one.getLayoutParams().width = (int) (rl_header.getWidth() / 1.3);
        frame_two.getLayoutParams().width = (int) (rl_header.getWidth() / 1.3);
        frame_three.getLayoutParams().width = (int) (rl_header.getWidth() / 1.3);

        int height_1 = frame_one.getLayoutParams().width;
        int height_2 = frame_two.getLayoutParams().width;
        int height_3 = frame_three.getLayoutParams().width;
        Log.d("width", "set width" + frame_two.getLayoutParams().width);

        frame_one.requestLayout();
        frame_two.requestLayout();
        frame_three.requestLayout();

        frame_one.getLayoutParams().height = (int) (height_1 / 1.5);
        frame_two.getLayoutParams().height = height_2 / 2;
        Log.d("width", "get width" + frame_two.getWidth());
        frame_three.getLayoutParams().height = height_3;

        frame_one.requestLayout();
        frame_two.requestLayout();
        frame_three.requestLayout();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        updateSizeInfo();
    }
}
