package com.stepnik.kornel.bookshare.services;

import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by korSt on 31.10.2016.
 */

public interface UserService {

    @GET("users/{id}")
    Call<List<Book>> getUsers(@Path("id") int userId);

    @POST("users/new")
    Call<User> createUser(@Body User user);

}
