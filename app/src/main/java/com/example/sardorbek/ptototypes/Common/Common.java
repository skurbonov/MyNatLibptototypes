package com.example.sardorbek.ptototypes.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.sardorbek.ptototypes.Model.new_requests.User;
import com.example.sardorbek.ptototypes.Remote.APIService;
import com.example.sardorbek.ptototypes.Remote.RetrofitClient;

import retrofit2.Retrofit;

/**
 * Created by sardorbek on 2/8/18.
 */

public class Common {
    public static User currentUser;

    private static final String BASE_URL="https://fcm.googleapis.com/";

    public static APIService getFCMService()
    {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On my way";
        else
            return "Ready to pick up";
    }

    public static final String DELETE="Delete";

    public static final String USER_KEY="User";
    public static final String PWD_KEY="Password";



    public static boolean isConnectedInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }



}
