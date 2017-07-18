package com.softlica.bishal.triplescale.di;

        import android.app.Application;

        import com.softlica.bishal.triplescale.ui.MainActivity;

        import dagger.Component;

/**
 * Created by bishal on 7/10/2017.
 */

@CustomScope.activityScope
@Component(modules = {PresenterModule.class}, dependencies = ApiComponent.class)
public interface PresenterComponent {
    void inject(MainActivity mainActivity);
}
