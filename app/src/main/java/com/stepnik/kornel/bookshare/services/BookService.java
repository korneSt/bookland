package com.stepnik.kornel.bookshare.services;

import com.squareup.otto.Produce;
import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.NewBooksEvent;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by korSt on 13.11.2016.
 */

public class BookService {

//    BookServiceAPI bookServiceAPI = Data.retrofit.create(BookServiceAPI.class);


    public void loadNewBooks() {
        BookServiceAPI bookServiceAPI = Data.retrofit.create(BookServiceAPI.class);
        Call<List<Book>> books = bookServiceAPI.getBooks();
        books.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {

                if (response.isSuccessful()) {
                    BusProvider.getInstance().post(produceNewBooksEvent(response));
//                    setAdapter(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });
    }

    @Produce
    public NewBooksEvent produceNewBooksEvent(Response<List<Book>> booksResult){
        return new NewBooksEvent(booksResult);
    }
}
