package com.stepnik.kornel.bookshare.services;

import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by korSt on 17.11.2016.
 */

public interface TransactionServiceAPI {
    @GET("trans/get")
    Call<Transaction> getTransaction(@Query("transId") Long transId);


    @POST("trans/init")
    @FormUrlEncoded
    Call<Transaction> startTransaction(@Field("bookId") Long bookId, @Field("userId") Long userId);

    @POST("trans/accept")
    @FormUrlEncoded
    Call<Transaction> acceptTransaction(@Field("transId") Long transId);

    @POST("/trans/confirm")
    @FormUrlEncoded
    Call<Transaction> confirmTransaction(@Field("transId") Long transId);

    @POST("/trans/finalize")
    @FormUrlEncoded
    Call<Transaction> finalizeTransaction(@Field("transId") Long transId);

    @POST("/trans/close")
    @FormUrlEncoded
    Call<Transaction> closeTransaction(@Field("transId") Long transId, @Field("feedback") String feedback,
                                       @Field("owner") boolean owner);

    @GET("trans/all")
    Call<List<Transaction>> getAllTransactions(@Query("userId") Long userId);
}
