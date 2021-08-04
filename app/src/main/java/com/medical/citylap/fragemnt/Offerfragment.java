package com.medical.citylap.fragemnt;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.medical.citylap.Adapter.OfferAdapter;
import com.medical.citylap.R;
import com.medical.citylap.helperfunction.LoadingDialog;
import com.medical.citylap.modles.AllOffer;
import com.medical.citylap.modles.Datum;
import com.medical.citylap.viewModel.OffersViewModel;

import java.util.ArrayList;
import java.util.Collections;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;


public class Offerfragment extends Fragment {
 OffersViewModel offersViewModel;
 public ArrayList<Datum> allOffer=new ArrayList<>();
 OfferAdapter offerAdapter;
 RecyclerView mRecyclerView;
 ImageView ivView;
 TextView tvView;
ImageView imgview;
ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_offerfragment, container, false);
        intilazation(view);
        if (isConnected()) {
          //  LoadingDialog.showDialog(getActivity());
            progressBar.setVisibility(View.VISIBLE);
            offersViewModel= ViewModelProviders.of(getActivity()).get(OffersViewModel.class);
            offersViewModel.getAllOffer().observe( getViewLifecycleOwner(),new Observer<AllOffer>() {
                @Override
                public void onChanged(AllOffer allOffers) {
                    ArrayList<Datum> rev=new ArrayList<>();
                    rev= (ArrayList<Datum>) allOffers.getData();
                    Collections.reverse(rev);
                    offerAdapter.setlist( rev);
                    mRecyclerView.setAdapter(offerAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            });

        } else {
            mRecyclerView.setVisibility(View.GONE);
            tvView.setVisibility(View.VISIBLE);
            imgview.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
       // LoadingDialog.hideDialog();

        ivView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new HomeFragment(), "NewFragmentTag");
                ft.commit();
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    public void intilazation(View view)
    {


        mRecyclerView = view.findViewById(R.id.recyclerview_offer);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        offerAdapter=new OfferAdapter(getActivity());
        ivView=view.findViewById(R.id.imagebutton_back_from_offer_to_home);
        tvView=view.findViewById(R.id.nointerntid);
        imgview=view.findViewById(R.id.imageView2_no);
        progressBar=view.findViewById(R.id.prograbaroffer);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}