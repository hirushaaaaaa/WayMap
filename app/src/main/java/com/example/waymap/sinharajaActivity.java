package com.example.waymap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class sinharajaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinharaja);

        // Find the location TextView
        TextView locIsurumuniya = findViewById(R.id.locisurumunuiya);

        // Set click listener to open Google Maps
        locIsurumuniya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Isurumuniya in Google Maps
                Uri gmmIntentUri = Uri.parse("geo:8.3312,80.3881?q=Isurumuniya, Anuradhapura, Sri Lanka");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
    }
}
