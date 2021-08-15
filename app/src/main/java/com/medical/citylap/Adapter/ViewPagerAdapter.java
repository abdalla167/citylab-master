package com.medical.citylap.Adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.medical.citylap.R;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static android.os.Environment.DIRECTORY_PICTURES;

public class ViewPagerAdapter extends PagerAdapter {

    // Context object
    Context context;

    // Array of images
    List<String>  images;

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
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.item_view_pager, container, false);

        // referencing the image view from the item.xml file
        ImageView imageView =  itemView.findViewById(R.id.imageViewMain);


        if(stat==1) {
            // setting the image in the imageView
            Glide.with(context).load("http://" + images.get(position)).into(imageView);
            // Adding the View
            Objects.requireNonNull(container).addView(itemView);

        }
        if(stat==0)
        {
            File file=new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)+"/"+images.get( position));
            Uri imgUri=Uri.fromFile(file);
        imageView.setImageURI(null);
          imageView.setImageURI(imgUri);
            Objects.requireNonNull(container).addView(itemView);

        }


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout) object);
    }
}