package com.example.sardorbek.ptototypes.Remote;

import com.example.sardorbek.ptototypes.Model.new_requests.MyResponse;
import com.example.sardorbek.ptototypes.Model.new_requests.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by sardorbek on 4/25/18.
 */

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAhnDAye0:APA91bG70WVnEVDnYQ-nq3dAJ49txtyQMSsMDodVsMjfYXYgPakxeC0XnNChMcXRswbwosKgAeapcaL-Ul3TInUcTaeKslOp2gdunUrMNbg7jjfToWHLi1m7tqmbo779DH4ZwXdgAyR7"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);


}
