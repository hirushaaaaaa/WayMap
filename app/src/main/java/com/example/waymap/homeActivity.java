package com.example.waymap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.annotations.Nullable;

public class homeActivity extends AppCompatActivity {

    // Define an ActivityResultLauncher for the camera
    // Define an ActivityResultLauncher for the camera
    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Toast.makeText(this, "Camera capture successful!", Toast.LENGTH_SHORT).show();
                    // Optionally close the current activity after capturing the image
                    finish(); // This will close the current homeActivity
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

        ImageView gmapButton = findViewById(R.id.gmapbutton);
        ImageView routeImage = findViewById(R.id.routeimage);
        TextView routeText = findViewById(R.id.routetext);

        Button mButton = findViewById(R.id.mButton);
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

        cameraImage.setOnClickListener(v -> {
            // Camera permission check
            if (ContextCompat.checkSelfPermission(homeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(homeActivity.this, new String[]{Manifest.permission.CAMERA}, 101);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraLauncher.launch(intent);  // Use the camera launcher to start the camera intent
            }
        });

        gmapButton.setOnClickListener(this::openGoogleMaps);

        // Add listeners for Route Planner
        routeImage.setOnClickListener(v -> navigateToActivity(RouteplannerActivity.class));
        routeText.setOnClickListener(v -> navigateToActivity(RouteplannerActivity.class));

        ImageView menuButton = findViewById(R.id.menu);
        menuButton.setOnClickListener(view -> showPopupMenu(view));
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

    // Generic method to navigate to an activity
    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(homeActivity.this, activityClass);
        startActivity(intent);
    }


    private void openCamera(View view) {
        // Check if there's an app that can handle the camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            cameraLauncher.launch(cameraIntent);
        } else {
            Toast.makeText(this, "No camera app found on this device", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();

        String query = getIntent().getStringExtra("query");
        if (!TextUtils.isEmpty(query)) {
            Toast.makeText(this, "Last searched: " + query, Toast.LENGTH_SHORT).show();
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());

        // Handle menu item clicks
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                navigateToActivity(homeActivity.class);
                return true;
            } else if (item.getItemId() == R.id.nav_settings) {
                navigateToActivity(SettingsActivity.class);
                return true;
            } else if (item.getItemId() == R.id.nav_logout) {
                Toast.makeText(view.getContext(), "Logged out", Toast.LENGTH_SHORT).show();
                // Add logout logic
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    // Handle the result of the camera intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ImageView imageView = findViewById(R.id.imageView);  // Replace with your ImageView reference
            imageView.setImageBitmap(bitmap);  // Set the captured image to imageView
        }
    }
}
