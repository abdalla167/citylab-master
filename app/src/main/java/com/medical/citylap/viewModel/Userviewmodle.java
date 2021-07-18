package com.medical.citylap.viewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.medical.citylap.modles.Loginmodle;

public class Userviewmodle extends ViewModel {
    public MutableLiveData<String> password=new MutableLiveData<>();
    public Loginmodle login;
    private Context context;

    public Userviewmodle(Loginmodle login, Context context) {
        this.login = login;
        this.context = context;
    }

    public void onloginclick()
    {


    }
}
