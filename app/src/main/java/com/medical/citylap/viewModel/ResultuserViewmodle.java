package com.medical.citylap.viewModel;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.medical.citylap.RetrofitClint;
import com.medical.citylap.helperfunction.LoadingDialog;
import com.medical.citylap.modles.AllOffer;
import com.medical.citylap.modles.Result;
import com.medical.citylap.modles.ResultApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ResultuserViewmodle extends ViewModel {

    MutableLiveData<ResultApi> resultmutbel=new MediatorLiveData<>();

    public  MutableLiveData<ResultApi> getResultuser(String token)
    {

       RetrofitClint.getInstance().getResults("Bearer "+token).enqueue(new Callback<ResultApi>() {
           @Override
           public void onResponse(Call<ResultApi> call, Response<ResultApi> response) {
               if (response.isSuccessful()) {
                   if (response.body().getData() != null)
                       resultmutbel.setValue(response.body());
                   //Log.d(TAG, "onResponse: "+response.body().getStatus());
               }
           }
           @Override
           public void onFailure(Call<ResultApi> call, Throwable t) {

           }
       });
return resultmutbel;
    }
}
