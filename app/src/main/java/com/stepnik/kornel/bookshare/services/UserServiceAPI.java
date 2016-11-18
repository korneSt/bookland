package com.stepnik.kornel.bookshare.services;

import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by korSt on 31.10.2016.
 */

public interface UserServiceAPI {

    @GET("user")
    Call<User> getUser(@Query("id") Long userId);

    @FormUrlEncoded
    @POST("login")
    Call<User> login(@Field("login") String username, @Field("password") String password);

}
