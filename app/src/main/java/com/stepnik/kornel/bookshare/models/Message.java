package com.stepnik.kornel.bookshare.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by korSt on 28.10.2016.
 */

public class Message implements Serializable {
    private long id;
    private long transId;
    private long senderId;
    private Long sendTime;
    private String message;

    public Message() {

    }

    public long getTransId() {
        return transId;
    }

    public void setTransId(long transId) {
        this.transId = transId;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String convertDate(Long date) {
        Date d = new Date(date);
        return d.toString();
    }
}
