package com.stepnik.kornel.bookshare.services;

import android.util.Log;

import com.squareup.otto.Produce;
import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.BookEvent;
import com.stepnik.kornel.bookshare.events.NewBooksEvent;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;
import com.stepnik.kornel.bookshare.models.LoginResponse;
import com.stepnik.kornel.bookshare.models.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

/**
 * Created by korSt on 13.11.2016.
 */

public class BookService {

    BookServiceAPI bookServiceAPI = Data.retrofit.create(BookServiceAPI.class);

    public void loadNewBooks() {
        Call<List<Book>> books = bookServiceAPI.getBooks(AppData.loggedUser.getUserId(), "");
        books.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {

                if (response.isSuccessful()) {
                    BusProvider.getInstance().post(new NewBooksEvent(response));
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });
    }

    public void getBook(Long bookId) {
        Call<Book> book = bookServiceAPI.getBook(bookId);

        book.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {

                if (response.isSuccessful()) {
                    BusProvider.getInstance().post(new BookEvent(response));
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {

            }
        });
    }

    public void searchBook(Long userId, String keyWord) {
        Call<List<Book>> books = bookServiceAPI.searchBook(userId, keyWord);
        books.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {

                if (response.isSuccessful()) {
                    BusProvider.getInstance().post(new NewBooksEvent(response));
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });
    }

    public void getUserBooks(Long userId) {

        Call<List<Book>> books = bookServiceAPI.getUserBooks(userId);

        books.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, retrofit2.Response<List<Book>> response) {

                if (response.isSuccessful()) {
                    BusProvider.getInstance().post(new BookEvent(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.d("status", String.valueOf(t.getStackTrace()));

            }
        });
    }

    public void getUserRentBooks (Long userId) {
//        Call<List<Book>> books = bookServiceAPI.getUserRentBooks(userId);
        ArrayList<Book> books = AppData.getBookList();
//        books.enqueue(new Callback<List<Book>>() {
//            @Override
//            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
//
//                if (response.isSuccessful()) {
//                    BusProvider.getInstance().post(new NewBooksEvent(response));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Book>> call, Throwable t) {
//
//            }
//        });
        BusProvider.getInstance().post(new BookEvent(books));
    }

    public void deleteBook(long bookId) {
        Call<Void> book = bookServiceAPI.deleteBook(bookId);

        book.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
