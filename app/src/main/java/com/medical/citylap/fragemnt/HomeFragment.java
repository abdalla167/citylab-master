package com.medical.citylap.fragemnt;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.medical.citylap.Adapter.SliderAdapterExample;
import com.medical.citylap.R;
import com.medical.citylap.MyPreference;
import com.medical.citylap.RetrofitClint;
import com.medical.citylap.activity.AboutUS;
import com.medical.citylap.activity.Blood_activity;
import com.medical.citylap.activity.BookingScreen;
import com.medical.citylap.activity.ResultActivty;
import com.medical.citylap.activity.SplashScreen;
import com.medical.citylap.activity.Suger_activity;
import com.medical.citylap.activity.Wight_acticty;
import com.medical.citylap.modles.AllOffer;
import com.medical.citylap.modles.Datum;
import com.medical.citylap.modles.Result;
import com.medical.citylap.modles.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment {
    Button result,booking,aboutus,seealloffer,suger,prusioer,perfectwight,medicalinformation;
    ImageView offer_one,offer_two,public_advice;
    TextView title_one,tile_two,start_one,start_two,end_one,end_two,pric_old_one,price_old_two,price_new_one,price_new_two,no_offer_one,no_ffer_two;
    Datum datum1;
    Datum datum2;
    public  int numbercheck;
    int number;
    SliderView sliderView;
    private SliderAdapterExample adapter;
public static Fragment fragment=new Offerfragment();
    MyPreference session;
    private Context myContext ;

    Bundle b = new Bundle();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        
        myContext = context ; 
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setnewoffers();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     View view= inflater.inflate(R.layout.fragment_home, container, false);
        intilazation( view);

        savedInstanceState = b ;

        boolean isLoaded = savedInstanceState.getBoolean("isLoaded");

        if (!isLoaded){

        }


        adapter = new SliderAdapterExample(myContext);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        addNewItem( view);
        seealloffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              final FragmentTransaction ft = getFragmentManager().beginTransaction();
              ft.replace(R.id.fragment_container, new Offerfragment(), "NewFragmentTag");
              ft.commit();
            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = myContext.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                String retrivedToken  = preferences.getString("phonenumberuser",null);
                if(retrivedToken !=null)
                {
                    startActivity(new Intent( myContext, ResultActivty.class));

                }
                else
                    {
                        final Dialog dialog = new Dialog(myContext);
                        dialog.setContentView(R.layout.custom_result);
                        dialog.show();
                }


            }
        });
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = myContext.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                String retrivedToken  = preferences.getString("phonenumberuser",null);
                if(retrivedToken !=null)
                {
                    startActivity(new Intent(myContext, BookingScreen.class));

                }
                else
                {
                    final Dialog dialog = new Dialog(myContext);
                    dialog.setContentView(R.layout.custom_result);
                    dialog.show();
                }


            }


        });
        suger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(myContext, Suger_activity.class));
            }
        });
        perfectwight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myContext, Wight_acticty.class));
            }
        });
        prusioer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myContext, Blood_activity.class));
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myContext, AboutUS.class));
            }
        });
        public_advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myContext, "التحديث القادم", Toast.LENGTH_SHORT).show();
            }
        });



        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.side_slide);

        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1500);
        result.startAnimation(animation);

        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1500);
        booking.startAnimation(animation);

        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1500);
        aboutus.startAnimation(animation);




        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1500);
        suger.startAnimation(animation);

        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1500);
        perfectwight.startAnimation(animation);


        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1500);
        prusioer.startAnimation(animation);



        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1500);
        medicalinformation.startAnimation(animation);


     return view;
    }
    public void intilazation(View view)
    {
        result=view.findViewById(R.id.resulthome_id);
        booking=view.findViewById(R.id.booking_test_id);
        aboutus=view.findViewById(R.id.aboutus_id);
        seealloffer=view.findViewById(R.id.see_all_offer);
        suger=view.findViewById(R.id.suger_id_home);
        prusioer=view.findViewById(R.id.presserblode_id_home);
        perfectwight=view.findViewById(R.id.perfect_wight_id_home);
        offer_one=view.findViewById(R.id.imag_home_offer_one);
        offer_two=view.findViewById(R.id.imag_home_offer_two);
        title_one=view.findViewById(R.id.title_one_offer);
        tile_two = view.findViewById(R.id.title_two_offer);
        start_one = view.findViewById(R.id.start_id_one);
        start_two = view.findViewById(R.id.start_id_two);
        end_one = view.findViewById(R.id.end_id_one);
        end_two = view.findViewById(R.id.end_id_two);
        pric_old_one = view.findViewById(R.id.old_pric_one_id);
        price_old_two = view.findViewById(R.id.old_pric_two_id);
        price_new_one = view.findViewById(R.id.new_pric_one_id);
        price_new_two = view.findViewById(R.id.new_pric_two_id);
        numbercheck=0;
        medicalinformation=view.findViewById(R.id.medical_information);
        no_ffer_two=view.findViewById(R.id.text_two);
        no_offer_one=view.findViewById(R.id.text_one);
        sliderView = view.findViewById(R.id.imageSlider);
        public_advice=view.findViewById(R.id.next_updat_home);
    }
    public int setnewoffers()
    {
//
//        Single <AllOffer> single=RetrofitClint.getInstance().getoffer().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//        AllOffer allOffer=new AllOffer()l
//        single.subscribe(o->)
//
  

            RetrofitClint.getInstance().getoffer_().enqueue(new Callback<AllOffer>() {
                @Override
                public void onResponse(Call<AllOffer> call, Response<AllOffer> response) {
                    if (response.isSuccessful()) {


                        b.putBoolean("isLoaded" , true);

                        if (response.body().getData().size() > 0) {
                            if (response.body().getData().size() == 1) {
                                datum1 = response.body().getData().get(response.body().getData().size() - 1);
                                if (myContext !=null)
                                Glide.with(myContext).load("http://" + datum1.getFiles().get(0))
                                        .into(offer_two);
                                tile_two.setText(datum1.getTitle());
                                start_two.setText(datum1.getStartTime().split(" ")[0]);
                                end_two.setText(datum1.getEndTime().split(" ")[0]);
                                price_old_two.setText(datum1.getPreviousPrice().toString());
                                price_new_two.setText(datum1.getCurrentPrice().toString());
                                no_ffer_two.setVisibility(View.GONE);
                            }


                            if (response.body().getData().size() > 1) {
                                datum1 = response.body().getData().get(response.body().getData().size() - 1);
                                datum2 = response.body().getData().get(response.body().getData().size() - 2);
                                if (myContext !=null)
                                Glide.with(myContext).load("http://" + datum1.getFiles().get(0))
                                        .into(offer_two);
                                tile_two.setText(datum1.getTitle());
                                start_two.setText(datum1.getStartTime().split(" ")[0]);
                                end_two.setText(datum1.getEndTime().split(" ")[0]);
                                price_old_two.setText(datum1.getPreviousPrice().toString());
                                price_new_two.setText(datum1.getCurrentPrice().toString());
                                if (myContext !=null)
                                Glide.with(myContext).load("http://" + datum2.getFiles().get(0))
                                        .into(offer_one);
                                title_one.setText(datum2.getTitle());
                                start_one.setText(datum2.getStartTime().split(" ")[0]);
                                end_one.setText(datum2.getEndTime().split(" ")[0]);
                                pric_old_one.setText(datum2.getPreviousPrice().toString());
                                price_new_one.setText(datum2.getCurrentPrice().toString());
                                no_ffer_two.setVisibility(View.GONE);
                                no_offer_one.setVisibility(View.GONE);
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<AllOffer> call, Throwable t) {
                }
            });
return  numbercheck;
    }
    public void addNewItem(View view) {
        SliderItem sliderItem = new SliderItem();
        List<SliderItem> sliderItemList = new ArrayList<>();
        sliderItem.setDescription("City lab");
        sliderItem.setImageUrl(R.drawable.citylabimag);
        sliderItemList.add(sliderItem);
        SliderItem sliderItem1 = new SliderItem();
        sliderItem1.setDescription("City lab");
        sliderItem1.setImageUrl(R.drawable.sliderone);
        sliderItemList.add(sliderItem1);
        SliderItem sliderItem2 = new SliderItem();
        sliderItem2.setDescription("City lab");
        sliderItem2.setImageUrl(R.drawable.headerhome);
        sliderItemList.add(sliderItem2);
        adapter.renewItems(sliderItemList);
    }
}