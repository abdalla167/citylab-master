package com.medical.citylap.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medical.citylap.Adapter.AdapterMidecalInformation;
import com.medical.citylap.Adapter.AdapterResult;
import com.medical.citylap.R;
import com.medical.citylap.modles.MedicalInformationModel;

import java.util.ArrayList;

public class MidecalInformation extends AppCompatActivity {
    AdapterMidecalInformation adapterMidecalInformation;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    MedicalInformationModel model=new MedicalInformationModel();
    ArrayList<MedicalInformationModel> modlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midecal_information);
         progressBar=findViewById(R.id.prograbarmedical);
        getdatamedical();
        mRecyclerView=findViewById(R.id.medcal_information_recycler);





    }

    public void getdatamedical() {


        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("medical");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {

                         model = npsnapshot.getValue(MedicalInformationModel.class);
                        //
                        modlist.add(model);
                    }
                    progressBar.setVisibility(View.GONE);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(MidecalInformation.this));
                    adapterMidecalInformation=new AdapterMidecalInformation(MidecalInformation.this,modlist);
                    mRecyclerView.setAdapter(adapterMidecalInformation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MidecalInformation.this, ""+error, Toast.LENGTH_SHORT).show();
            }


        });
    }


    }


