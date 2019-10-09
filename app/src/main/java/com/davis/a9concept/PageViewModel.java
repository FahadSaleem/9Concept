package com.davis.a9concept;

import android.content.Context;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private List<String> mAlbums;
    private Util util;
    private LiveData<List<String>> mText = Transformations.map(mIndex, new Function<Integer, List<String>>() {
        @Override
        public List<String> apply(Integer input) {
            List<String> images = util.getImages(mAlbums.get(input));
            return images;
        }
    });

    public void init(Context context) {
        util = new Util(context);
        mAlbums = util.getAlbums();
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<List<String>> getText() {
        return mText;
    }
}