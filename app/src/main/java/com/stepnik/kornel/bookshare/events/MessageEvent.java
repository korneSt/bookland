package com.stepnik.kornel.bookshare.events;

import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Message;

import java.util.List;

import retrofit2.Response;

/**
 * Created by korSt on 03.12.2016.
 */

public class MessageEvent {
    public Response<Message> result;
    public List<Message> results;

    public MessageEvent (Response<Message> booksResult){
        this.result = booksResult;
    }

    public MessageEvent(List<Message> booksResult) {
        this.results = booksResult;
    }

}
