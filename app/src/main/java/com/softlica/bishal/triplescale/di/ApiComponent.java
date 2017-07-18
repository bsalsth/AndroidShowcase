package com.softlica.bishal.triplescale.di;


import com.softlica.bishal.triplescale.repo.UserResoponse;

import dagger.Component;

/**
 * Created by bishal on 7/10/2017.
 */

@CustomScope.custom
@Component(modules = {ApiModule.class}, dependencies = {NetComponent.class})
public interface ApiComponent {
    UserResoponse response();
}
