package com.example.waymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class getstartActivity extends AppCompatActivity {

    private VideoView backgroundVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstart);

        // Initialize VideoView
        backgroundVideoView = findViewById(R.id.videobg);

        // Set video URI (video file should be in the "res/raw" folder)
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.nnnnnnn);
        backgroundVideoView.setVideoURI(videoUri);

        // Start the video and loop it
        backgroundVideoView.start();
        backgroundVideoView.setOnPreparedListener(mp -> mp.setLooping(true));

        // Skip button functionality
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView skipButton = findViewById(R.id.skipbutton);
        skipButton.setOnClickListener(v -> {
            Intent intent = new Intent(getstartActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();  // Optional: Finish current activity to prevent returning
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (backgroundVideoView != null) {
            backgroundVideoView.start();  // Resume video when activity is resumed
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (backgroundVideoView != null) {
            backgroundVideoView.pause();  // Pause video when activity is paused
        }
    }
}
