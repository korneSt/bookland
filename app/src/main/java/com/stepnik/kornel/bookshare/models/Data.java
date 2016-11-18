package com.stepnik.kornel.bookshare.models;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by korSt on 03.11.2016.
 */

public class Data {
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http:/10.128.77.108:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    
}
