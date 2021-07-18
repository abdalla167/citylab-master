package com.medical.citylap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.medical.citylap.R;
import com.medical.citylap.helperfunction.Utile;

public class Blood_activity extends AppCompatActivity {
    ImageView backt_to_home;
    EditText Age,blood,wight;
    TextView information;
    Button show_information;
    CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blood_);
        inti();
        backt_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Blood_activity.this,Home.class));
                finish();
            }
        });
        show_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(Age.getText().toString().equals(""))
                 {
                     Age.setError("!");
                     Toast.makeText(Blood_activity.this, "برجاء ادخال جميع البيانات", Toast.LENGTH_SHORT).show();

                 }
                 if(wight.getText().toString().equals(""))
                 {
                     wight.setError("!");
                     Toast.makeText(Blood_activity.this, "برجاء ادخال جميع البيانات", Toast.LENGTH_SHORT).show();

                 }
                 if(blood.getText().toString().equals(""))
                 {
                     blood. setError("!");
                 }

             if(!Age.getText().toString().equals("")&&! blood.getText().toString().equals("")&& !wight.getText() .toString().equals(""))
            {
                Utile.hideKeyboard(Blood_activity.this);

                cardView.setVisibility(View.VISIBLE);
                information.setText("enta tmam awee we lazem take care your self");
            }
            }
        });
    }

    public void inti()
    {
        backt_to_home=findViewById(R.id.backfrom_blood_to_home);
        Age=findViewById(R.id.age_in_blood_leve);
        blood=findViewById(R.id.level_in_blood_leve);
        wight=findViewById(R.id.wight_in_blood_leve);
        information=findViewById(R.id.information_in_blood_leve);
        show_information=findViewById(R.id.confer_blood);
        cardView=findViewById(R.id.cardView_blood);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Blood_activity.this,Home.class));
        finish();
    }
}