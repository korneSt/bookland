package com.stepnik.kornel.bookshare.models;

import java.io.Serializable;

/**
 * Created by korSt on 27.10.2016.
 */

public class User implements Serializable {
    private int userId;
    private String username;
    private String name;
    private String lastname;
    private int bookCount;
    private int localization;
    private int lendTime;
    private String about;

}
