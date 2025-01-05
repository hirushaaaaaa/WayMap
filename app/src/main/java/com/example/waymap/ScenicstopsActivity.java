package com.example.waymap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ScenicstopsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenicstops);

        // Find the ImageView for Anuradhapura
        ImageView imageAnuradhapura = findViewById(R.id.imageanuradhapura);
        ImageView imageSinharaja = findViewById(R.id.imagesinharaja);
        ImageView imageRuwanmeliseya = findViewById(R.id.imageruwanmeliseya);
        ImageView imagegalleport = findViewById(R.id.imagegalleport);
        ImageView imageella = findViewById(R.id.imageella);
        ImageView imagedambulla = findViewById(R.id.imagedambulla);
        ImageView imagedamirissa = findViewById(R.id.imagedamirissa);
        ImageView imagesripadasthanaya = findViewById(R.id.imagesripadasthanaya);


        // Set a click listener to navigate to AnuradhapuraActivity
        imageAnuradhapura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScenicstopsActivity.this, AnuradhapuraActivity.class);
                startActivity(intent);
            }

        });
        imageSinharaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScenicstopsActivity.this, sinharajaActivity.class);
                startActivity(intent);
            }
            });
        imagegalleport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScenicstopsActivity.this, GalleportActivity.class);
                startActivity(intent);
            }
        });
        imageella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScenicstopsActivity.this, EllaActivity.class);
                startActivity(intent);
            }
        });
        imageRuwanmeliseya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScenicstopsActivity.this, RuwanmeliseyaActivity.class);
                startActivity(intent);
            }
        });
        imagedamirissa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScenicstopsActivity.this, MirissaActivity.class);
                startActivity(intent);
            }
        });
        imagedambulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScenicstopsActivity.this, DambullaActivity.class);
                startActivity(intent);
            }
        });
        imagesripadasthanaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScenicstopsActivity.this, SripadasthanayaActivity.class);
                startActivity(intent);
            }
        });
    }

    }

