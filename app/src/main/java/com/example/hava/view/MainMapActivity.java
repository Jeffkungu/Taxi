package com.example.hava.view;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hava.R;
import com.example.hava.model.Trip;
import com.example.hava.utils.Constants;
import com.example.hava.viewmodel.MainViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean permissionGranted = false;
    private FusedLocationProviderClient locationProviderClient;
    private MarkerOptions markerOptions;
    private MainViewModel mainViewModel;
    private ArrayList<LatLng> latLngArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
        Button button = findViewById(R.id.buttonMainMap);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.checkIfDbIsEmpty();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainMapActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        checkPermissions();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in user location and move the camera
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
        }

        displayAllTrips();
    }

    private void displayAllTrips() {
        mainViewModel.getTrips().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                latLngArrayList = new ArrayList<>();
                for(Trip trip : trips){
                    LatLng latLng = new LatLng(trip.getPickupLat(), trip.getPickupLng());
                    latLngArrayList.add(latLng);
                }
                showPickUpLocations();
            }
        });
    }

    private void showPickUpLocations() {
        for (int i = 0; i < latLngArrayList.size(); i++){
            MarkerOptions marker = new MarkerOptions().position(latLngArrayList.get(i));
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pick_location));
            mMap.addMarker(marker);
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
                .findFragmentById(R.id.map);
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
                                LatLng latLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
                                moveCameraView(latLng, 11f, "Current Location");
                            }
                        } else {
                        }
                    }
                });
            }
        } catch (SecurityException e) {

        }
    }

    public void moveCameraView(LatLng latLng, float zoom, String locationName) {
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        markerOptions = new MarkerOptions()
                .position(latLng)
                .title(locationName);
        mMap.addMarker(markerOptions);
    }


}
