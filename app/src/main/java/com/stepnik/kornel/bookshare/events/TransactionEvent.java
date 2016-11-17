package com.stepnik.kornel.bookshare.events;

import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Transaction;

import java.util.List;

import retrofit2.Response;

/**
 * Created by korSt on 17.11.2016.
 */

public class TransactionEvent {
    public Response<List<Transaction>> result;

    public TransactionEvent(Response<List<Transaction>> transResult){
        this.result = transResult;
    }

}
