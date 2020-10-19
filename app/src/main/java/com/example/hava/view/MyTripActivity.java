package com.example.hava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import com.example.hava.R;
import com.example.hava.databinding.ActivityMyTripBinding;
import com.example.hava.model.Trip;
import com.example.hava.utils.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MyTripActivity extends FragmentActivity implements OnMapReadyCallback {
    private ActivityMyTripBinding activityMyTripBinding;
    private Trip trip;
    private GoogleMap mMap;
    private boolean permissionGranted = false;
    private FusedLocationProviderClient locationProviderClient;
    private MarkerOptions markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Trip Details");

        activityMyTripBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_trip);
        checkPermissions();

        Intent intent = getIntent();

        if(intent.hasExtra(Constants.PARSED_TRIP)) {
            trip = intent.getParcelableExtra(Constants.PARSED_TRIP);
            activityMyTripBinding.setTrip(trip);
        }

    }

    private void checkPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true;
                initialiseMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    private void initialiseMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.myTripMap);
        mapFragment.getMapAsync(this);
    }

    private void getUserLocation() {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (permissionGranted) {
                Task task = locationProviderClient.getLastLocation();
                task.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Location userLocation = (Location) task.getResult();
                            if (userLocation != null){
//                                LatLng latLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
//                                moveCameraView(latLng, 15f, "Current Location");
                            }
                        } else {
                        }
                    }
                });
            }
        } catch (SecurityException e) {

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (permissionGranted) {
            getUserLocation();
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            LatLng latLng1 = new LatLng(trip.getPickupLat(), trip.getPickupLng());
            LatLng latLng2 = new LatLng(trip.getDropoffLat(), trip.getDropoffLng());
            ArrayList<LatLng> latLngArrayList = new ArrayList<>();
            latLngArrayList.add(latLng1);
            latLngArrayList.add(latLng2);
            drawMarker(latLngArrayList);

        }
    }

    private void drawMarker(ArrayList<LatLng> l) {
        // Creating an instance of MarkerOptions

        PolylineOptions options = new PolylineOptions();
        options.color(Color.BLUE);

        for (int i = 0; i < l.size(); i++) {
            options.add(l.get(i));
            MarkerOptions marker = new MarkerOptions().position(l.get(i));
            if(i==0){
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pick_location));
            } else {
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            }


            // Adding marker on the Google Map
            mMap.addMarker(marker);
        }

        mMap.addPolyline(options);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(l.get(0).latitude, l.get(0).longitude)).zoom(18).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

}
