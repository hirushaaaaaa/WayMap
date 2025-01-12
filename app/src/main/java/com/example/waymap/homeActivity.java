package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class homeActivity extends AppCompatActivity {

    // Define an ActivityResultLauncher for the camera
    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Toast.makeText(this, "Camera capture successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Camera capture canceled", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        ImageView distanceCalculatorImage = findViewById(R.id.Imagebutton);
        TextView distanceCalculatorText = findViewById(R.id.textbutton);
        ImageView profileImage = findViewById(R.id.profilebutton);
        ImageView scenicStopsImage = findViewById(R.id.scenicimage);
        TextView scenicStopsText = findViewById(R.id.scenictext);
        ImageView cameraImage = findViewById(R.id.camerabutton);
        ImageView searchImage = findViewById(R.id.searchbutton);
        ImageView gmapButton = findViewById(R.id.gmapbutton);
        ImageView routeImage = findViewById(R.id.routeimage);
        TextView routeText = findViewById(R.id.routetext);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button mButton = findViewById(R.id.mButton);
        mButton.setOnClickListener(view -> {
            Intent intent = new Intent(homeActivity.this, moreActivity.class);
            startActivity(intent);
        });

        // Set up listeners
        distanceCalculatorImage.setOnClickListener(v -> navigateToActivity(DistanceActivity.class));
        distanceCalculatorText.setOnClickListener(v -> navigateToActivity(DistanceActivity.class));

        scenicStopsImage.setOnClickListener(v -> navigateToActivity(ScenicstopsActivity.class));
        scenicStopsText.setOnClickListener(v -> navigateToActivity(ScenicstopsActivity.class));

        profileImage.setOnClickListener(v -> navigateToActivity(profileActivity.class));
        searchImage.setOnClickListener(v -> navigateToActivity(SearchActivity.class));

        cameraImage.setOnClickListener(this::openCamera);
        gmapButton.setOnClickListener(this::openGoogleMaps);

        // Add listeners for Route Planner
        routeImage.setOnClickListener(v -> navigateToActivity(RouteplannerActivity.class));
        routeText.setOnClickListener(v -> navigateToActivity(RouteplannerActivity.class));
    }

    private void openGoogleMaps(View view) {
        String geoUri = "geo:0,0?q=Colombo";
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps app is not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCamera(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            cameraLauncher.launch(cameraIntent);
        } else {
            Toast.makeText(this, "No camera app found on this device", Toast.LENGTH_SHORT).show();
        }
    }

    // Generic method to navigate to an activity
    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(homeActivity.this, activityClass);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String query = getIntent().getStringExtra("query");
        if (!TextUtils.isEmpty(query)) {
            Toast.makeText(this, "Last searched: " + query, Toast.LENGTH_SHORT).show();
        }
    }

}
