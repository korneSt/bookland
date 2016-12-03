package com.stepnik.kornel.bookshare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stepnik.kornel.bookshare.R;
import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.Message;
import com.stepnik.kornel.bookshare.services.AppData;

import java.util.ArrayList;

/**
 * Created by korSt on 03.12.2016.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    public MessageAdapter(Context context, int resource) {
        super(context, resource);
    }
    public MessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);

//        if (convertView == null) {
            if (AppData.loggedUser.getUserId().equals(message.getSenderId())) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.messages_list_item_right, parent, false);
            } else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.messages_list_item_left, parent, false);

            }
//        }

        TextView tvContent = (TextView) convertView.findViewById(R.id.tv_message);
        TextView tvUser = (TextView) convertView.findViewById(R.id.tv_user);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);

        tvContent.setText(message.getMessage());
        tvUser.setText(String.valueOf(message.getSenderId()));
        tvDate.setText(message.convertDate(message.getSendTime()));

        return convertView;
    }
}
