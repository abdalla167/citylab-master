package com.medical.citylap;

import com.medical.citylap.Interfacess.API;
import com.medical.citylap.modles.AllOffer;
import com.medical.citylap.modles.Loginmodle;
import com.medical.citylap.modles.Reservation;
import com.medical.citylap.modles.ResultApi;
import com.medical.citylap.modles.SimpleResponse;
import com.medical.citylap.modles.UsersResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClint {

    private static final String BASE_URL  = "http://citylab123-001-site1.htempurl.com/";
    private static RetrofitClint Instance;
    private API apiApi;

    public RetrofitClint() {


        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build();
        apiApi=retrofit.create(API.class);
    }

    public static RetrofitClint getInstance() {
        if(null==Instance)
        {
            Instance=new RetrofitClint();

        }
        return Instance;
    }

public Single<ResultApi> getResults(String token)
{
    return apiApi.getResults(token);

}
public Call<Loginmodle>userlogin(String login , String token)
{
    return apiApi.userlogin(login , token);

}
public Call<AllOffer>getoffer_()
{
    return apiApi.offers_();
}
public Call<SimpleResponse>usersignup(String name, String phonenumber, String token)
{

    return apiApi.addUser(name,phonenumber, token);
}

public  Single<AllOffer>getoffer()
{
    return  apiApi.offers();

}
public Call<SimpleResponse> upload_book(Reservation reservation ,String token)
{
    return apiApi.upload_reservation(
            "Bearer "+token,
            reservation.getName(),
            reservation.getPhoneNumber(),
            reservation.getAge(),
            reservation.getDay(),
            reservation.getReservationDate(),
            reservation.getType(),
            reservation.getAddress(),
            reservation.getBuildingNo(),
            reservation.getFloorNo(),
            reservation.getAppartementNo(),
            reservation.getFile()


            );

}


public  Call<UsersResponse>getalluer(String token)
    {
        return apiApi.getAllUsers(token);
    }

 }
