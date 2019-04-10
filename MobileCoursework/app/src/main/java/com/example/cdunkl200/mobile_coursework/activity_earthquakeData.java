//
// Name                 Callum Dunkley
// Student ID           S1510033
// Programme of Study   Computing
//

package com.example.cdunkl200.mobile_coursework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class activity_earthquakeData extends AppCompatActivity implements OnMapReadyCallback {
    TextView listdata1;
    TextView listdata2;
    TextView listdata3;
    TextView listdata4;
    TextView listdata5;
    TextView listdata6;
    private GoogleMap mMap;
    private String receivedLat;
    private String receivedLong;
    private String receivedLocation;
    private String receivedMagnitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_data);

        listdata1 = findViewById(R.id.listdata1);
        listdata2 = findViewById(R.id.listdata2);
        listdata3 = findViewById(R.id.listdata3);
        listdata4 = findViewById(R.id.listdata4);
        listdata5 = findViewById(R.id.listdata5);
        listdata6 = findViewById(R.id.listdata6);


        Intent intent = getIntent();
        receivedLocation =  intent.getStringExtra("location");
        String receivedDate =  intent.getStringExtra("date");
        receivedMagnitude =  intent.getStringExtra("mag");
        String receivedDepth =  intent.getStringExtra("depth");
        receivedLat =  intent.getStringExtra("lat");
        receivedLong =  intent.getStringExtra("long");


        listdata1.setText(receivedLocation);
        listdata2.setText(receivedDate);
        listdata3.setText(receivedMagnitude);
        listdata4.setText(receivedDepth);
        listdata5.setText(receivedLat);
        listdata6.setText(receivedLong);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //enable back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //getting back to listview
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng quakeMarker = new LatLng(Double.parseDouble(receivedLat), Double.parseDouble(receivedLong));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quakeMarker, 7));
        mMap.addMarker(new MarkerOptions().position(quakeMarker).title(receivedLocation + " " + receivedMagnitude));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(quakeMarker));
    }
}