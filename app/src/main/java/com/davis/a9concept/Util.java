package com.davis.a9concept;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class Util {

    private Context mContext;
    private ContentResolver mContentResolver;

    public Util(Context context) {
        mContext = context;
        mContentResolver = context.getContentResolver();
    }

    public List<String> getImages(String Album) {
        List<String> list = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.DISPLAY_NAME
        };
        String selection = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + " = ?";
        String[] selectionArgs = new String[]{Album};
        if (Album.equals("All")) {
            selection = null;
            selectionArgs = null;
        }
        Cursor cur = mContentResolver
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, selection, selectionArgs,
                        MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
        assert cur != null;
        if (cur.moveToFirst()) {
            String data;
            int dataColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);
            do {
                data = cur.getString(dataColumn);
                list.add(data);
            } while (cur.moveToNext());
            cur.close();
        }
        return list;
    }

    public List<String> getAlbums() {
        Uri storageName;
        String columnName;
        List<String> list = new ArrayList<>();
        list.add("All");

        columnName = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;
        storageName = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = new String[]{"DISTINCT " + MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME};
        Cursor cur = mContentResolver.query(storageName, projection, null, null, null);

        assert cur != null;
        if (cur.moveToFirst()) {
            String bucket;
            int bucketColumn = cur.getColumnIndex(columnName);
            do {
                bucket = cur.getString(bucketColumn);
                list.add(bucket);
            } while (cur.moveToNext());
        }
        cur.close();
        return list;
    }
}
