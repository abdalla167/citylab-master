package com.medical.citylap;

import com.medical.citylap.Interfacess.API;
import com.medical.citylap.modles.AllOffer;
import com.medical.citylap.modles.Loginmodle;
import com.medical.citylap.modles.Reservation;
import com.medical.citylap.modles.ResultApi;
import com.medical.citylap.modles.SimpleResponse;
import com.medical.citylap.modles.UsersResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClint {

    private static final String BASE_URL  = "http://citylab123-001-site1.htempurl.com/";
    private static RetrofitClint Instance;
    private API apiApi;

    public RetrofitClint() {


        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
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

public Call <ResultApi>getResults(String token)
{
    return apiApi.getResults(token);

}
public Call<Loginmodle>userlogin(String login , String token)
{
    return apiApi.userlogin(login , token);

}
public Call<SimpleResponse>usersignup(String name, String phonenumber, String token)
{

    return apiApi.addUser(name,phonenumber, token);
}

public  Call<AllOffer>getoffer()
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
