package com.example.waymap;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class homeActivity extends AppCompatActivity {

    // Define an ActivityResultLauncher for the camera
    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // Handle the result
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    ImageView imageView = findViewById(R.id.imageView);  // Replace with your ImageView reference
                    imageView.setImageBitmap(bitmap);  // Set the captured image to imageView
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
                // Start the camera using ActivityResultLauncher
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraLauncher.launch(intent);
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

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                navigateToActivity(homeActivity.class);
                return true;
            } else if (item.getItemId() == R.id.nav_settings) {
                navigateToActivity(SettingsActivity.class);
                return true;
            } else if (item.getItemId() == R.id.nav_logout) {
                // Log out logic here
                logoutUser(view);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    private void logoutUser(View view) {
        // Clear stored user data, for example in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();




        Intent intent = new Intent(homeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
        startActivity(intent);

        // Optionally, show a logout confirmation message
        Toast.makeText(view.getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
    }

}
