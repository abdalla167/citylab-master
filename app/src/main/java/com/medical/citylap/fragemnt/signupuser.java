package com.medical.citylap.fragemnt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.medical.citylap.R;
import com.medical.citylap.RetrofitClint;

import com.medical.citylap.MyPreference;
import com.medical.citylap.activity.SplashScreen;
import com.medical.citylap.modles.Loginmodle;
import com.medical.citylap.modles.SimpleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class signupuser extends Fragment {
    MyPreference session;
    public String pass=null;
    EditText password,name;
    Button loButton;
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_signupuser, container, false);
        intilazation(view);

        loButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               RetrofitClint.getInstance().usersignup(name.getText().toString(),password.getText().toString() , SplashScreen.deviceToken).enqueue(new Callback<SimpleResponse>() {
                   @Override
                   public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                       if(response.isSuccessful())
                       {
                           Toast.makeText(getContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()) {

                String phonnumber = password.getText().toString();
                SharedPreferences preferences = getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                preferences.edit().putString("phonenumberuser", phonnumber).apply();

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new Profilefragment(), "NewFragmentTag");
                ft.commit();
                }
                       }
                   }

                   @Override
                   public void onFailure(Call<SimpleResponse> call, Throwable t) {

                   }
               });
                }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new LoginFragment(), "NewFragmentTag");
                ft.commit();
            }
        });
return view;
    }

    public void intilazation(View view)
    {

        password =view.findViewById(R.id.editText_phon_number_sign_in_id);
        name=view.findViewById(R.id.editText_name_sign_in_id);
        loButton = view.findViewById(R.id.button_login_id);
        textView=view.findViewById(R.id.Loginfromsignup);
    }
}