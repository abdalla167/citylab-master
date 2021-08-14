package com.medical.citylap.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.medical.citylap.R;
import com.medical.citylap.activity.ShowMedicalInformation;
import com.medical.citylap.modles.MedicalInformationModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterMidecalInformation    extends RecyclerView.Adapter<AdapterMidecalInformation.ViewHolder2> {
    Context mContext;
    ArrayList<MedicalInformationModel>medicalInformationModelArrayList=new ArrayList<>();

    @NonNull
    @Override
    public AdapterMidecalInformation.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medical_information,parent,false);
        return new AdapterMidecalInformation.ViewHolder2(view);
    }

    public AdapterMidecalInformation( Context mContext,ArrayList<MedicalInformationModel> listOffer) {

        this.mContext = mContext;
        this.medicalInformationModelArrayList=listOffer;
    }

    public AdapterMidecalInformation() {
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMidecalInformation.ViewHolder2 holder, int position) {

holder.hidlin.setText(medicalInformationModelArrayList.get(position).getHide());

    }

    @Override
    public int getItemCount() {
        if(medicalInformationModelArrayList.size()>0)
            return medicalInformationModelArrayList.size();
        else
            return 0;
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder
    {
        ConstraintLayout linearLayout;

      TextView hidlin;


        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
          hidlin=itemView.findViewById(R.id.hideline_medical_information);
          linearLayout=itemView.findViewById(R.id.liner_medaicl_information);
          linearLayout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
              MedicalInformationModel medicalInformationModel=new MedicalInformationModel();
              medicalInformationModel=medicalInformationModelArrayList.get(getAdapterPosition());
                  Intent intent=new Intent(mContext, ShowMedicalInformation.class);
                  intent.putExtra("object_medical",medicalInformationModel);
                  mContext.startActivity(intent);
              }
          });
        }
    }


}
