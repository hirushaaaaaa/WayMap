package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class homeActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView distanceCalculatorImage = findViewById(R.id.Imagebutton);
        TextView distanceCalculatorText = findViewById(R.id.textbutton);
        ImageView profileImage = findViewById(R.id.profilebutton);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView cameraImage = findViewById(R.id.camerabutton);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView searchImage = findViewById(R.id.searchbutton);
        ImageView gmapButton = findViewById(R.id.gmapbutton);
        Button moreButton = findViewById(R.id.moreButton);

        distanceCalculatorImage.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, DistanceActivity.class);
            startActivity(intent);
        });

        distanceCalculatorText.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, DistanceActivity.class);
            startActivity(intent);
        });

        moreButton.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, moreActivity.class);
            startActivity(intent);
        });

        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, profileActivity.class);
            startActivity(intent);
        });

        searchImage.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, SearchActivity.class);
            startActivity(intent);

        });

        cameraImage.setOnClickListener(v -> openCamera());

        gmapButton.setOnClickListener(v -> openGoogleMaps());
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        else
            Toast.makeText(this, "No camera app found on this device", Toast.LENGTH_SHORT).show();
    }

    private void openGoogleMaps() {
        String geoUri = "geo:0,0?q=Colombo";
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps app is not installed", Toast.LENGTH_SHORT).show();
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
}
