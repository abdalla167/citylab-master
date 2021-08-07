package com.medical.citylap.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.medical.citylap.R;
import com.medical.citylap.modles.Datum;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AdapteronlyImage extends RecyclerView.Adapter<AdapteronlyImage.ViewHolder2> {


    List<String> listoffer=new ArrayList<>();
    private Context mContext;
    Uri uri;
    int stat;
    public void setlist(List<String> listOffer){

        this.listoffer=listOffer;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdapteronlyImage.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemimageresult,parent,false);
        return new AdapteronlyImage.ViewHolder2(view);
    }

    public AdapteronlyImage( Context mContext,int stat) {
this.stat=stat;
        this.mContext = mContext;
    }

    public AdapteronlyImage() {
    }

    @Override
    public void onBindViewHolder(@NonNull AdapteronlyImage.ViewHolder2 holder, int position) {


if (stat==0)
{

}
else {
    Glide.with(mContext).load("http://" + listoffer.get(position))
            .into(holder.imageView);
    holder.progressBar.setVisibility(View.GONE);
}

    }

    @Override
    public int getItemCount() {
        if(listoffer.size()>0)
            return listoffer.size();
        else
            return 0;
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder
    {

        ProgressBar progressBar;
        ImageView imageView;
        ViewPager viewPager;
        ViewPagerAdapter mViewPagerAdapter;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
         imageView=itemView.findViewById(R.id.image_inside_result_id);

            progressBar=itemView.findViewById(R.id.progress_inside_rec);
         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final Dialog dialog = new Dialog(mContext);
                 dialog.setContentView(R.layout.custom_daliog);

                 // Include dialog.xml file
                 dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                 dialog.show();

//                 ImageView imaged=dialog.findViewById(R.id.image_in_dalog);
//                 Glide.with(mContext).load("http://"+listoffer.get(getAdapterPosition()))
//                         .into(imaged);

                 viewPager =dialog.findViewById(R.id.viewPagerMain);

                 // Initializing the ViewPager Object


                 // Initializing the ViewPagerAdapter
                 mViewPagerAdapter = new ViewPagerAdapter(mContext, listoffer);

                 // Adding the Adapter to the ViewPager
                 viewPager.setAdapter(mViewPagerAdapter);

             }
         });
        }
    }


}
