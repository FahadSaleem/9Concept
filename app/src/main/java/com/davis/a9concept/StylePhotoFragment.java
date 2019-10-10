package com.davis.a9concept;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class StylePhotoFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String SELECTED_IMAGES = "selectedImages";
    public Size mSelectedSize;
    private StylePhotoViewModel stylePhotoViewModel;
    private LinearLayout mLinearLayout;
    private final int IMAGE_BASE_SIZE = 500;
    private String mImagePath;

    public static StylePhotoFragment newInstance(int index, ArrayList<String> selectedImages) {
        StylePhotoFragment fragment = new StylePhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putStringArrayList(SELECTED_IMAGES, selectedImages);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stylePhotoViewModel = ViewModelProviders.of(this).get(StylePhotoViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            ArrayList<String> selectedImages;
            selectedImages = getArguments().getStringArrayList(SELECTED_IMAGES);
            stylePhotoViewModel.init(selectedImages);
        }
        stylePhotoViewModel.setIndex(index);
    }

    ImageView imageView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_style_photo, container, false);
        mLinearLayout = root.findViewById(R.id.style_photo_linear_layout);


        imageView = root.findViewById(R.id.style_photo_frag_img);
        stylePhotoViewModel.getSizeList().observe(this, new Observer<ArrayList<Size>>() {
            @Override
            public void onChanged(ArrayList<Size> sizes) {
                mSelectedSize = sizes.get(0);
                Log.d("size", mSelectedSize.id + " " + mSelectedSize.value);
                for (final Size s : sizes) {
                    mLinearLayout.addView(generateSizeView(s));
                }
            }
        });
        stylePhotoViewModel.getImage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String s) {
                mImagePath = s;
                loadImage();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (mSelectedSize.id.equals("1")) {
                            CropImage.activity(Uri.fromFile(new File(s)))
                                    .setAspectRatio(3, 2)
                                    .start(getContext(), StylePhotoFragment.this);
                        } else if (mSelectedSize.id.equals("2")) {
                            CropImage.activity(Uri.fromFile(new File(s)))
                                    .setAspectRatio(2, 1)
                                    .start(getContext(), StylePhotoFragment.this);
                        } else if (mSelectedSize.id.equals("3")) {
                            CropImage.activity(Uri.fromFile(new File(s)))
                                    .setAspectRatio(1, 1)
                                    .start(getContext(), StylePhotoFragment.this);
                        }
                    }
                });
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private void loadImage() {
        Glide.with(Objects.requireNonNull(getContext()))
                .load(mImagePath)
                .override(IMAGE_BASE_SIZE * mSelectedSize.width,
                        IMAGE_BASE_SIZE * mSelectedSize.height)
                .centerCrop()
                .into(imageView);
    }


    private View generateSizeView(final Size s) {
        final LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        final LinearLayout.LayoutParams params1 =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(getContext());
        textView.setText(s.value);
        textView.setTextSize(36);
        textView.setPadding(25, 25, 25, 25);
        params.setMargins(10, 0, 10, 0);
        params1.setMargins(5, 5, 5, 5);
        textView.setLayoutParams(params);

        final FrameLayout frameLayout = new FrameLayout(Objects.requireNonNull(getContext()));

        if (s.id.equals(mSelectedSize.id)) {
            frameLayout.setBackgroundColor(Color.parseColor("#ABD81B60"));
            textView.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            frameLayout.setBackgroundColor(Color.TRANSPARENT);
            textView.setTextColor(Color.parseColor("#FF000000"));
        }

        frameLayout.addView(textView);
        frameLayout.setLayoutParams(params1);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedSize = s;
                FrameLayout layout;
                ViewGroup row = (ViewGroup) view.getParent();
                for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
                    View view1 = row.getChildAt(itemPos);
                    if (view1 instanceof FrameLayout) {
                        layout = (FrameLayout) view1;
                        TextView textView1 = (TextView) layout.getChildAt(0);
                        if (textView1.getText().equals(mSelectedSize.value)) {
                            layout.setBackgroundColor(Color.parseColor("#ABD81B60"));
                            textView1.setTextColor(Color.parseColor("#FFFFFFFF"));
                        } else {
                            layout.setBackgroundColor(Color.TRANSPARENT);
                            textView1.setTextColor(Color.parseColor("#FF000000"));
                        }
                        loadImage();
                    }
                }
            }
        });
        return frameLayout;
    }

    public static class Size {
        String id;
        String value;
        int height;
        int width;

        Size(String id, int width, int height) {
            this.id = id;
            this.height = height;
            this.width = width;
            this.value = width + ":" + height;
        }
    }

    public static class StylePhotoViewModel extends ViewModel {
        private ArrayList<String> mSelectedImages;
        private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
        private LiveData<ArrayList<Size>> mSizeList = Transformations.map(mIndex, new Function<Integer, ArrayList<Size>>() {
            @Override
            public ArrayList<Size> apply(Integer input) {

                ArrayList list = new ArrayList<Size>() {{
                    add(new Size("1", 3, 2));
                    add(new Size("2", 2, 1));
                    add(new Size("3", 1, 1));
                }};
                return list;
            }
        });
        private LiveData<String> mImage = Transformations.map(mIndex, new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                return mSelectedImages.get(input);
            }
        });

        public void init(ArrayList<String> data) {
            mSelectedImages = data;
        }

        public void setIndex(int index) {
            mIndex.setValue(index);
        }

        public LiveData<ArrayList<Size>> getSizeList() {
            return mSizeList;
        }

        public LiveData<String> getImage() {
            return mImage;
        }
    }
}