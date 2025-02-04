package com.example.waymap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ScenicstopsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView selectedImageView; // To keep track of the image being edited
    private boolean isEditMode = true; // Track whether we're in edit mode
    private ImageView adminImageView; // Moved to class-level

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
        adminImageView = findViewById(R.id.add);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false); // Retrieve admin status

        // Debug: Check if the admin status is retrieved correctly
        Log.d("DEBUG", "Admin Status: " + isAdmin);
        Log.d("DEBUG", "Login Status: " + sharedPreferences.getBoolean("isLoggedIn", false));
        Log.d("DEBUG", "Admin Status: " + sharedPreferences.getBoolean("isAdmin", false));

        // Set visibility based on admin status
        adminImageView.setVisibility(isAdmin ? View.VISIBLE : View.GONE);

        // Set click listeners to navigate to different activities
        imageAnuradhapura.setOnClickListener(v -> startActivity(new Intent(this, AnuradhapuraActivity.class)));
        imageSinharaja.setOnClickListener(v -> startActivity(new Intent(this, sinharajaActivity.class)));
        imageRuwanmeliseya.setOnClickListener(v -> startActivity(new Intent(this, RuwanmeliseyaActivity.class)));
        imageGalleport.setOnClickListener(v -> startActivity(new Intent(this, GalleportActivity.class)));
        imageElla.setOnClickListener(v -> startActivity(new Intent(this, EllaActivity.class)));
        imageDambulla.setOnClickListener(v -> startActivity(new Intent(this, DambullaActivity.class)));
        imageMirissa.setOnClickListener(v -> startActivity(new Intent(this, MirissaActivity.class)));
        imageSripada.setOnClickListener(v -> startActivity(new Intent(this, SripadasthanayaActivity.class)));

        // Set the click listener for the admin ImageView (add/edit button)
        adminImageView.setOnClickListener(v -> {
            if (isAdmin) {
                adminImageView.setVisibility(View.VISIBLE); // Show only if admin
            } else {
                adminImageView.setVisibility(View.GONE); // Hide for regular users
            }
        });
    }

    // Toggle the edit mode and allow image selection
    private void toggleEditMode(ImageView... images) {
        isEditMode = !isEditMode;
        if (isEditMode) {
            Toast.makeText(this, "Edit mode activated. Tap an image to change.", Toast.LENGTH_SHORT).show();
            enableImageEditing(images);
        } else {
            Toast.makeText(this, "Edit mode deactivated.", Toast.LENGTH_SHORT).show();
            disableImageEditing(images);
        }
    }

    // Enable editing by setting click listeners on ImageViews
    private void enableImageEditing(ImageView... images) {
        for (ImageView imageView : images) {
            imageView.setOnClickListener(v -> {
                if (isEditMode) {
                    selectedImageView = imageView; // Track the image that is being edited
                    openImagePicker();
                }
            });
        }
    }

    // Disable editing mode (remove click listeners)
    private void disableImageEditing(ImageView... images) {
        for (ImageView imageView : images) {
            imageView.setOnClickListener(null); // Remove the click listener
        }
    }

    // Open image picker to select an image from the gallery
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*"); // Filter to only images
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handle the result from the image picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData(); // Get the URI of the selected image
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                if (selectedImageView != null) {
                    selectedImageView.setImageBitmap(bitmap); // Set the image to the selected ImageView
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
