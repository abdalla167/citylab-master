package com.medical.citylap.viewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.medical.citylap.RetrofitClint;

import com.medical.citylap.helperfunction.LoadingDialog;
import com.medical.citylap.modles.AllOffer;
import com.medical.citylap.modles.ResultApi;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class OffersViewModel extends ViewModel {

    MutableLiveData<AllOffer> allOfferMediatorLiveData=new MediatorLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<AllOffer> getAllOffer()
    {

//RetrofitClint.getInstance().getoffer().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<AllOffer>() {
//    @Override
//    public void onSubscribe(@NonNull Disposable d) {
//        disposable.add(d);
//    }
//    @Override
//    public void onSuccess(@NonNull AllOffer allOffer) {
//        if (!allOffer.getStatus())
//        allOfferMediatorLiveData.postValue(allOffer);
//        Log.d("TAG", "onSuccess: "+allOffer.getStatus());
//    }
//    @Override
//    public void onError(@NonNull Throwable e) {
//        Log.d("TAG", "onError: "+e.getMessage());
//    }
//});
//try {
//    Single <AllOffer> single=RetrofitClint.getInstance().getoffer().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//    single.subscribe(o->allOfferMediatorLiveData.setValue(o),throwable -> Log.e(TAG, "Throwable " + throwable.getMessage()));
//    disposable.add(single.subscribe());
//
//}
//catch (Exception e)
//{
//    Log.d(TAG, "getAllOffer: "+e.getMessage());
//    return null;
//}

        RetrofitClint.getInstance().getoffer_().enqueue(new Callback<AllOffer>() {
            @Override
            public void onResponse(Call<AllOffer> call, Response<AllOffer> response) {
                if (response.isSuccessful())
                {
                    allOfferMediatorLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AllOffer> call, Throwable t) {

            }
        });

        return allOfferMediatorLiveData;
    }
    @Override
    protected void onCleared () {
        super.onCleared();
        disposable.clear();
    }
}
