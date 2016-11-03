package com.stepnik.kornel.bookshare.services;

import com.stepnik.kornel.bookshare.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by korSt on 20.10.2016.
 */

public interface BookService {

    @GET("book/all")
    Call<List<Book>> getBooks();

    @FormUrlEncoded
    @POST("book/create")
    Call<Book> addBook(@Field("title") String title, @Field("author") String author, @Field("ownerId") Long ownerId);
}
