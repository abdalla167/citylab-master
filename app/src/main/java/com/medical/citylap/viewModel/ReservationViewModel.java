package com.medical.citylap.viewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.medical.citylap.RetrofitClint;
import com.medical.citylap.modles.Reservation;
import com.medical.citylap.modles.SimpleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ReservationViewModel extends ViewModel {

public void upload_reservation(Reservation reservation)
{
    RetrofitClint.getInstance().upload_book(reservation,"475fe934k5b54k4c15kb7e5k0ee1b6f5754e").enqueue(new Callback<SimpleResponse>() {
        @Override
        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
            Log.d(TAG, "onResponse: "+response.body().getMessage());
        }

        @Override
        public void onFailure(Call<SimpleResponse> call, Throwable t) {
            Log.d(TAG, "onResponse:+error "+t.getMessage());

        }
    });

}

}
