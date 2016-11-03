package com.stepnik.kornel.bookshare.services;

import com.stepnik.kornel.bookshare.models.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Path;

/**
 * Created by korSt on 03.11.2016.
 */

public class BookData implements BookService {

    @Override
    public Call<List<Book>> getBooks() {
        return null;
    }

    @Override
    public Call<Book> addBook(@Field("title") String title, @Field("author") String author, @Field("ownerId") Long ownerId) {
        return null;
    }

}
