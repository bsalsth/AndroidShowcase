package com.softlica.bishal.triplescale.presenters;

import com.softlica.bishal.triplescale.models.User;
import com.softlica.bishal.triplescale.models.UserWrapper;
import com.softlica.bishal.triplescale.services.ApiService;
import com.softlica.bishal.triplescale.ui.viewmodel.HomeViewModel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by bishal on 7/15/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {

    HomePresenter presenter;
    @Mock
    ApiService service;

    @Mock
    HomeViewModel viewModel;

    UserWrapper userWrapper;

    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };
        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new HomePresenter(viewModel);
        presenter.setService(service);
        userWrapper = new UserWrapper();
    }


    @Test
    public void displayUsers() {

        User user = Mockito.mock(User.class);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userWrapper.setUsers(userList);
        Mockito.when(service.getUsers()).thenReturn(Single.just(userWrapper));
        presenter.getUsers();
        Mockito.verify(viewModel).displayData(userList);
    }

    @Test
    public void displayNoUsers() throws Exception {
        userWrapper.setUsers(Collections.EMPTY_LIST);
        Mockito.when(service.getUsers()).thenReturn(Single.just(userWrapper));
        presenter.getUsers();
        Mockito.verify(viewModel).displayNoData("No Data Available!");

    }


}