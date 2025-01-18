package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, reEnterPasswordEditText;
    private Button signUpButton, backToWelcomeButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Find views by ID
        usernameEditText = findViewById(R.id.editTextText2);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword3);
        reEnterPasswordEditText = findViewById(R.id.editTextTextPassword4);
        signUpButton = findViewById(R.id.signUpButton);
        backToWelcomeButton = findViewById(R.id.button2);

        // Navigate back to Welcome Activity
        backToWelcomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        });

        // Handle signup action
        signUpButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String reEnterPassword = reEnterPasswordEditText.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || reEnterPassword.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(reEnterPassword)) {
                Toast.makeText(SignupActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save user data to Firebase
            String userId = databaseReference.push().getKey(); // Generate unique user ID
            User user = new User(userId, username, email, password);

            databaseReference.child(userId).setValue(user)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(SignupActivity.this, "User registered successfully.", Toast.LENGTH_SHORT).show();
                        // Navigate to Welcome Activity or another screen
                        Intent intent = new Intent(SignupActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(SignupActivity.this, "Failed to register user: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }

    // User class to model data
    public static class User {
        public String userId;
        public String username;
        public String email;
        public String password;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String userId, String username, String email, String password) {
            this.userId = userId;
            this.username = username;
            this.email = email;
            this.password = password;
        }
    }
}
