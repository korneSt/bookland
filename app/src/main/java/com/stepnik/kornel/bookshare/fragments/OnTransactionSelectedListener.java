package com.stepnik.kornel.bookshare.fragments;

import com.stepnik.kornel.bookshare.models.Transaction;

/**
 * Created by korSt on 17.11.2016.
 */

public interface OnTransactionSelectedListener {
    void onTransactionSelected(Transaction transaction, Boolean closed);
    void onCloseTransaction(Transaction transaction);
}
