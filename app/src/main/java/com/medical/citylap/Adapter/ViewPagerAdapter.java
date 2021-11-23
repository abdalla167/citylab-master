package com.medical.citylap.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.medical.citylap.R;
import com.ortiz.touchview.TouchImageView;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static android.os.Environment.DIRECTORY_PICTURES;

public class ViewPagerAdapter extends PagerAdapter {

    // Context object
    Context context;

    // Array of images
    List<String>  images;
ProgressBar progressBar;
    // Layout Inflater
    LayoutInflater mLayoutInflater;

int stat;
    // Viewpager Constructor
    public ViewPagerAdapter(Context context, List<String> images,int stat) {
        this.context = context;
        this.images = images;
        this.stat=stat;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // return the number of images
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ConstraintLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.item_view_pager, container, false);

        // referencing the image view from the item.xml file
        TouchImageView imageView =  itemView.findViewById(R.id.imageViewMain);
        progressBar =itemView.findViewById(R.id.progressimageresultviewpager);

        if(stat==1) {
            // setting the image in the imageView
            Glide.with(context).load("http://" + images.get(position)).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);

                    return false;

                }
            }).into(imageView);
            // Adding the View
            (container).addView(itemView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView.setRotation(imageView.getRotation() + 90);
                }
            });

        }
        if(stat==0)
        {
            File file=new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)+"/"+images.get( position));
            Uri imgUri=Uri.fromFile(file);
        imageView.setImageURI(null);
          imageView.setImageURI(imgUri);
            Objects.requireNonNull(container).addView(itemView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView.setRotation(imageView.getRotation() + 90);
                }
            });

        }


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((ConstraintLayout) object);
    }
}