package com.stepnik.kornel.bookshare.services;

import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.BookEvent;
import com.stepnik.kornel.bookshare.events.NewBooksEvent;
import com.stepnik.kornel.bookshare.events.UserEvent;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Data;
import com.stepnik.kornel.bookshare.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by korSt on 18.11.2016.
 */

public class UserService {
    UserServiceAPI userServiceAPI = Data.retrofit.create(UserServiceAPI.class);

    public void getUser(Long userId) {
        Call<User> user = userServiceAPI.getUser(userId);

        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    BusProvider.getInstance().post(new UserEvent(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

//    public void getNearUsers() {
//        ArrayList<User> u = AppData.getUsersList();
//        BusProvider.getInstance().post(new UserEvent(u));
//    }

    public void getNearUsers(Long userId) {
        Call<List<User>> books = userServiceAPI.getNearUsers(userId);
        books.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.isSuccessful()) {
                    BusProvider.getInstance().post(new UserEvent(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    public void setPreferences(Long userId, float radius, float lat, float lon) {
        Call<Void> prefs = userServiceAPI.setPreferences(userId, radius, lat, lon);

        prefs.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()) {
//                    BusProvider.getInstance().post(new UserEvent(response));
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }
}
