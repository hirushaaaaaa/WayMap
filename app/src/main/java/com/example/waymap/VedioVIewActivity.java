package com.example.waymap;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VedioVIewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) VideoView videoView = findViewById(R.id.videobg);

        // Set video URI (stored in res/raw folder)
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.nnnnnnn);
        videoView.setVideoURI(videoUri);

        // Start video and loop it
        videoView.start();
        videoView.setOnPreparedListener(mp -> mp.setLooping(true));

        // Add fallback for when video cannot play (Optional)
        videoView.setOnErrorListener((mp, what, extra) -> {
            findViewById(R.id.backgroundImage).setVisibility(View.VISIBLE);
            return true;
        });
    }
}
