package com.davis.a9concept;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    ArrayList<String> selectedImages = new ArrayList<>();
    // data is passed into the constructor
    RecyclerViewAdapter(Context context, List<String> data, ArrayList<String> selectedImages) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        for (String path: selectedImages){
            if (data.contains(path)){
                this.selectedImages.add(path);
            }
        }
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        ImageView iv = view.findViewById(R.id.imageView);
        iv.getLayoutParams().height = (width/3)-30;
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(mData.get(position))
                .centerCrop()
                .into(holder.mImageView);
        if (selectedImages.contains(getItem(position))){
                holder.containerSlide.setBackgroundColor(Color.parseColor("#ABD81B60"));
        }
        else {
            holder.containerSlide.setBackgroundColor(Color.TRANSPARENT);
        }

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, String path, OnAdapterImageAdded listener);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;
        FrameLayout containerSlide;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            containerSlide = itemView.findViewById(R.id.container_slide);
            android.view.ViewGroup.LayoutParams mParams = mImageView.getLayoutParams();

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getItem(getAdapterPosition()), new OnAdapterImageAdded() {
                @Override
                public void imageAdded(String path) {
                    if (selectedImages.contains(path)){
                        selectedImages.remove(path);
                        notifyDataSetChanged();
                    }
                    else {
                        if (selectedImages.size()==3){
                           // Toast.makeText(mContext,"You can select a maximum of three pictures", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        selectedImages.add(path);
                        notifyDataSetChanged();
                    }
                }
            });


        }
    }

    public interface OnAdapterImageAdded{
        void imageAdded(String path);
    }
}
