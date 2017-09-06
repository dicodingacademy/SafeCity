package com.dicoding.safecity.home;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.dicoding.safecity.R;
import com.dicoding.safecity.model.PoiResponse;
import com.dicoding.safecity.model.TicketResponse;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainView, OnMapReadyCallback {
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private static final int RADIUS = 1000;
    private GoogleMap map;
    private boolean isPublicPlace;
    private LocationManager locationManager;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public MainPresenter presenter;
    @BindView(R.id.spinner)
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkLocationPermission();
        initializePresenter();
        initializeMaps();
        initializeSpinner();
    }

    private void initializeSpinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getSelectedItemId() == 0){
                    presenter.getTicketList(RADIUS);
                } else if (adapterView.getSelectedItemId() == 1){
                    isPublicPlace = true;
                    presenter.getPoiList(RADIUS);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void initializePresenter() {
        Context context = this;
        presenter = new MainPresenter(context, this);
        presenter.getTicketList(1000);
    }

    private void initializeMaps() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                R.id.map_view)).getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME,
                MIN_DISTANCE, mLocationListener);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            LatLng latLng = new LatLng(lat, lng);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            map.animateCamera(cameraUpdate);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}
        @Override
        public void onProviderEnabled(String s) {}
        @Override
        public void onProviderDisabled(String s) {}
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    @Override
    public void setVisibilityProgressBar(int visibility) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void getEventTicketSuccess(Response<List<TicketResponse>> response) {
        map.clear();
        for (int i = 0; i < response.body().size(); i++){
            String categoryName = response.body().get(i).getCategoryName();
            String priorityName = response.body().get(i).getPriorityName();

            BitmapDescriptor bitmapDescriptor = null;
            if (Objects.equals(priorityName, "Low")){
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            } else if (Objects.equals(priorityName, "Medium")){
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
            } else if (Objects.equals(priorityName, "High")){
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            }
            LatLng location = new LatLng(response.body().get(i).getLatitude(), response.body().get(i).getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(location)
                    .title(categoryName)
                    .snippet("Priority : " + priorityName)
                    .icon(bitmapDescriptor));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void getEventPoiSuccess(Response<List<PoiResponse>> response) {
        map.clear();
        for (int i = 0; i < response.body().size(); i++){
            BitmapDescriptor bitmapDescriptor = null;
            if (Objects.equals(response.body().get(i).getPointInterestTypeName(), "Hotel")){
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.hotel);
            } else if(Objects.equals(response.body().get(i).getPointInterestTypeName(), "Restaurant")){
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.restaurant);
            } else if (Objects.equals(response.body().get(i).getPointInterestTypeName(), "Match Venue")){
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.shootingrange);
            } else if (Objects.equals(response.body().get(i).getPointInterestTypeName(), "Public Transportation")){
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.busstop);
            } else if (Objects.equals(response.body().get(i).getPointInterestTypeName(), "Shopping Mall")){
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.mall);
            } else if (Objects.equals(response.body().get(i).getPointInterestTypeName(), "Mosque")){
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.mosquee);
            } else if (Objects.equals(response.body().get(i).getPointInterestTypeName(), "Toilet")){
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.toilets);
            } else if (Objects.equals(response.body().get(i).getPointInterestTypeName(), "Parking")){
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.parkinggarage);
            } else if (Objects.equals(response.body().get(i).getPointInterestTypeName(), "Others")){
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.building);
            } else if (Objects.equals(response.body().get(i).getPointInterestTypeName(), "ATM")){
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.atm);
            } else if (Objects.equals(response.body().get(i).getPointInterestTypeName(), "Ticket Booth")){
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ticket);
            } else if (Objects.equals(response.body().get(i).getPointInterestTypeName(), "Security")){
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.army);
            }
            LatLng location = new LatLng(response.body().get(i).getLatitude(), response.body().get(i).getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(location)
                    .title(response.body().get(i).getName())
                    .snippet(response.body().get(i).getDescription())
                    .icon(bitmapDescriptor));
            if (isPublicPlace){
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle(marker.getTitle());
                        alertDialog.setMessage(marker.getSnippet());
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Get Direction",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                Uri.parse("http://maps.google.com/maps?daddr=" + marker.getPosition().latitude + "," + marker.getPosition().longitude));
                                        startActivity(intent);
                                    }
                                });
                        alertDialog.show();
                        return false;
                    }
                });
            }
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission. ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission. ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission. ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission. ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME,
                                MIN_DISTANCE, mLocationListener);
                    }

                } else {
                    moveTaskToBack(true);
                }
            }

        }
    }

}
