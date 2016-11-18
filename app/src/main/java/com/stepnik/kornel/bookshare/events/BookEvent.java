package com.stepnik.kornel.bookshare.events;

import com.stepnik.kornel.bookshare.models.Book;

import java.util.List;

import retrofit2.Response;

/**
 * Created by korSt on 17.11.2016.
 */

public class BookEvent {
    public Response<Book> result;

    public BookEvent (Response<Book> booksResult){
        this.result = booksResult;
    }
}
