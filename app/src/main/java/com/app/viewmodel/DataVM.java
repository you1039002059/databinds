package com.app.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.app.extras.AlertMsgCallback;
import com.app.extras.ConnectivityReceiver;
import com.app.extras.ConstantData;
import com.app.model.DataManager;
import com.app.smtask.MyApplication;
import com.app.smtask.R;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*
 * Created by Yash on 13/6/18.
 */

public class DataVM extends AndroidViewModel
{
    private Disposable disposable;
    private MutableLiveData<DataManager> dataList;

    public DataVM(@NonNull Application application)
    {
        super(application);
    }

    public LiveData<DataManager> getServerData(AlertMsgCallback<String> callback)
    {
        if (dataList == null)
        {
            //  Intialization of MutableLiveData.

            dataList = new MutableLiveData<>();

             /*
              *  Network connectivity check to check whether Internet is connected or not.
              */

            networkCheck(callback);
        }

        return dataList;
    }

    private void networkCheck(AlertMsgCallback<String> callback)
    {
        if(ConnectivityReceiver.isNetworkAvailable(this.getApplication()))
        {
            /*
            * isNetworkAvailable will check whether device is connected to wifi or data connection
            * If yes then will move to next step otherwise display a message "Please Check Your Internet Connection"*/

            serverDataApi(callback);
        }
        else
        {
            Toast.makeText(this.getApplication(), R.string.connectWifiDataConn,Toast.LENGTH_SHORT).show();
        }
    }

    private void serverDataApi(final AlertMsgCallback<String> callback)
    {
        // Do an asynchronous operation to fetch data using RxJava along with Retrofit

        /*
        * RxJava = OBSERVABLE + OBSERVER + SCHEDULERS
        * Observer: It consumes the data stream emitted by the observable and
        * Observer subscribe to the observable using subscribeOn() method to receive the data emitted by the observable.
        * In RxJava Schedulers.io() will execute the code on IO thread.*/

        disposable = MyApplication.apiService.getDataFromServer(ConstantData.serviceURL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JsonArray>()
                {
                    @Override
                    public void accept(JsonArray jsonArray) throws Exception
                    {
                        JSONArray response = new JSONArray(jsonArray.toString());

                        setAllData(response);
                    }
                }, new Consumer<Throwable>()
                {
                    @Override
                    public void accept(Throwable throwable) throws Exception
                    {
                        if(throwable instanceof SocketTimeoutException)
                        {
                            timeOut(callback);
                        }
                    }
                });
    }

    private void timeOut(AlertMsgCallback<String> callback)
    {
        callback.alertMsgCallback(this.getApplication().getResources().getString(R.string.timeOut));
    }

    private void setAllData(JSONArray response)
    {
        try
        {
            for(int i = 0; i < response.length(); i++)
            {
                DataManager dataManager = new DataManager();

                JSONObject jsonObject = response.getJSONObject(i);

                if(jsonObject.has(this.getApplication().getResources().getString(R.string.titleParams)))
                    if (!(jsonObject.isNull(this.getApplication().getResources().getString(R.string.titleParams))))
                    {
                        JSONObject titleJsonObject = jsonObject.getJSONObject(this.getApplication().getResources().getString(R.string.titleParams));

                        if(titleJsonObject.has(this.getApplication().getResources().getString(R.string.valueParams)))
                            if (!(titleJsonObject.isNull(this.getApplication().getResources().getString(R.string.valueParams))))
                                dataManager.setTitle(titleJsonObject.getString(this.getApplication().getResources().getString(R.string.valueParams)));
                    }

                if(jsonObject.has(this.getApplication().getResources().getString(R.string.imageParams)))
                    if (!(jsonObject.isNull(this.getApplication().getResources().getString(R.string.imageParams))))
                    {
                        JSONObject imageJsonObject = jsonObject.getJSONObject(this.getApplication().getResources().getString(R.string.imageParams));

                        if(imageJsonObject.has(this.getApplication().getResources().getString(R.string.valueParams)))
                            if (!(imageJsonObject.isNull(this.getApplication().getResources().getString(R.string.valueParams))))
                                dataManager.setImage(imageJsonObject.getString(this.getApplication().getResources().getString(R.string.valueParams)));
                    }

                if(jsonObject.has(this.getApplication().getResources().getString(R.string.htmlTxtParams)))
                    if (!(jsonObject.isNull(this.getApplication().getResources().getString(R.string.htmlTxtParams))))
                    {
                        JSONObject htmlTxtJsonObject = jsonObject.getJSONObject(this.getApplication().getResources().getString(R.string.htmlTxtParams));

                        if(htmlTxtJsonObject.has(this.getApplication().getResources().getString(R.string.valueParams)))
                            if (!(htmlTxtJsonObject.isNull(this.getApplication().getResources().getString(R.string.valueParams))))
                                dataManager.setHtmlText(htmlTxtJsonObject.getString(this.getApplication().getResources().getString(R.string.valueParams)));
                    }

                dataList.setValue(dataManager);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCleared()
    {
        // Note: We do not need to call super.onCleared() because the base implementation is empty.
        if(disposable != null && !disposable.isDisposed())
        {
            disposable.dispose();// To dispose or unSuscribe the RxJava Observer
        }
    }
}
