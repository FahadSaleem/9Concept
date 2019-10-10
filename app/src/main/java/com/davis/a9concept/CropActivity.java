package com.davis.a9concept;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

public class CropActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        String size = getIntent().getStringExtra("size");
        String uri = getIntent().getStringExtra("uri");
        Toast.makeText(getApplicationContext(), size + " " + uri, Toast.LENGTH_SHORT).show();
        final ImageView backInventory = findViewById(R.id.backInventory);
        backInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (size.equals("1")) {
            CropImage.activity(Uri.parse(uri))
                    .setAspectRatio(3, 2)
                    .start(this);
        } else if (size.equals("2")) {
            CropImage.activity(Uri.parse(uri))
                    .setAspectRatio(2, 1)
                    .start(this);
        } else if (size.equals("3")) {
            CropImage.activity(Uri.parse(uri))
                    .setAspectRatio(1, 1)
                    .start(this);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                ImageView pv = findViewById(R.id.previewIv);
                pv.setImageURI(resultUri);
                getDropboxIMGSize(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void getDropboxIMGSize(Uri uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        Log.d("croppedImage", "height: " + imageHeight + ", width: " + imageWidth);

    }
}
