package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class moreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        TextView moreActivityText = findViewById(R.id.backk);
        // Initialize LinearLayouts
        LinearLayout offlineMaps = findViewById(R.id.linearLayout11);
        LinearLayout fuelManagement = findViewById(R.id.linearLayout22);
        LinearLayout customRoutes = findViewById(R.id.linearLayout33);
        LinearLayout socialRoutes = findViewById(R.id.linearLayout44);

        // Set click listeners
        //offlineMaps.setOnClickListener(v -> openActivity(OfflineMapsActivity.class));
        //fuelManagement.setOnClickListener(v -> openActivity(FuelManagementActivity.class));
       //customRoutes.setOnClickListener(v -> openActivity(CustomRoutesActivity.class));
        //socialRoutes.setOnClickListener(v -> openActivity(SocialRoutesActivity.class));
        // Set OnClickListener for Distance Calculator TextView
        moreActivityText.setOnClickListener(v -> {
            Intent intent = new Intent(moreActivity.this, homeActivity.class);
            startActivity(intent);
        });


    }
}
