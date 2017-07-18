package com.softlica.bishal.triplescale.services;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.softlica.bishal.triplescale.main.App;
import com.softlica.bishal.triplescale.models.User;
import com.softlica.bishal.triplescale.models.UserWrapper;
import com.softlica.bishal.triplescale.repo.UserResoponse;
import com.softlica.bishal.triplescale.utility.U;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bishal on 7/8/2017.
 */

public class ApiService implements IService {

    UserResoponse userResoponse;

    @Inject
    public ApiService(UserResoponse userResoponse) {
        this.userResoponse = userResoponse;
    }


    //    return UserWrapper observable for presenter to subscribe
    public Single<UserWrapper> getUsers() {
        // For demo purpose we are using only 15 users
        Single<UserWrapper> userWrapperObservable = userResoponse.getUserContainer(15 + "");
        return userWrapperObservable.map(userWrapper -> {
            for (User user : userWrapper.getUsers()) {
                U.log(user.getName()+ "<<<<<<<<<DOB ==>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=>" + user.getDob() );
                user.setFullAddress(U.getFullLocation(user.getLocation()));
                user.setFullName(U.getFullName(user.getName()));
                user.setDob(U.getAge(user.getDob()) + "");

            }
            return userWrapper;
        });

    }

}
