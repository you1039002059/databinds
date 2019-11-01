package com.app.extras;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
 * Created by Yash on 13/6/18.
 */

public class ConnectivityReceiver
{
    /****************    for checking internet connection*******/
    public static boolean isNetworkAvailable(Context context)
    {

        ConnectivityManager connectivity;
        try
        {
            connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null)
            {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                {
                    for (NetworkInfo anInfo : info)
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED)
                        {
                            return true;
                        }
                }
            }
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
