package com.medical.citylap.fragemnt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.medical.citylap.R;
import com.medical.citylap.RetrofitClint;
import com.medical.citylap.activity.SplashScreen;
import com.medical.citylap.modles.Loginmodle;
import com.medical.citylap.modles.User;
import com.medical.citylap.modles.UsersResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class Profilefragment extends Fragment {
TextView name,phonnumber;

Button logout;
public String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view=inflater.inflate(R.layout.fragment_profil_user_screen, container, false);
intil(view);
        getdata();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getContext().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                preferences.edit().remove("phonenumberuser").commit();
                preferences.edit().remove("nameuserprofile").commit();



                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new LoginFragment(), "NewFragmentTag");
                ft.commit();
            }
        });
        return view;
    }
public void intil(View view)
{
    name=view.findViewById(R.id.nameclintidprofle);
    phonnumber=view.findViewById(R.id.phonnumberclintprofile);
    logout=view.findViewById(R.id.lougout_button);

}
public void getdata()
{
    SharedPreferences preferences3 = getContext().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
    String retrivedname_user  = preferences3.getString("nameuserprofile",null);
    String retrivedphonenumber = preferences3.getString("phonenumberuser", null);

    if(retrivedname_user !=null)
    {
        name.setText(retrivedname_user);
        phonnumber.setText(retrivedphonenumber);
        Log.d(TAG, "getdata: "+retrivedname_user);
    }
    else {

        RetrofitClint.getInstance().userlogin("01119082271" , SplashScreen.deviceToken).enqueue(new Callback<Loginmodle>() {
            @Override
            public void onResponse(Call<Loginmodle> call, Response<Loginmodle> response1) {
                if (response1.isSuccessful()) {

                    RetrofitClint.getInstance().getalluer("Bearer " + response1.body().getData().getToken()).enqueue(new Callback<UsersResponse>() {
                        @Override
                        public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response2) {
                            if (response2.isSuccessful()) {

                                SharedPreferences preferences = getContext().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                                String retrivedphonenumber = preferences.getString("phonenumberuser", null);//second parameter default value.
                              int g=0;
                                for (int i = 0; i < response2.body().getData().size(); i++) {
                                    if (retrivedphonenumber.equals(response2.body().getData().get(i).getPhoneNumber())) {
                                        name.setText(response2.body().getData().get(i).getName());
                                        phonnumber.setText(response2.body().getData().get(i).getPhoneNumber());
                                          g=i;
                                        break;
                                    }
                                }
                                preferences.edit().putString("phonenumberuserprofil",response2.body().getData().get(g).getPhoneNumber()).apply();
                                preferences.edit().putString("nameuserprofile",response2.body().getData().get(g).getName()).apply();
                            }
                        }
                        @Override
                        public void onFailure(Call<UsersResponse> call, Throwable t) {
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<Loginmodle> call, Throwable t) {

            }
        });
    }

}
}