package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class sinharajaActivity extends AppCompatActivity {

    private TextView isurumunutext; // TextView to be edited by admin

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rainforest);

        // Enable the back button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Back Button Listener
        Button backButton = findViewById(R.id.backButton6);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(sinharajaActivity.this, ScenicstopsActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        });

        // Find ImageView for Admin Edit
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView adminImageView = findViewById(R.id.add5);
        isurumunutext = findViewById(R.id.isurumunitext); // TextView that admin will edit

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

        // Set click listener for the admin ImageView (add5) to edit or add the text
        adminImageView.setOnClickListener(v -> {
            if (isAdmin) {
                showEditDialog();
            }
        });

        // Open Sinharaja location in Google Maps
        TextView locsinharajaya = findViewById(R.id.locsinharajaya);
        locsinharajaya.setOnClickListener(v -> {
            // Coordinates for Sinharaja Rainforest
            String geoUri = "geo:6.4069,80.4995?q=Sinharaja Rainforest";
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            mapIntent.setPackage("com.google.android.apps.maps"); // Ensure Google Maps is used
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Fallback if Google Maps is not installed
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri)));
            }
        });
    }

    // Method to show a dialog for the admin to edit or add text
    private void showEditDialog() {
        // Create an EditText to allow the admin to input new text
        final EditText editText = new EditText(this);
        String currentText = isurumunutext.getText().toString();

        // Check if there's already text in the TextView
        if (!currentText.isEmpty()) {
            // Set current text to EditText if available
            editText.setText(currentText);
        } else {
            // If the TextView is empty, give a hint to the admin
            editText.setHint("Enter new text...");
        }

        // Create an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or Add Text")
                .setView(editText)
                .setPositiveButton("Save", (dialog, which) -> {
                    // Save the new text and update the TextView
                    String newText = editText.getText().toString().trim();
                    if (!newText.isEmpty()) {
                        isurumunutext.setText(newText); // Update the TextView with new text
                        Toast.makeText(sinharajaActivity.this, "Text updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(sinharajaActivity.this, "Text cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Navigate to ScenicstopsActivity when back button is clicked
            Intent intent = new Intent(sinharajaActivity.this, ScenicstopsActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
