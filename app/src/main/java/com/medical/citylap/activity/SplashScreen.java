package com.medical.citylap.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.medical.citylap.R;
import com.medical.citylap.RetrofitClint;
import com.medical.citylap.modles.Loginmodle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 3000;


    public static String deviceToken ;
    public static String token_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                         deviceToken = task.getResult();
                        // Log and toast
                    }
                });

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.side_slide);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(2000);


        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.side_slide);
        animation2.setInterpolator(new LinearInterpolator());
        animation2.setRepeatCount(Animation.INFINITE);
        animation2.setDuration(2000);

        final TextView textView=findViewById(R.id.textView3_spalsh);
        final ImageView splash = findViewById(R.id.imagelogogsplash);
        splash.startAnimation(animation);
        textView.startAnimation(animation2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = SplashScreen.this.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                final String retrivedToken  = preferences.getString("phonenumberuser",null);
                //second parameter default value.
                if(retrivedToken !=null) {
                    RetrofitClint.getInstance().userlogin(retrivedToken,deviceToken).enqueue(new Callback<Loginmodle>() {
                        @Override
                        public void onResponse(Call<Loginmodle> call, Response<Loginmodle> response) {
                            if (response.isSuccessful())
                            //save token in application
                            {
                                Log.d("TAG", "onResponse: " + response.body().getData().getToken());
                                token_user = response.body().getData().getToken();

                            }
                        }
                        @Override
                        public void onFailure(Call<Loginmodle> call, Throwable t) {

                        }
                    });
                }

                startActivity(new Intent(SplashScreen.this, Home.class));
                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }

}