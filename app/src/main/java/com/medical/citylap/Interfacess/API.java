package com.medical.citylap.Interfacess;

import com.medical.citylap.modles.AllOffer;
import com.medical.citylap.modles.Loginmodle;
import com.medical.citylap.modles.ResultApi;
import com.medical.citylap.modles.SimpleResponse;
import com.medical.citylap.modles.UsersResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {



    @FormUrlEncoded
    @POST("api/Users/Login")
    public Call<Loginmodle>userlogin(@Field("PhoneNumber") String phone ,
                                     @Field("deviceToken") String token);

    @GET("api/offers/all")
    public Single<AllOffer>offers();


    @GET("api/offers/all")
    public Call<AllOffer>offers_();

    @GET("api/Results/All")
    @Headers("Accept:application/json")
    public Single<ResultApi> getResults(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/Users/Register")
   Call<SimpleResponse> addUser(@Field("Name") String name,
                                @Field("PhoneNumber") String phone,
                                @Field("deviceToken") String token);

    @GET("api/users/all")
    @Headers("Accept:application/json")
    Call<UsersResponse> getAllUsers(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/Reservations/reserve")
    @Headers("Accept:application/json")
    Call<SimpleResponse> upload_reservation(
            @Header("Authorization") String token,
            @Field("name")String name,
                                       @Field("phoneNumber") String PhoneNumber,
                                       @Field("age") String Age,
                                       @Field("day") int Day,
                                       @Field("reservationDate") String ReservationDate,
                                       @Field("type") int type,
                                       @Field("address") String Address,
                                       @Field("buildingNo") String BuildingNo,
                                       @Field("floorNo") String FloorNo,
                                       @Field("appartementNo")String appnumber,
                                       @Field("file") String File

                                     );





}
