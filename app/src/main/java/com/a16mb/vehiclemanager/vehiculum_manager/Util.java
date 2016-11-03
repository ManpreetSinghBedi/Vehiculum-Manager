package com.a16mb.vehiclemanager.vehiculum_manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * Created by Manpreet Bedi on 02-11-2016.
 */

public class Util {
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

    public static final String INSERT_URL = "http://vehiclemanager.16mb.com/loginForm.php";
}
