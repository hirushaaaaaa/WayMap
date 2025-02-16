package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {
    private EditText feedbackEditText;
    private TextView charCountTextView;
    private ChipGroup feedbackTypeChipGroup;
    private TextView sendFeedbackButton;
    private ImageButton[] emotionButtons;
    private RecyclerView recyclerView;
    private FeedbackAdapter adapter;
    private List<Feedback> feedbackList;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("feedbacks");

        // Initialize views
        feedbackEditText = findViewById(R.id.feedbackEditText);
        charCountTextView = findViewById(R.id.charCount);
        feedbackTypeChipGroup = findViewById(R.id.feedbackTypeChipGroup);
        sendFeedbackButton = findViewById(R.id.sendFeedbackButton);
        recyclerView = findViewById(R.id.recyclerViewFeedback);

        // Initialize feedback list and adapter
        feedbackList = new ArrayList<>();
        adapter = new FeedbackAdapter(feedbackList);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Setup character count
        setupCharacterCount();

        // Setup emotion buttons
        setupEmotionButtons();

        // Setup send feedback button
        sendFeedbackButton.setOnClickListener(v -> validateAndSendFeedback());

        // Load feedback from Firebase
        loadFeedback();
    }

    private void setupCharacterCount() {
        feedbackEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int remaining = 70 - s.length();
                charCountTextView.setText(remaining + " chars");
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupEmotionButtons() {
        int[] emotionButtonIds = {
                R.id.happyEmotion, R.id.neutralEmotion,
                R.id.mehEmotion, R.id.sadEmotion,
                R.id.veryUnhappyEmotion
        };

        emotionButtons = new ImageButton[emotionButtonIds.length];
        for (int i = 0; i < emotionButtonIds.length; i++) {
            emotionButtons[i] = findViewById(emotionButtonIds[i]);
            final int index = i;
            emotionButtons[i].setOnClickListener(v -> selectEmotion(index));
        }
    }

    private void selectEmotion(int selectedIndex) {
        for (int i = 0; i < emotionButtons.length; i++) {
            emotionButtons[i].setSelected(i == selectedIndex);
        }
    }

    private void loadFeedback() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                feedbackList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Feedback feedback = snapshot.getValue(Feedback.class);
                    if (feedback != null) {
                        feedbackList.add(feedback);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FeedbackActivity.this,
                        "Failed to load feedback: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateAndSendFeedback() {
        String feedbackText = feedbackEditText.getText().toString().trim();
        Chip selectedFeedbackType = findViewById(feedbackTypeChipGroup.getCheckedChipId());

        if (feedbackText.isEmpty()) {
            Toast.makeText(this, "Please enter feedback", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedFeedbackType == null) {
            Toast.makeText(this, "Please select feedback type", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected emotion
        int selectedEmotion = -1;
        for (int i = 0; i < emotionButtons.length; i++) {
            if (emotionButtons[i].isSelected()) {
                selectedEmotion = i;
                break;
            }
        }

        if (selectedEmotion == -1) {
            Toast.makeText(this, "Please select an emotion", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Feedback object
        Feedback feedback = new Feedback(
                selectedFeedbackType.getText().toString(),
                feedbackText,
                selectedEmotion
        );

        // Save feedback to Firebase
        String feedbackId = databaseReference.push().getKey();
        if (feedbackId != null) {
            databaseReference.child(feedbackId).setValue(feedback)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
                            feedbackEditText.setText(""); // Clear the input field
                            // Reset emotion selection
                            selectEmotion(-1);
                            // Reset chip selection
                            feedbackTypeChipGroup.clearCheck();
                        } else {
                            Toast.makeText(this, "Failed to submit feedback", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.titleFeedback) {
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Feedback class
    public static class Feedback {
        private String type;
        private String message;
        private int emotion;

        // Default constructor required for Firebase
        public Feedback() {
        }

        public Feedback(String type, String message, int emotion) {
            this.type = type;
            this.message = message;
            this.emotion = emotion;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getEmotion() {
            return emotion;
        }

        public void setEmotion(int emotion) {
            this.emotion = emotion;
        }

        public String getEmotionEmoji() {
            switch (emotion) {
                case 0: return "😊"; // Happy
                case 1: return "😐"; // Neutral
                case 2: return "😕"; // Meh
                case 3: return "😢"; // Sad
                case 4: return "😠"; // Very Unhappy
                default: return "❓"; // Unknown
            }
        }
    }
}