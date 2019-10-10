package com.davis.a9concept;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

public class SharedData {
    public static class ResizedImages {
        public static ArrayList<StylePhotoFragment.MyImage> resizedImages = null;

        public static void init(int size) {
            resizedImages = new ArrayList<>(size);
        }

        public static void init(ArrayList<String> selectedImages) {
            resizedImages = new ArrayList<>();
            for (String s : selectedImages) {
                resizedImages.add(
                        new StylePhotoFragment.MyImage(
                                new StylePhotoFragment.Size("1", 3, 2),
                                Uri.fromFile(new File(s)),
                                s));
            }
        }


        public static void setResizedImages(int index, StylePhotoFragment.MyImage image) {
            resizedImages.set(index, image);
        }

        public static StylePhotoFragment.MyImage getResizedImages(int index) {
            return resizedImages.get(index);
        }
    }
}
