package com.example.waymap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ScenicstopsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenicstops);

        // Find ImageViews
        ImageView imageAnuradhapura = findViewById(R.id.imageanuradhapura);
        ImageView imageSinharaja = findViewById(R.id.imagesinharaja);
        ImageView imageRuwanmeliseya = findViewById(R.id.imageruwanmeliseya);
        ImageView imageGalleport = findViewById(R.id.imagegalleport);
        ImageView imageElla = findViewById(R.id.imageella);
        ImageView imageDambulla = findViewById(R.id.imagedambulla);
        ImageView imageMirissa = findViewById(R.id.imagedamirissa);
        ImageView imageSripada = findViewById(R.id.imagesripadasthanaya);

        // Admin-only ImageView
        ImageView adminImageView = findViewById(R.id.add);

        // Get stored login details
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false); // Retrieve admin status

        // Debug: Check if the admin status is retrieved correctly
        Log.d("DEBUG", "Admin Status: " + isAdmin);

        if (isAdmin) {
            Log.d("DEBUG", "Admin ImageView should be VISIBLE.");
            adminImageView.setVisibility(View.VISIBLE);
        } else {
            Log.d("DEBUG", "Admin ImageView should be GONE.");
            adminImageView.setVisibility(View.GONE);
        }

        // Set click listeners to navigate to different activities
        imageAnuradhapura.setOnClickListener(v -> startActivity(new Intent(this, AnuradhapuraActivity.class)));
        imageSinharaja.setOnClickListener(v -> startActivity(new Intent(this, sinharajaActivity.class)));
        imageRuwanmeliseya.setOnClickListener(v -> startActivity(new Intent(this, RuwanmeliseyaActivity.class)));
        imageGalleport.setOnClickListener(v -> startActivity(new Intent(this, GalleportActivity.class)));
        imageElla.setOnClickListener(v -> startActivity(new Intent(this, EllaActivity.class)));
        imageDambulla.setOnClickListener(v -> startActivity(new Intent(this, DambullaActivity.class)));
        imageMirissa.setOnClickListener(v -> startActivity(new Intent(this, MirissaActivity.class)));
        imageSripada.setOnClickListener(v -> startActivity(new Intent(this, SripadasthanayaActivity.class)));
    }
}
