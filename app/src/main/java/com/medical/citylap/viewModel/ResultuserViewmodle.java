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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ResultuserViewmodle extends ViewModel {

    MutableLiveData<ResultApi> resultmutbel=new MediatorLiveData<>();
    //from api
    public  MutableLiveData<ResultApi> getResultuser(String token)
    {

        Single<ResultApi> single= RetrofitClint.getInstance().getResults("Bearer "+token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        single.subscribe(o->resultmutbel.setValue(o));
return resultmutbel;
    }
    //from local

    

}
