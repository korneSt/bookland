package com.stepnik.kornel.bookshare.services;

import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.NewBooksEvent;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by korSt on 08.12.2016.
 */

public class GoodreadsService {

    private GoodreadsServiceAPI bookServiceAPI = Data.retrofitGR.create(GoodreadsServiceAPI.class);

    public void searchBooks(String title) {
        Call<ResponseBody> books = bookServiceAPI.getBooks("aqNRg8X7TIUBvboElhdg", title);
        books.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
//                    BusProvider.getInstance().post(new NewBooksEvent(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
