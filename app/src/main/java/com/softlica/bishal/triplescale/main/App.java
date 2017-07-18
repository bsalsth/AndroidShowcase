package com.softlica.bishal.triplescale.main;

import android.app.Application;
import android.location.Location;
import android.support.multidex.MultiDexApplication;

import com.softlica.bishal.triplescale.di.ApiComponent;
import com.softlica.bishal.triplescale.di.AppModule;
import com.softlica.bishal.triplescale.di.DaggerApiComponent;
import com.softlica.bishal.triplescale.di.DaggerNetComponent;
import com.softlica.bishal.triplescale.di.NetComponent;
import com.softlica.bishal.triplescale.di.NetModule;
import com.softlica.bishal.triplescale.utility.Constant;


/**
 * Created by bishal on 7/10/2017.
 */

public class App extends MultiDexApplication {

    private static Application sInstance;
    private static ApiComponent apiComponent;
    private static NetComponent netComponent;
    private static Location currentLocation;

    public static Location getCurrentLocation() {
        return currentLocation;
    }

    public static void setCurrentLocation(Location currentLocation) {
        App.currentLocation = currentLocation;
    }


    public static ApiComponent getApiComponent() {
        return apiComponent;
    }


    public static NetComponent getNetComponent() {
        return netComponent;
    }

    public static Application getsInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Location dummyLoc = new Location("Dummy Location");
        dummyLoc.setLatitude(51);
        dummyLoc.setLongitude(-71);
        currentLocation = dummyLoc;

        netComponent = DaggerNetComponent.builder()
                .netModule(new NetModule(Constant.BASE_URL))
                .appModule(new AppModule(this))
                .build();
        apiComponent = DaggerApiComponent.builder()
                .netComponent(netComponent)
                .build();

    }


}

