package com.medical.citylap.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.medical.citylap.R;

public class AboutUS extends AppCompatActivity {
public ImageView buttonback1;
public ImageView imageView_facebook,imageView_what;
    public static String FACEBOOK_URL = "https://www.facebook.com/citylabeg";
    public static String FACEBOOK_PAGE_ID = "citylabeg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_u_s);
        inti();
        buttonback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUS.this,Home.class));
            }
        });
imageView_facebook.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(AboutUS.this);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }
});
imageView_what.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String contact = "+02 "+"01062436363"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
});

    }
    public void inti()
    {
        buttonback1=findViewById(R.id.backfrom_aboutus_to_home);
        imageView_facebook=findViewById(R.id.facbooklink);
        imageView_what=findViewById(R.id.whatapplink);

    }
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

}