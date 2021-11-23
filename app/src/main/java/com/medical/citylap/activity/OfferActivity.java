package com.medical.citylap.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.medical.citylap.Adapter.OfferAdapter;
import com.medical.citylap.R;
import com.medical.citylap.fragemnt.HomeFragment;
import com.medical.citylap.helperfunction.ConnectionReceiver;
import com.medical.citylap.helperfunction.Connectivity;
import com.medical.citylap.modles.AllOffer;
import com.medical.citylap.modles.Datum;
import com.medical.citylap.viewModel.OffersViewModel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class OfferActivity extends AppCompatActivity implements  ConnectionReceiver.ReceiverListener {
    OffersViewModel offersViewModel;
    public ArrayList<Datum> allOffer=new ArrayList<>();
    OfferAdapter offerAdapter;
    RecyclerView mRecyclerView;
    ImageView ivView;
    TextView tvView;
    public static boolean isconectedtointernt=false;
    ImageView imgview;
    ProgressBar progressBar;
    Socket socket;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_offerfragment);
        intilazation();
        checkConnection();
        if ( isconectedtointernt==true) {
            //  LoadingDialog.showDialog(getActivity());
            progressBar.setVisibility(View.VISIBLE);
            offersViewModel= ViewModelProviders.of(this).get(OffersViewModel.class);
            offersViewModel.getAllOffer().observe( this,new Observer<AllOffer>() {
                @Override
                public void onChanged(AllOffer allOffers) {
                    if (allOffers !=null)
                    {
                        if(allOffers.getData()==null)
                        {
                            mRecyclerView.setVisibility(View.GONE);
                            tvView.setVisibility(View.VISIBLE);
                            tvView.setText("لا يوجد عروض حاليا");
                            progressBar.setVisibility(View.GONE);
                        }
                        else {
                            ArrayList<Datum> rev = new ArrayList<>();
                            rev = (ArrayList<Datum>) allOffers.getData();
                            Collections.reverse(rev);
                            offerAdapter.setlist(rev);
                            mRecyclerView.setAdapter(offerAdapter);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
            });
        } else {

            mRecyclerView.setVisibility(View.GONE);
            tvView.setVisibility(View.VISIBLE);
            imgview.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        // LoadingDialog.hideDialog();


        ivView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              startActivity(new Intent(OfferActivity.this,Home.class));
                finish();

            }
        });

    }
    public void intilazation()
    {


        mRecyclerView = findViewById(R.id.recyclerview_offer);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        offerAdapter=new OfferAdapter(this);
        ivView=findViewById(R.id.imagebutton_back_from_offer_to_home);
        tvView=findViewById(R.id.nointerntid);
        imgview=findViewById(R.id.imageView2_no);
        progressBar=findViewById(R.id.prograbaroffer);


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isConnectede() {






       return  Connectivity.isConnectedFast(this);




    }
    private void checkConnection() {

        // initialize intent filter
        IntentFilter intentFilter = new IntentFilter();

        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

        // register receiver
        registerReceiver(new ConnectionReceiver(), intentFilter);

        // Initialize listener
        ConnectionReceiver.Listener =OfferActivity.this;

        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // get connection status
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        // display snack bar
        showSnackBar(isConnected);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(OfferActivity.this,Home.class));
        finish();
    }
    private void showSnackBar(boolean isConnected) {

        // initialize color and message
        String message;
        int color;

        // check condition
        if (isConnected) {


            isconectedtointernt=true;

        } else {


            message = "Not Connected to Internet";
            isconectedtointernt=false;
            // set text color
            color = Color.RED;
        }



    }


    @Override
    public void onNetworkChange(boolean isConnected) {
        // display snack bar
        showSnackBar(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // call method
        checkConnection();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // call method
        checkConnection();
    }
}