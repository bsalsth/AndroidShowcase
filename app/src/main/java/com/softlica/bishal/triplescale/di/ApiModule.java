package com.softlica.bishal.triplescale.di;

import com.softlica.bishal.triplescale.repo.UserResoponse;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by bishal on 7/10/2017.
 */

@Module
public class ApiModule {

    @Provides
    @CustomScope.custom
    UserResoponse provideApiResponse(Retrofit retrofit){
        return retrofit.create(UserResoponse.class);
    }
}
