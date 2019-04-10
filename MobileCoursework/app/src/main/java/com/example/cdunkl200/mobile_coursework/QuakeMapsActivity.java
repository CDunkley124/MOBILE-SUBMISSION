//
// Name                 Callum Dunkley
// Student ID           S1510033
// Programme of Study   Computing
//

package com.example.cdunkl200.mobile_coursework;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class QuakeMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<Earthquake> mapQuakes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake_maps);

        Intent intent = getIntent();


       mapQuakes =  (ArrayList<Earthquake>)intent.getSerializableExtra("StringKey");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng quakeMarker = new LatLng(55.798103, 0.464723);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quakeMarker, 5));

//        for (int i = 0; i < mapQuakes.size(); i++) {
//            createMarker(mapQuakes.get(i).getLat(), mapQuakes.get(i).getLong(), mapQuakes.get(i).getLocation());
//        }

        for (int i=0; i<mapQuakes.size(); i++) {
            if (mapQuakes.get(i).getMagnitude() >= -1 && mapQuakes.get(i).getMagnitude() < 0.5){
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(mapQuakes.get(i).getLat(), mapQuakes.get(i).getLong()))
                        .anchor(0.5f, 0.5f)
                        .title(mapQuakes.get(i).getLocation() + " " + mapQuakes.get(i).getMagnitude())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            } else if (mapQuakes.get(i).getMagnitude() >= 0.5 && mapQuakes.get(i).getMagnitude() < 1.5){
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(mapQuakes.get(i).getLat(), mapQuakes.get(i).getLong()))
                        .anchor(0.5f, 0.5f)
                        .title(mapQuakes.get(i).getLocation() + " " + mapQuakes.get(i).getMagnitude())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            } else if (mapQuakes.get(i).getMagnitude() >= 1.5 && mapQuakes.get(i).getMagnitude() < 2){
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(mapQuakes.get(i).getLat(), mapQuakes.get(i).getLong()))
                        .anchor(0.5f, 0.5f)
                        .title(mapQuakes.get(i).getLocation() + " " + mapQuakes.get(i).getMagnitude())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            } else if (mapQuakes.get(i).getMagnitude() >= 2){
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(mapQuakes.get(i).getLat(), mapQuakes.get(i).getLong()))
                        .anchor(0.5f, 0.5f)
                        .title(mapQuakes.get(i).getLocation() + " " + mapQuakes.get(i).getMagnitude())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }


    }

}
