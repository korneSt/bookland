package com.stepnik.kornel.bookshare.services;

import android.util.Log;

import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.NewBooksEvent;
import com.stepnik.kornel.bookshare.events.TransactionEvent;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;
import com.stepnik.kornel.bookshare.models.Transaction;
import com.stepnik.kornel.bookshare.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by korSt on 31.10.2016.
 */

public class TransactionService {

    TransactionServiceAPI transactionServiceAPI = Data.retrofit.create(TransactionServiceAPI.class);

    public void startTransaction(User user, Book book) {
        Call<Transaction> startTransaction = transactionServiceAPI.startTransaction(book.getId(), user.getId());
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
}
