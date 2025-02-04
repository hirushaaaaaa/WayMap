package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class sinharajaActivity extends AppCompatActivity {

    private EditText isurumunuEditText;
    private ImageView editButton, saveButton;
    private SharedPreferences sharedPreferences;
    private boolean isAdmin;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rainforest);

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize UI components
        Button backButton = findViewById(R.id.backButton6);
        editButton = findViewById(R.id.editbutton);
        saveButton = findViewById(R.id.savedbutton);
        isurumunuEditText = findViewById(R.id.isurumunitext);

        // Load admin status from SharedPreferences
        SharedPreferences loginPrefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        isAdmin = loginPrefs.getBoolean("isAdmin", false);
        sharedPreferences = getSharedPreferences("AdminPrefs", MODE_PRIVATE);

        // Debugging log
        Log.d("DEBUG", "Admin Status: " + isAdmin);

        // Load saved text
        String savedText = sharedPreferences.getString("isurumuniText", "");
        if (savedText.isEmpty()) {
            isurumunuEditText.setHint("Enter new text here...");
        } else {
            isurumunuEditText.setText(savedText);
        }

        // Set UI based on admin status
        if (isAdmin) {
            editButton.setVisibility(View.VISIBLE);  // Show edit button if admin
        } else {
            editButton.setVisibility(View.GONE); // Hide edit button for non-admins
            saveButton.setVisibility(View.GONE); // Hide save button for non-admins
            isurumunuEditText.setEnabled(false); // Disable the EditText
        }

        // Edit Button: Enable text editing or allow adding new text
        editButton.setOnClickListener(v -> {
            isurumunuEditText.setEnabled(true); // Allow text editing
            isurumunuEditText.requestFocus();  // Focus on the text field
            saveButton.setVisibility(View.VISIBLE); // Show Save button when editing
        });

        // Save Button: Save the edited or new text
        saveButton.setOnClickListener(v -> {
            String newText = isurumunuEditText.getText().toString().trim();
            if (!newText.isEmpty()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("isurumuniText", newText);  // Save text to SharedPreferences
                editor.apply();
                Toast.makeText(this, "Text saved!", Toast.LENGTH_SHORT).show();
                isurumunuEditText.setEnabled(false); // Disable editing after saving
                saveButton.setVisibility(View.GONE); // Hide save button after saving
            } else {
                Toast.makeText(this, "Text cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });

        // Back button action
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(sinharajaActivity.this, ScenicstopsActivity.class));
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(sinharajaActivity.this, ScenicstopsActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
