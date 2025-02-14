package com.example.waymap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class homeActivity extends AppCompatActivity {

    private SearchView searchView;

    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    ImageView imageView = findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);
                    Toast.makeText(this, "Camera capture successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Camera capture canceled", Toast.LENGTH_SHORT).show();
                }
            });
    private String query;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        ImageView distanceCalculatorImage = findViewById(R.id.Imagebutton);
        TextView distanceCalculatorText = findViewById(R.id.textbutton);
        ImageView profileImage = findViewById(R.id.profilebutton);
        ImageView scenicStopsImage = findViewById(R.id.scenicimage);
        TextView scenicStopsText = findViewById(R.id.scenictext);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView cameraImage = findViewById(R.id.camerabutton);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView gmapButton = findViewById(R.id.gmapbutton);
        ImageView routeImage = findViewById(R.id.routeimage);
        TextView routeText = findViewById(R.id.routetext);
         ImageView hotelImage = findViewById(R.id.hotelImage);
         TextView hotelText = findViewById(R.id.hotelText);
         @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView feedbackImage = findViewById(R.id.feedback);
        TextView mButton = findViewById(R.id.mButton);
        mButton.setOnClickListener(view -> {
            Intent intent = new Intent(homeActivity.this, moreActivity.class);
            startActivity(intent);
        });
        feedbackImage.setOnClickListener(view -> {
            Intent intent = new Intent(homeActivity.this, FeedbackActivity.class);
            startActivity(intent);
        });

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
        distanceCalculatorImage.setOnClickListener(v -> navigateToActivity(DistanceActivity.class));
        distanceCalculatorText.setOnClickListener(v -> navigateToActivity(DistanceActivity.class));

        scenicStopsImage.setOnClickListener(v -> navigateToActivity(ScenicstopsActivity.class));
        scenicStopsText.setOnClickListener(v -> navigateToActivity(ScenicstopsActivity.class));

        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, profileActivity.class);
            startActivity(intent);
        });

        cameraImage.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(homeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(homeActivity.this, new String[]{Manifest.permission.CAMERA}, 101);
            } else {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraLauncher.launch(intent);
            }
        });

        gmapButton.setOnClickListener(this::openGoogleMaps);

        routeImage.setOnClickListener(v -> navigateToActivity(RouteplannerActivity.class));
        routeText.setOnClickListener(v -> navigateToActivity(RouteplannerActivity.class));

        ImageView menuButton = findViewById(R.id.menu);
        menuButton.setOnClickListener(view -> showPopupMenu(view));
        searchView = findViewById(R.id.searchView); // Add this line

        // Rest of your code...

        // Set the listener for searchView
        searchView = findViewById(R.id.searchView);

        // Set the listener for searchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Show a toast indicating the search query
                Toast.makeText(homeActivity.this, "Searching for: " + query, Toast.LENGTH_SHORT).show();

                // Handle search query and navigate to the correct activity
                navigateBasedOnSearchQuery(query);
                return false; // Prevent default action
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false; // Optionally, you can implement live search here if needed
            }
        });
    }

    private void navigateBasedOnSearchQuery(String query) {
        // Check the query and navigate to the appropriate activity
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
                // Add more cases as needed
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
            } else if (item.getItemId() == R.id.nav_feedbacks) {  // Handle Feedback menu click
                navigateToActivity(FeedbackAdapter.class);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }


    private void logoutUser(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(homeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
        startActivity(intent);

        Toast.makeText(view.getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
    }


}
