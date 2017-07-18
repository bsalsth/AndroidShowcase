package com.softlica.bishal.triplescale.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.softlica.bishal.triplescale.R;
import com.softlica.bishal.triplescale.di.DaggerPresenterComponent;
import com.softlica.bishal.triplescale.di.PresenterModule;
import com.softlica.bishal.triplescale.main.App;
import com.softlica.bishal.triplescale.models.EmptyRecyclerViewAdapter;
import com.softlica.bishal.triplescale.models.User;
import com.softlica.bishal.triplescale.models.UserRecycleViewAdapter;
import com.softlica.bishal.triplescale.presenters.HomePresenter;
import com.softlica.bishal.triplescale.ui.viewmodel.HomeViewModel;
import com.softlica.bishal.triplescale.utility.U;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HomeViewModel, SwipeRefreshLayout.OnRefreshListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @Inject
    HomePresenter presenter;

    @BindView(R.id.progressBar2)
    ProgressBar progressBar;

    @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    UserRecycleViewAdapter adapter;
    EmptyRecyclerViewAdapter emptyRecyclerViewAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    // variables for location
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        DaggerPresenterComponent.builder()
                .presenterModule(new PresenterModule(this))
                .apiComponent(App.getApiComponent())
                .build().inject(this);
        adapter = new UserRecycleViewAdapter(getApplicationContext());
        // set custom listener for onclick event in recycle view
        adapter.setListener(user -> {
            Intent i = new Intent(this, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CURRENT_USER", user);
            i.putExtras(bundle);
            startActivity(i);
        });

        refreshLayout.setOnRefreshListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        if (!(U.isGooglePlayServicesAvailable(this)))
            finish();

        presenter.onCreate();
    }

// viewmodel implementation

    @Override
    public void displayData(List<User> users) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        adapter.setData(users);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayNoData(String s) {
        emptyRecyclerViewAdapter = new EmptyRecyclerViewAdapter(s);
        recyclerView.setAdapter(emptyRecyclerViewAdapter);
    }

    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
// viewmodel implementation ends here


    // android lifecycle implementations
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
        refreshLayout.setRefreshing(false);
    }

    // android lifecycle implementations ends here

    // menu starts here
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_sort_name) {
            presenter.sortByName();
            return true;
        }
        if (id == R.id.action_sort_age) {
            presenter.sortByAge();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //    menu ends here
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    startLocationUpdates();
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT)
                            .show();

                } else {
                    // Permission Denied
                    Toast.makeText(this, "This app needs location service to work properly", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void startLocationUpdates() {
        int hasLocationPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }

        if (mGoogleApiClient.isConnected()) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mCurrentLocation != null) {
                int lat = (int) (mCurrentLocation.getLatitude());
                int lng = (int) (mCurrentLocation.getLongitude());
                Location currentLocation = new Location("current");
                currentLocation.setLatitude(mCurrentLocation.getLatitude());
                currentLocation.setLongitude(mCurrentLocation.getLatitude());
                App.setCurrentLocation(currentLocation);
            }
        }
    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    // IMPLEMENTATION FOR INTERFACE
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
