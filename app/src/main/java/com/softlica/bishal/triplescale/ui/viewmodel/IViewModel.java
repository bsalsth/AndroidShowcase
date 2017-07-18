package com.softlica.bishal.triplescale.ui.viewmodel;

import com.softlica.bishal.triplescale.models.User;

import java.util.List;

/**
 * Created by bishal on 7/8/2017.
 */

public interface IViewModel {

    void displayData(List<User> users);

    void displayNoData(String s);

    void showProgressDialog();

    void dismissProgressDialog();

    void showError(String s);

}
