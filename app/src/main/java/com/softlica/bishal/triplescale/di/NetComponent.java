package com.softlica.bishal.triplescale.di;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by bishal on 7/10/2017.
 */
@Singleton
@Component(modules = {NetModule.class, AppModule.class})
public interface NetComponent {

    Retrofit retrofit();
}
