package com.softlica.bishal.triplescale.di;

import com.softlica.bishal.triplescale.presenters.IPresenter;
import com.softlica.bishal.triplescale.ui.viewmodel.HomeViewModel;
import com.softlica.bishal.triplescale.ui.viewmodel.IViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bishal on 7/10/2017.
 */

@Module
public class PresenterModule {
    HomeViewModel view;


    public PresenterModule(HomeViewModel view) {
        this.view = view;
    }


    @Provides
    @CustomScope.activityScope
    HomeViewModel provideView() {
        return view;
    }

}
