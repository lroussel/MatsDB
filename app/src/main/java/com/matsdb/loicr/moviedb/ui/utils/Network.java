package com.matsdb.loicr.moviedb.ui.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Classe permettant de vérifier la connexion à internet
 */

public class Network {

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        android.net.Network[] networks = connectivityManager.getAllNetworks();
        NetworkInfo networkInfo;
        for (android.net.Network mNetwork : networks){
            networkInfo = connectivityManager.getNetworkInfo(mNetwork);
            if(networkInfo.getState().equals(NetworkInfo.State.CONNECTED)){
                return true;
            }
        }

        return false;
    }
}
