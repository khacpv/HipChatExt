package com.attlassian.hipchatext.common.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by khacpham on 8/17/15.<br/>
 * Utility class for network
 */
public class NetworkUtils {

    /**
     * detect has network or not.
     * @return true: has network, false: disconnect
     * */
    public static boolean isConnectedNetwork(Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
