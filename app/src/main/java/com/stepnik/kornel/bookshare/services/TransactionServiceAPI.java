package com.stepnik.kornel.bookshare.services;

import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Message;
import com.stepnik.kornel.bookshare.models.Transaction;

import java.sql.Timestamp;
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

    @POST("trans/confirm")
    @FormUrlEncoded
    Call<Transaction> confirmTransaction(@Field("transId") Long transId);

    @POST("trans/finalize")
    Call<Void> finalizeTransaction(@Query("transId") Long transId);

    @POST("trans/close")
    Call<Void> closeTransaction(@Query("transId") Long transId, @Query("feedback") String feedback,
                                       @Query("rate") int rate, @Query("owner") boolean owner);

    @POST("trans/contact")
    @FormUrlEncoded
    Call<Message> createMessage(@Field("transId") Long transId, @Field("userId") Long userId,
                                @Field("message") String message);

    @GET("trans/messages")
    Call<List<Message>> getMessages(@Query("transId") Long transId);

    @GET("trans/all")
    Call<List<Transaction>> getAllTransactions(@Query("userId") Long userId);

    @GET("trans/history")
    Call<List<Transaction>> getHistoryTransactions(@Query("userId") Long userId);

    @GET("trans/news")
    Call<List<Message>> getNewMessages(@Query("time") Timestamp timestamp);
}
