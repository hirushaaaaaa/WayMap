package com.example.waymap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class homeActivity extends AppCompatActivity {

    private Uri currentImageUri;
    private static final int PERMISSION_REQUEST_CODE = 101;

    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (currentImageUri != null) {
                        try {
                            // Mark the image as not pending (Android 10+ requires this)
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.IS_PENDING, 0);
                            getContentResolver().update(currentImageUri, values, null, null);

                            // Notify Media Scanner (For Android 9 and below)
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, currentImageUri));
                            }

                            Toast.makeText(this, "Image saved successfully!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Error saving image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // Clean up failed capture
                    if (currentImageUri != null) {
                        getContentResolver().delete(currentImageUri, null, null);
                        currentImageUri = null;
                    }
                    Toast.makeText(this, "Image capture cancelled", Toast.LENGTH_SHORT).show();
                }
            });

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

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
        ImageView hotelImage = findViewById(R.id.hotelImage);
        TextView hotelText = findViewById(R.id.hotelText);
        ImageView feedbackImage = findViewById(R.id.feedback);
        TextView mButton = findViewById(R.id.mButton);
        ImageView mButtonl = findViewById(R.id.mButtonl);

        // Set click listeners for "More" buttons
        mButton.setOnClickListener(view -> {
            Intent intent = new Intent(homeActivity.this, moreActivity.class);
            startActivity(intent);
        });

        mButtonl.setOnClickListener(view -> {
            Intent intent = new Intent(homeActivity.this, moreActivity.class);
            startActivity(intent);
        });

        // Feedback button listener
        feedbackImage.setOnClickListener(view -> {
            Intent intent = new Intent(homeActivity.this, FeedbackActivity.class);
            startActivity(intent);
        });

        // Hotel buttons listeners
        hotelImage.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(homeActivity.this, HotelActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(homeActivity.this, "Error opening hotel details", Toast.LENGTH_SHORT).show();
            }
        });

        hotelText.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, HotelActivity.class);
            startActivity(intent);
        });

        // Distance calculator listeners
        distanceCalculatorImage.setOnClickListener(v -> navigateToActivity(DistanceActivity.class));
        distanceCalculatorText.setOnClickListener(v -> navigateToActivity(DistanceActivity.class));

        // Scenic stops listeners
        scenicStopsImage.setOnClickListener(v -> navigateToActivity(ScenicstopsActivity.class));
        scenicStopsText.setOnClickListener(v -> navigateToActivity(ScenicstopsActivity.class));

        // Profile button listener
        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, profileActivity.class);
            startActivity(intent);
        });

        // Camera button listener
        cameraImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(homeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(homeActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
            } else {
                captureImage();
            }
        });

        // Google Maps button listener
        gmapButton.setOnClickListener(this::openGoogleMaps);

        // Route planner listeners
        routeImage.setOnClickListener(v -> navigateToActivity(RouteplannerActivity.class));
        routeText.setOnClickListener(v -> navigateToActivity(RouteplannerActivity.class));

        // Menu button setup
        ImageView menuButton = findViewById(R.id.menu);
        menuButton.setOnClickListener(this::showPopupMenu);

        // Search view setup
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(homeActivity.this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
                navigateBasedOnSearchQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void captureImage() {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "WayMap_" + System.currentTimeMillis() + ".jpg");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/WayMap");
            values.put(MediaStore.Images.Media.IS_PENDING, 1);

            ContentResolver resolver = getContentResolver();
            currentImageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            if (currentImageUri != null) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, currentImageUri);

                // Grant URI permissions to the camera app
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                cameraLauncher.launch(intent);
            } else {
                Toast.makeText(this, "Failed to create image file", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error launching camera: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateBasedOnSearchQuery(String query) {
        if (query != null) {
            switch (query.toLowerCase()) {
                case "route planner":
                    navigateToActivity(RouteplannerActivity.class);
                    break;
                case "scenic stops":
                    navigateToActivity(ScenicstopsActivity.class);
                    break;
                case "profile":
                    navigateToActivity(profileActivity.class);
                    break;
                case "hotel":
                    navigateToActivity(HotelActivity.class);
                    break;
                case "transport":
                    navigateToActivity(TransportActivity.class);
                    break;
                case "fuel management":
                    navigateToActivity(FuelManagementActivity.class);
                    break;
                case "sri lankan airline":
                    navigateToActivity(SrilankanActivity.class);
                    break;
                case "local dishes":
                    navigateToActivity(LocaldishesActivity.class);
                    break;
                case "adventure and activities":
                    navigateToActivity(ExploreActivity.class);
                    break;
                default:
                    Toast.makeText(homeActivity.this, "No results found for: " + query, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
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
                logoutUser(view);
                return true;
            } else if (item.getItemId() == R.id.nav_SOS) {
                navigateToActivity(SOSActivity.class);
                return true;
            } else if (item.getItemId() == R.id.nav_feedbacks) {
                navigateToActivity(ViewFeedbackActivity.class);
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private void logoutUser(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(homeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        Toast.makeText(view.getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            } else {
                Toast.makeText(this, "Camera permission is required to capture photos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}