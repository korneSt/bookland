package com.stepnik.kornel.bookshare;

import com.stepnik.kornel.bookshare.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by korSt on 20.10.2016.
 */

public interface BookService {
    @GET("books/{user}/repos")
    Call<List<Book>> listRepos(@Path("user") String user);
    @GET("books")
    Call<List<Book>> getBooks();

    @GET("movies/readAllBeforeYear?year=2017")
    Call<List<Book>> getBooksYear();
}
