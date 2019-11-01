package com.app.apirequest;

import com.google.gson.JsonArray;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/*
 * Created by Yash on 13/6/18.
 */

public interface ApiInterface
{
     /*
        * Observable packs the data that can be passed around from one thread to another thread.
        * They basically emit the data periodically or only once in their life cycle based on their configurations.
      * */

    @GET
    // @GET is the type of request
   Observable<JsonArray> getDataFromServer(@Url String url);//@Url We will pass the Url using @Url tag as it will ignore the base url
}
