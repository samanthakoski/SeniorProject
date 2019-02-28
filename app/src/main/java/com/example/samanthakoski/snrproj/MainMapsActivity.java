package com.example.samanthakoski.snrproj;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;


public class MainMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CameraPosition camPos;
    private PlaceDetectionClient placeDetectionClient;
    private FirebaseUser user;
    private Boolean locPermGranted;
    private FusedLocationProviderClient fusedLocProvClient;
    private LatLng defaultLoc = new LatLng(35.2828, -120.6596);
    private final float defaultZoom = 15.0f;
    private final int PERM_REQ_ACC_FINE_LOC = 1;
    private Location lastKnownLoc = null;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            lastKnownLoc = savedInstanceState.getParcelable(KEY_LOCATION);
            camPos = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        setContentView(R.layout.activity_main_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocProvClient = LocationServices.getFusedLocationProviderClient(this);
        placeDetectionClient = Places.getPlaceDetectionClient(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
        // Add a marker in Sydney and move the camera
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        locPermGranted = false;
        switch (requestCode) {
            case PERM_REQ_ACC_FINE_LOC: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locPermGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void getLocationPermission() {
        if (ContextCompat
                .checkSelfPermission(
                        this.getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            locPermGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERM_REQ_ACC_FINE_LOC);
        }
    }

    private void updateLocationUI() {
        if (mMap == null) return;
        try {
            mMap.setMyLocationEnabled(locPermGranted);
            mMap.getUiSettings().setMyLocationButtonEnabled(locPermGranted);
            if (!locPermGranted) {
                lastKnownLoc = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            // do nothing
        }
    }

    private void getDeviceLocation() {
        try {
            if (locPermGranted) {
                Task locResult = fusedLocProvClient.getLastLocation();
                if (locResult == null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLoc, defaultZoom));
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                } else {
                    locResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful()) {
                                lastKnownLoc = task.getResult();
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLoc.getLatitude(),
                                                lastKnownLoc.getLongitude()), defaultZoom));
                            } else {
                                mMap.moveCamera(CameraUpdateFactory
                                        .newLatLngZoom(defaultLoc, defaultZoom));
                                mMap.getUiSettings().setMyLocationButtonEnabled(false);

                        }
                    }});
                }
            }
        } catch (SecurityException e) {
            // do nothing
        }
    }
}
