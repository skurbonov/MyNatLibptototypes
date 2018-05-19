package com.example.sardorbek.ptototypes.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sardorbek on 4/25/18.
 */

public class RetrofitClient {
    private static Retrofit retrofit=null;

    public static  Retrofit getClient(String baseURl)
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl(baseURl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
