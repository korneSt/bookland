package com.stepnik.kornel.bookshare.services;

import com.stepnik.kornel.bookshare.models.Book;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by korSt on 08.12.2016.
 */

public interface GoodreadsServiceAPI {

    @GET("search.xml")
    Call<ResponseBody> getBooks(@Query("key") String key, @Query("q") String title);
}
