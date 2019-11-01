package com.app.smtask;

import android.app.Application;
import com.app.apirequest.ApiInterface;
import com.app.apirequest.ApiRequestSingleton;

/*
 * Created by Yash on 13/6/18.
 */

public class MyApplication extends Application
{
    public static ApiInterface apiService;

    @Override
    public void onCreate()
    {
        super.onCreate();

        //Creating Instance of Retrofit
        apiService = ApiRequestSingleton.getClient().create(ApiInterface.class);
    }
}
