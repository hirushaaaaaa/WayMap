package com.example.waymap;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LiveActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        // Initialize MapView
        mapView = findViewById(R.id.mapView);

        // MapView requires a saved instance state to properly restore its state
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);  // This will trigger onMapReady when the map is ready
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Set initial position for the map (e.g., coordinates of Sri Lanka)
        LatLng sriLanka = new LatLng(7.8731, 80.7718); // Coordinates of Sri Lanka
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sriLanka, 10)); // Zoom level 10

        // Add a marker for Sri Lanka
        googleMap.addMarker(new MarkerOptions().position(sriLanka).title("Sri Lanka"));

        // Additional map settings if needed
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
