package com.davis.a9concept;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private RecyclerViewAdapter adapter;



    ArrayList<String> selectedImages = new ArrayList<>();
    DynamicTabsActivity.OnImageSelected listener;

    public static PlaceholderFragment newInstance(int index, DynamicTabsActivity.OnImageSelected listener, ArrayList<String> selectedImages) {
        PlaceholderFragment fragment = new PlaceholderFragment(listener, selectedImages);
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public PlaceholderFragment(DynamicTabsActivity.OnImageSelected listener, ArrayList<String> selectedImages){
        this.listener = listener;
        this.selectedImages = selectedImages;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.init(getContext());
        int index = 0;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dynamic_tabs, container, false);

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));


        final Context context = getContext();
        final RecyclerViewAdapter.ItemClickListener listener = this;
        pageViewModel.getText().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> images) {
                adapter = new RecyclerViewAdapter(context, images,selectedImages);
                adapter.setClickListener(listener);
                recyclerView.setAdapter(adapter);
            }
        });




        return root;
    }

    @Override
    public void onItemClick(View view, String position, RecyclerViewAdapter.OnAdapterImageAdded listenerAdapter) {
        listener.onSelected(String.valueOf(position),listenerAdapter);
    }
}