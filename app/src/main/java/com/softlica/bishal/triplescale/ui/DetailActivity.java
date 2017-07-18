package com.softlica.bishal.triplescale.ui;

import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.softlica.bishal.triplescale.R;
import com.softlica.bishal.triplescale.main.App;
import com.softlica.bishal.triplescale.models.User;
import com.softlica.bishal.triplescale.utility.U;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap gMap;
    SupportMapFragment mapFragment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_img)
    ImageView userImage;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.distance_away)
    TextView distanceAway;
    @BindView(R.id.address)
    TextView address;
    User user;
    LatLng latLng;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        user = (User) b.getSerializable("CURRENT_USER");
        if (user != null) {
            Picasso.with(this)
                    .load(user.getPicture().getLarge()).placeholder(R.mipmap.profile)
                    .error(R.mipmap.profile)
                    .fit()
                    .into(userImage);
            User.Name name = user.getName();
            // dob is replaced by age and string is joined for this demo
            age.setText(user.getDob() + " years");
            address.setText(user.getFullAddress());

            latLng = U.geocodeAddress(user.getFullAddress());
            // get location of current user
            Location userlocation = U.getLocationObject(latLng);
            double milesAway = U.getDistance(userlocation, App.getCurrentLocation());
            user.setDistance(((milesAway > 9000) ? "Too Far" : milesAway + "Mi"));
            distanceAway.setText(user.getDistance());
            setTitle(U.getFullName(name));
        }
        mapFragment.getMapAsync(this);

        fab.setOnClickListener(view -> {
            Toast.makeText(this, "Calling ...", Toast.LENGTH_SHORT).show();
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.gMap = googleMap;
        Geocoder geocoder = new Geocoder(this);

        // Get latlng from user address
        googleMap.setOnMarkerClickListener(this);
        gMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(user.getFullAddress())
                .snippet(user.getLocation().getState())
                .visible(true));

        CameraPosition currentView = CameraPosition.builder()
                .target(latLng)
                .zoom(10)
                .bearing(0)
                .tilt(45)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(currentView));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
