package com.stepnik.kornel.bookshare.services;

import com.stepnik.kornel.bookshare.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by korSt on 20.10.2016.
 */

public interface BookServiceAPI {

    @GET("book")
    Call<Book> getBook(@Query("id") Long bookId);

    @GET("book/all")
    Call<List<Book>> getBooks();

    @GET("book/user")
    Call<List<Book>> getUserBooks(@Query("id") Long userId);

    @FormUrlEncoded
    @POST("book/create")
    Call<Book> addBook(@Field("title") String title, @Field("author") String author, @Field("ownerId") Long ownerId);

    @GET("book/author/{author}")
    Call<List<Book>> getBookByAuthor(@Path("author") String author);
}
