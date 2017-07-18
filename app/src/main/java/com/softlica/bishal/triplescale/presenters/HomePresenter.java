package com.softlica.bishal.triplescale.presenters;

import com.softlica.bishal.triplescale.models.User;
import com.softlica.bishal.triplescale.models.UserWrapper;
import com.softlica.bishal.triplescale.services.ApiService;
import com.softlica.bishal.triplescale.ui.viewmodel.HomeViewModel;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bishal on 7/8/2017.
 */

public class HomePresenter implements IPresenter {
    private HomeViewModel view;
    public Single<UserWrapper> userWrapperObservable;
    private Disposable disposable;
    Scheduler schedulers;
    public static List<User> users;

    private static boolean SORT_BY_AGE;
    private static boolean SORT_BY_NAME;

    @Inject
    ApiService service;

    @Inject
    public HomePresenter(HomeViewModel view) {
        this.view = view;
        schedulers = AndroidSchedulers.mainThread();
    }

    @Override
    public void onCreate() {
        // get observable and subscribe to it
        view.showProgressDialog();
        getUsers();
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        dispose();
    }

    public void dispose() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void getUsers() {
        userWrapperObservable = service.getUsers();
        disposable = userWrapperObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserWrapper>() {
                    @Override
                    public void onSuccess(UserWrapper value) {
                        users = value.getUsers();
                        if (users.size() > 0) {
                            view.displayData(value.getUsers());
                        } else {
                            view.displayNoData("No Data Available!");
                        }
                        // have to call on both place as it data is observed on seperate thread
                        view.dismissProgressDialog();
                    }
                    @Override
                    public void onError(Throwable e) {
                        view.showError("Could not load data");
                        view.displayNoData("Could not get data! Try again later");
                        view.dismissProgressDialog();
                    }
                });
    }

    // dispose the subscription and request new data
    public void refresh() {
        getUsers();
    }
    public void setService(ApiService service) {
        this.service = service;
    }

    // For sorting current users by age
    public void sortByAge() {
        if (users == null) return;
        if (SORT_BY_AGE == false) {
            SORT_BY_AGE = true;
            Collections.sort(users, (user, t1) -> Integer.parseInt(user.getDob()) - Integer.parseInt(t1.getDob()));
        } else {
            SORT_BY_AGE = false;
            Collections.sort(users, (user, t1) -> Integer.parseInt(t1.getDob()) - Integer.parseInt(user.getDob()));
        }
        view.displayData(users);
    }

    // For sorting current users by name
    public void sortByName() {
        if (users == null)
            return;

        if (SORT_BY_NAME == false) {
            SORT_BY_NAME = true;

            Collections.sort(users, (user, t1) -> user.getFullName().compareTo(t1.getFullName()));
        } else {
            SORT_BY_NAME = false;
            Collections.sort(users, (user, t1) -> (t1.getFullName().compareTo(user.getFullName())));
        }
        view.displayData(users);
    }
}
