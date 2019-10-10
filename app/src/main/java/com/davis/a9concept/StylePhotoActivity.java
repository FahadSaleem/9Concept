package com.davis.a9concept;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class StylePhotoActivity extends AppCompatActivity {

    private ArrayList<String> selectedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_photo);
        ViewPager viewPager = findViewById(R.id.style_photo_view_pager);
        selectedImages = getIntent().getStringArrayListExtra("selectedImages");
        StylePhotoPagerAdapter adapter = new StylePhotoPagerAdapter(getSupportFragmentManager(), selectedImages);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class StylePhotoPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<String> mSelectedImages;

        StylePhotoPagerAdapter(FragmentManager fm, ArrayList<String> selectedImages) {
            super(fm);
            mSelectedImages = selectedImages;
            SharedData.ResizedImages.init(mSelectedImages);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a StylePhotoFragment (defined as a static inner class below).
            return StylePhotoFragment.newInstance(position, mSelectedImages);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mSelectedImages.get(position);
        }

        @Override
        public int getCount() {
            return mSelectedImages.size();
        }
    }
}