package com.davis.a9concept;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    DynamicTabsActivity.OnImageSelected listener;
    ArrayList<String> selectedImages;
    private List<String> albums;

    public SectionsPagerAdapter(FragmentManager fm, Context context, DynamicTabsActivity.OnImageSelected listener, ArrayList<String> selectedImages) {
        super(fm);
        Util util = new Util(context);
        albums = util.getAlbums();
        this.listener = listener;
        this.selectedImages = selectedImages;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a StylePhotoFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position, listener, selectedImages);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.albums.get(position);
    }

    @Override
    public int getCount() {
        return this.albums.size();
    }
}