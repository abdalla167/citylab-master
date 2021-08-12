package com.medical.citylap.viewModel;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.medical.citylap.RetrofitClint;

import com.medical.citylap.helperfunction.LoadingDialog;
import com.medical.citylap.modles.AllOffer;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersViewModel extends ViewModel {

    MutableLiveData<AllOffer> allOfferMediatorLiveData=new MediatorLiveData<>();

    public MutableLiveData<AllOffer> getAllOffer()
    {

        Single <AllOffer> single=RetrofitClint.getInstance().getoffer().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        single.subscribe(o->allOfferMediatorLiveData.setValue(o));

return allOfferMediatorLiveData;
    }
}
