package com.ramadan.twitterclient.presentation.model;

import static android.R.attr.id;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public class User {

    private String username;
    private long id;

    public User(String username , long id){
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
