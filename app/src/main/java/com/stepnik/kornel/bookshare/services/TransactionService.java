package com.stepnik.kornel.bookshare.services;

import android.util.Log;

import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.MessageEvent;
import com.stepnik.kornel.bookshare.events.NewBooksEvent;
import com.stepnik.kornel.bookshare.events.TransactionEvent;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;
import com.stepnik.kornel.bookshare.models.LoginResponse;
import com.stepnik.kornel.bookshare.models.Message;
import com.stepnik.kornel.bookshare.models.Transaction;
import com.stepnik.kornel.bookshare.models.User;

import java.sql.Timestamp;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by korSt on 31.10.2016.
 */

public class TransactionService {

    TransactionServiceAPI transactionServiceAPI = Data.retrofit.create(TransactionServiceAPI.class);

    public void startTransaction(LoginResponse user, Book book) {
        Call<Transaction> startTransaction = transactionServiceAPI.startTransaction(book.getId(), user.getUserId());
        startTransaction.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                Log.d("succes", String.valueOf(response.body()));
//                if (response.isSuccessful()) {
//                    BusProvider.getInstance().post(new TransactionEvent(response));
//                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Log.d("succes", String.valueOf(t.getStackTrace()));

            }
        });
    }

    public void acceptTransaction(Long transactionId) {
        Call<Transaction> startTransaction = transactionServiceAPI.acceptTransaction(transactionId);
        startTransaction.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                Log.d("succes", String.valueOf(response.body()));
//                if (response.isSuccessful()) {
//                    BusProvider.getInstance().post(new TransactionEvent(response));
//                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Log.d("succes", String.valueOf(t.getStackTrace()));

            }
        });
    }

    public void confirmTransaction(Long transactionId) {
        Call<Transaction> startTransaction = transactionServiceAPI.confirmTransaction(transactionId);
        startTransaction.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                Log.d("succes", String.valueOf(response.body()));
//                if (response.isSuccessful()) {
//                    BusProvider.getInstance().post(new TransactionEvent(response));
//                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Log.d("succes", String.valueOf(t.getStackTrace()));

            }
        });
    }

    public void getAllTransactions(Long userId) {
        Call<List<Transaction>> startTransaction = transactionServiceAPI.getAllTransactions(userId);
        startTransaction.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {

                if (response.isSuccessful()) {
                    BusProvider.getInstance().post(new TransactionEvent(response));
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });
    }

    public void createMessage (final Long transactionId, final Long userId, String message) {
        Call<Message> createMessage = transactionServiceAPI.createMessage(transactionId, userId, message);
        createMessage.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.d("succes", String.valueOf(response.body()));
                if (response.isSuccessful()) {
                    getMessages(transactionId);
//                    BusProvider.getInstance().post(new MessageEvent(response));
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d("succes", String.valueOf(t.getStackTrace()));

            }
        });
    }

    public void getMessages(Long transId) {
        Call<List<Message>> getMessages = transactionServiceAPI.getMessages(transId);
        getMessages.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                if (response.isSuccessful()) {
                    BusProvider.getInstance().post(new MessageEvent(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    public void getNewMessages(Timestamp timestamp) {
        Call<List<Message>> newMessages = transactionServiceAPI.getNewMessages(timestamp);
        newMessages.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                if (response.isSuccessful()) {
                    BusProvider.getInstance().post(new MessageEvent(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }
}
