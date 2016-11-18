package com.stepnik.kornel.bookshare.events;

import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.User;

import retrofit2.Response;

/**
 * Created by korSt on 18.11.2016.
 */
public class UserEvent {

    public Response<User> result;

    public UserEvent(Response<User> response) {
        this.result = response;
    }
}
