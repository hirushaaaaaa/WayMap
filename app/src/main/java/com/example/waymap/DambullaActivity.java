package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DambullaActivity extends AppCompatActivity {

    private EditText dambullatext;
    private TextView locdambulla;
    private Button backButto, editButton1, saveButton1;
    private boolean isAdmin = false; // Admin flag

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dambulla);

        // Initialize Views
        dambullatext = findViewById(R.id.dambullatext);
        locdambulla = findViewById(R.id.locdambulla);
        backButto = findViewById(R.id.backButto);
        editButton1 = findViewById(R.id.editButton);
        saveButton1 = findViewById(R.id.saveButton);

        // Check for null references (prevent crashes)
        if (dambullatext == null || locdambulla == null || backButto == null || editButton1 == null || saveButton1 == null) {
            Log.e("DambullaActivity", "One or more UI components not found in layout!");
            return;
        }

        // Check admin permissions
        checkAdminPermissions();

        // Load content from Firebase
        loadContent();

        // Back button functionality
        backButto.setOnClickListener(v -> finish()); // Close the activity

        // Edit button functionality
        editButton1.setOnClickListener(v -> {
            dambullatext.setEnabled(true); // Enable editing
            dambullatext.requestFocus(); // Focus on the EditText
        });

        // Save button functionality
        saveButton1.setOnClickListener(v -> {
            String content = dambullatext.getText().toString().trim();
            if (!content.isEmpty()) {
                saveContent(content);
                dambullatext.setEnabled(false); // Disable editing after saving
            } else {
                Toast.makeText(this, "Content cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAdminPermissions() {
        // Retrieve admin status from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        // Debugging: Check if the admin status is retrieved correctly
        Log.d("DEBUG", "Admin Status: " + isAdmin);

        // Set the visibility of edit and save buttons based on admin status
        if (editButton1 != null && saveButton1 != null) {
            if (isAdmin) {
                editButton1.setVisibility(View.VISIBLE);
                saveButton1.setVisibility(View.VISIBLE);
            } else {
                editButton1.setVisibility(View.GONE);
                saveButton1.setVisibility(View.GONE);
            }
        }
    }

    private void saveContent(String content) {
        // Save the content to Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("dambulla_content");
        databaseReference.setValue(content)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Content Saved!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to Save Content", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadContent() {
        // Load the content from Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("dambulla_content");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String savedContent = snapshot.getValue(String.class);
                if (savedContent != null) {
                    dambullatext.setText(savedContent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DambullaActivity.this, "Failed to Load Content", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
