package com.stepnik.kornel.bookshare.models;

//import retrofit2.GsonConverterFactory;
import com.stepnik.kornel.bookshare.services.HeaderInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by korSt on 03.11.2016.
 */

public class Data {
    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(new HeaderInterceptor())
            .build();

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://bookland.azurewebsites.netcen/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build();
}
