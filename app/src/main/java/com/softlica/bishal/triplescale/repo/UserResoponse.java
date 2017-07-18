package com.softlica.bishal.triplescale.repo;

import com.softlica.bishal.triplescale.models.UserWrapper;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bishal on 7/8/2017.
 */

public interface UserResoponse {

    @GET("/api/")
    Single<UserWrapper> getUserContainer(@Query("results") String size);

}
