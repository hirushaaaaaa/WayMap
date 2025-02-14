package com.example.waymap;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class FeedbackActivity extends AppCompatActivity {
    private EditText feedbackEditText;
    private TextView charCountTextView;
    private ChipGroup feedbackTypeChipGroup;
    private TextView sendFeedbackButton;
    private ImageButton[] emotionButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        // Initialize views
        feedbackEditText = findViewById(R.id.feedbackEditText);
        charCountTextView = findViewById(R.id.charCount);
        feedbackTypeChipGroup = findViewById(R.id.feedbackTypeChipGroup);
        sendFeedbackButton = findViewById(R.id.sendFeedbackButton);

        // Setup character count
        setupCharacterCount();

        // Setup emotion buttons
        setupEmotionButtons();

        // Setup send feedback button
        sendFeedbackButton.setOnClickListener(v -> validateAndSendFeedback());
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

        // TODO: Implement actual feedback submission logic
        Toast.makeText(this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
    }

}