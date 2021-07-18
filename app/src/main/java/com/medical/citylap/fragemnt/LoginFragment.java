package com.medical.citylap.fragemnt;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.medical.citylap.Interfacess.API;
import com.medical.citylap.R;
import com.medical.citylap.RetrofitClint;
import com.medical.citylap.activity.SplashScreen;
import com.medical.citylap.helperfunction.Utile;
import com.medical.citylap.modles.Loginmodle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    public String pass=null;
    EditText password;
    Button loButton;
    ProgressBar progressBar;
    public API api;
    TextView textView_sign_up;
    private String phone = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    phone = s.toString();
                    Utile.hideKeyboard(getActivity());
                    loButton.setEnabled(true);
                }
                else
                    loButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        textView_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new signupuser(), "NewFragmentTag");
                ft.commit();
            }
        });
        loButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getContext(), password.getText().toString(), Toast.LENGTH_SHORT).show();
             if(   CheckVaildData(password.getText().toString())==1)
             {
                 loButton.setBackgroundColor(Color.BLUE);
                 loginfunction(password.getText().toString());

             }


            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

     View view= inflater.inflate(R.layout.fragment_login, container, false);
        intilazation(view);
        return view;
    }
    public void intilazation(View view)
    {

        password =view.findViewById(R.id.edittext_phon_number_for_login);
        loButton = view.findViewById(R.id.button_login_id);
        textView_sign_up=view.findViewById(R.id.Loginfromsignup);
        progressBar=view.findViewById(R.id.progresslogin);

    }
    public int CheckVaildData(String password){
        if(password!=null)
        {
            if(password.length()<10||password.length()>13)
            {
                Toast.makeText(getContext(), "برجاء ادخال البيانات بشكل صحيح", Toast.LENGTH_SHORT).show();
                return 0;
            }

            return 1;

        }
        Toast.makeText(getContext(), "برجاء ادخال البيانات بشكل صحيح", Toast.LENGTH_SHORT).show();
        return 0;
    }
    public void loginfunction(final String password)
    {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClint.getInstance().userlogin(password,SplashScreen.deviceToken).enqueue(new Callback<Loginmodle>() {
            @Override
            public void onResponse(Call<Loginmodle> call, Response<Loginmodle> response) {
                if(response.isSuccessful()) {
                  //  Toast.makeText(getContext(), response.body().getData().getToken().toString(), Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    preferences.edit().putString("phonenumberuser", password).apply();
                    SplashScreen.token_user = response.body().getData().getToken();
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, new Profilefragment(), "NewFragmentTag");
                    progressBar.setVisibility(View.GONE);
                    ft.commit();
                }
            }

            @Override
            public void onFailure(Call<Loginmodle> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}