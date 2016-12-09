package com.stepnik.kornel.bookshare.models;

//import retrofit2.GsonConverterFactory;
import com.stepnik.kornel.bookshare.services.HeaderInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by korSt on 03.11.2016.
 */

public class Data {
    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(new HeaderInterceptor())
            .build();

    private static OkHttpClient httpClientGR = new OkHttpClient.Builder()
            .build();

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://bookland.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build();

    public static Retrofit retrofitGR = new Retrofit.Builder()
            .client(new OkHttpClient())
            .baseUrl("https://www.goodreads.com/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();
}
