package com.medical.citylap.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.medical.citylap.R;
import com.medical.citylap.base.BaseFragment;
import com.medical.citylap.fragemnt.Fragment_map;
import com.medical.citylap.fragemnt.HomeFragment;
import com.medical.citylap.fragemnt.LoginFragment;
import com.medical.citylap.fragemnt.MapsFragment;
import com.medical.citylap.fragemnt.Profilefragment;

public class Home extends AppCompatActivity {
    private static final int TIME_INTERVAL = 2000; //
    private long mBackPressed;
    Fragment fragmenthome,profile,login;

    boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:


                    loadFragment(fragmenthome , "home");
                    return true;
                case R.id.navigation_location:

                    startActivity(new Intent(Home.this, Mooglmap.class));
                    //fragment=new Fragment_map();
                    // loadFragment(fragment);
                    return true;
                case R.id.navigation_chat:

                    ////start whats app
                    whatsapp();

                    return true;
                case R.id.navigation_profil:

                    if (checkuserlogin() == null) {
                        //open login screen


                        loadFragment(login , "login");
                    } else {
                        loadFragment(profile , "profile");

                    }

                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmenthome = new HomeFragment();
        profile=new Profilefragment();
        login=new LoginFragment();
        loadFragment(fragmenthome , "home");
        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }



    ///////////////////////////

    ////////////////

    private boolean loadFragment(Fragment fragment , String tag) {
        //switching fragment
        if (fragment != null && !fragment.isVisible()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public String checkuserlogin() {
        String id = null;
        SharedPreferences preferences = this.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String retrivedToken = preferences.getString("phonenumberuser", null);//second parameter default value.
        return retrivedToken;
    }
    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            System.exit(0);
            finish();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Please click back again to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }
    public void whatsapp() {
        String contact = "+02 "+"01062436363"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }
}


