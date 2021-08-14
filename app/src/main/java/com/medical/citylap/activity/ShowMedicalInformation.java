package com.medical.citylap.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.medical.citylap.R;
import com.medical.citylap.modles.MedicalInformationModel;

public class ShowMedicalInformation extends AppCompatActivity {
ImageView imageView;
TextView hid,cintent;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medical_information);
        imageView=findViewById(R.id.image_show_medical);
        hid=findViewById(R.id.hidlin_show);
        progressBar=findViewById(R.id.presser_bar_image_show);
        cintent=findViewById(R.id.content_show);
        Intent i = getIntent();
        MedicalInformationModel medicalInformationModel = (MedicalInformationModel)i.getSerializableExtra("object_medical");
        Glide.with(this).load(medicalInformationModel.getImage().toString()) .listener(new RequestListener<Drawable>() {
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
        })
                .into(imageView);
        Log.d("TAG", "onCreate: "+(medicalInformationModel.getImage().toString()));
        hid.setText(medicalInformationModel.getHide());
        cintent.setText(medicalInformationModel.getContent());


    }
}