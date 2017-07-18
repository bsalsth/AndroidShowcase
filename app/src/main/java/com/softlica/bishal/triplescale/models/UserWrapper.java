package com.softlica.bishal.triplescale.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bishal on 7/8/2017.
 */

public class UserWrapper {

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @SerializedName("results")
    private List<User> users;
}
