package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ExploreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_explore);

        // Find the button by its ID
        Button safariButton = findViewById(R.id.safaributton);
        Button surfingButton = findViewById(R.id.surfingbutton);
        Button campingButton = findViewById(R.id.campingbutton);
        Button divingButton = findViewById(R.id.divingbutton);
        Button waterraftingButton = findViewById(R.id.waterrafytingbutton);
        Button cultureButton = findViewById(R.id.culturebutton);
        Button dolphinButton = findViewById(R.id.whalebutton);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView adminImageView = findViewById(R.id.adddd);

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

        // Set an OnClickListener on the button
        safariButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExploreActivity.this, SafariActivity.class);
            startActivity(intent);
        });
        surfingButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExploreActivity.this, SurfingActivity.class);
            startActivity(intent);
        });
        campingButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExploreActivity.this, CampingActivity.class);
            startActivity(intent);
        });
        divingButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExploreActivity.this, DivingActivity.class);
            startActivity(intent);
        });
        waterraftingButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExploreActivity.this, WaterraftingActivity.class);
            startActivity(intent);
        });
        cultureButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExploreActivity.this, CultureActivity.class);
            startActivity(intent);
        });
        dolphinButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExploreActivity.this, DolphinwatchingActivity.class);
            startActivity(intent);
        });

        // Admin click to add a new adventure activity
        adminImageView.setOnClickListener(v -> {
            if (isAdmin) {
                showAddActivityDialog();
            }
        });
    }

    // Method to show a dialog for the admin to add a new adventure activity
    private void showAddActivityDialog() {
        // Create EditTexts for entering the activity name and description
        final EditText activityNameEditText = new EditText(this);
        activityNameEditText.setHint("Enter Activity Name");

        final EditText activityDescriptionEditText = new EditText(this);
        activityDescriptionEditText.setHint("Enter Activity Description");

        // Set up the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Adventure Activity")
                .setView(activityNameEditText)
                .setView(activityDescriptionEditText)
                .setPositiveButton("Add", (dialog, which) -> {
                    String activityName = activityNameEditText.getText().toString().trim();
                    String activityDescription = activityDescriptionEditText.getText().toString().trim();

                    // Check if the activity name and description are not empty
                    if (!activityName.isEmpty() && !activityDescription.isEmpty()) {
                        // Save the new activity (for now, we can store it in SharedPreferences)
                        SharedPreferences sharedPreferences = getSharedPreferences("ActivitiesPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(activityName, activityDescription); // Save activity
                        editor.apply(); // Commit the changes

                        Toast.makeText(ExploreActivity.this, "Activity added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ExploreActivity.this, "Both fields are required", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
