package com.stepnik.kornel.bookshare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.squareup.otto.Subscribe;
import com.stepnik.kornel.bookshare.MainActivity;
import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.bus.BusProvider;
import com.stepnik.kornel.bookshare.events.TransactionEvent;
import com.stepnik.kornel.bookshare.models.Transaction;
import com.stepnik.kornel.bookshare.services.TransactionService;

import java.util.ArrayList;
import java.util.HashMap;

import static java.security.AccessController.getContext;

/**
 * Created by korSt on 17.11.2016.
 */

public class TransactionsFragment extends Fragment{
    ListView lvTransactions;
    ArrayList<HashMap<String, String>> transactionList;
    ArrayList<Transaction> transactionData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate( R.layout.fragment_transactions,container,false );
        MainActivity mainActivity = (MainActivity) getContext();
        mainActivity.setTitle("Transaction");

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            lvTransactions = (ListView) view.findViewById(R.id.lv_transactions);

            transactionList = new ArrayList<>();
            transactionData = new ArrayList<>();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        new TransactionService().getAllTransactions(1L);

    }

    @Subscribe
    public void onTransactionEvent(TransactionEvent event) {

        for (Transaction t : event.result.body()) {
            transactionData.add(t);
            HashMap<String, String> tempTransaction = new HashMap<>();
            tempTransaction.put("title", String.valueOf(t.getOwnerId()));
            tempTransaction.put("author", String.valueOf(t.getUserId()));
            transactionList.add(tempTransaction);
        }

        ListAdapter adapter = new SimpleAdapter(
                getContext(), transactionList,
                R.layout.list_item, new String[]{"title", "author"},
                new int[]{R.id.tv_title, R.id.tv_author}
        );
        lvTransactions.setAdapter(adapter);

    }

}
