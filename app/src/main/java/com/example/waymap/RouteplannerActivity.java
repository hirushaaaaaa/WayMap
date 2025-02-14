package com.example.waymap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentContainerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RouteplannerActivity extends AppCompatActivity {
    private static final String TAG = "RouteplannerActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String MAPS_API_KEY = "AIzaSyBmDUjdK2diTVVSEuIJF3uwISql9EaibN0";

    private GoogleMap mMap;
    private EditText fromInput;
    private EditText destinationInput;
    private TextView dateText;
    private Spinner vehicleTypeSpinner;
    private EditText adultsInput;
    private EditText childrenInput;
    private CheckBox breakfastCheck, lunchCheck, dinnerCheck;
    private EditText specialNotesInput;
    private TextView distanceText, estimatedTimeText, estimatedCostText;
    private Button confirmTripButton;
    private LinearLayout savedTripsContainer;
    private final List<String> tripDetailsList = new ArrayList<>();
    private boolean isFormVisible = false;
    private GeoApiContext geoApiContext;
    private Marker sourceMarker, destinationMarker;
    private Polyline routePolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_routeplanner);

            // Initialize components
            initializeGoogleMapsContext();
            initializeViews();
            checkLocationPermission();
            setupMapFragment();
            setupAddTripButton();
            setupConfirmTripButton();

            // Retrieve trips for the logged-in user
            retrieveTripsFromFirebase();

        } catch (Exception e) {
            handleInitializationError(e);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTripsFromFirebase();
    }

    private void handleInitializationError(Exception e) {
    }

    private void initializeGoogleMapsContext() {
        geoApiContext = new GeoApiContext.Builder()
                .apiKey(MAPS_API_KEY)
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .build();
    }

    private void setupMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {
                mMap = googleMap;
                try {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                } catch (SecurityException e) {
                    Log.e(TAG, "Error setting up map: " + e.getMessage());
                }
            });
        }
    }

    private void retrieveTripsFromFirebase() {
        SharedPreferences loginPrefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String userId = loginPrefs.getString("userId", null);

        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference tripsRef = FirebaseDatabase.getInstance().getReference("Trips").child(userId);

        // Use addListenerForSingleValueEvent instead of addValueEventListener
        tripsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                savedTripsContainer.removeAllViews();
                tripDetailsList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tripDetails = snapshot.child("details").getValue(String.class);
                    if (tripDetails != null) {
                        tripDetailsList.add(tripDetails);
                        LinearLayout tripCard = getLinearLayout(tripDetails, snapshot.getKey());
                        savedTripsContainer.addView(tripCard, 0);
                    }
                }

                savedTripsContainer.setVisibility(tripDetailsList.isEmpty() ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving trips", databaseError.toException());
                Toast.makeText(RouteplannerActivity.this, "Failed to retrieve trips", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveTripToFirebase(String tripDetails) {
        SharedPreferences loginPrefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String userId = loginPrefs.getString("userId", null);

        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference tripsRef = FirebaseDatabase.getInstance().getReference("Trips").child(userId);
        String tripId = tripsRef.push().getKey();

        if (tripId != null) {
            Map<String, Object> tripMap = new HashMap<>();
            tripMap.put("details", tripDetails);
            tripMap.put("timestamp", ServerValue.TIMESTAMP);

            tripsRef.child(tripId).setValue(tripMap)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Trip saved successfully");
                        Toast.makeText(RouteplannerActivity.this, "Trip saved to database", Toast.LENGTH_SHORT).show();
                        retrieveTripsFromFirebase(); // Refresh the list after saving
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Failed to save trip", e);
                        Toast.makeText(RouteplannerActivity.this, "Failed to save trip: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private LinearLayout getLinearLayout(String tripDetails, String tripId) {
        LinearLayout tripCard = new LinearLayout(this);
        tripCard.setOrientation(LinearLayout.VERTICAL);
        tripCard.setPadding(16, 16, 16, 16);
        tripCard.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);

        TextView tripView = new TextView(this);
        tripView.setText(tripDetails);
        tripView.setPadding(8, 8, 8, 8);
        tripCard.addView(tripView);

        Button startButton = new Button(this);
        startButton.setText("Start Navigation");
        startButton.setOnClickListener(v -> {
            String[] lines = tripDetails.split("\n");
            String from = lines[2].replace("From: ", "").trim();
            String destination = lines[3].replace("Destination: ", "").trim();
            launchGoogleMapsNavigation(from, destination);
        });
        tripCard.addView(startButton);

        Button deleteButton = new Button(this);
        deleteButton.setText("Delete Trip");
        deleteButton.setOnClickListener(v -> {
            deleteTripFromFirebase(tripId);
            savedTripsContainer.removeView(tripCard);
            tripDetailsList.remove(tripDetails);
        });
        tripCard.addView(deleteButton);

        return tripCard;
    }

    private void deleteTripFromFirebase(String tripId) {
        SharedPreferences loginPrefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String userId = loginPrefs.getString("userId", null);

        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference tripRef = FirebaseDatabase.getInstance().getReference("Trips").child(userId).child(tripId);
        tripRef.removeValue()
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(RouteplannerActivity.this, "Trip deleted", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e -> {
                    Toast.makeText(RouteplannerActivity.this, "Failed to delete trip", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error deleting trip", e);
                });
    }

    private void initializeViews() {
        try {
            fromInput = findViewById(R.id.from_input);
            destinationInput = findViewById(R.id.destination_input);
            dateText = findViewById(R.id.date_text);
            vehicleTypeSpinner = findViewById(R.id.vehicle_type_spinner);
            adultsInput = findViewById(R.id.adults_input);
            childrenInput = findViewById(R.id.children_input);
            breakfastCheck = findViewById(R.id.breakfast_check);
            lunchCheck = findViewById(R.id.lunch_check);
            dinnerCheck = findViewById(R.id.dinner_check);
            specialNotesInput = findViewById(R.id.special_notes_input);
            distanceText = findViewById(R.id.distance_text);
            estimatedTimeText = findViewById(R.id.estimated_time_text);
            estimatedCostText = findViewById(R.id.estimated_cost_text);
            confirmTripButton = findViewById(R.id.confirm_trip_button);
            savedTripsContainer = findViewById(R.id.saved_trips_container);



            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.vehicle_types, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vehicleTypeSpinner.setAdapter(adapter);

            // Setup input listeners
            fromInput.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus && !fromInput.getText().toString().isEmpty()) {
                    calculateRoute();
                }
            });

            destinationInput.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus && !destinationInput.getText().toString().isEmpty()) {
                    calculateRoute();
                }
            });


            dateText.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RouteplannerActivity.this,
                        (view, year, month, dayOfMonth) -> {
                            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                            dateText.setText(selectedDate);
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            });

        } catch (Exception e) {
            Log.e(TAG, "Error initializing views: " + e.getMessage());
            throw e;
        }
    }

    private void calculateRoute() {
        String source = fromInput.getText().toString();
        String destination = destinationInput.getText().toString();

        if (source.isEmpty() || destination.isEmpty()) {
            return;
        }

        try {

            if (sourceMarker != null) sourceMarker.remove();
            if (destinationMarker != null) destinationMarker.remove();
            if (routePolyline != null) routePolyline.remove();


            DirectionsResult result = DirectionsApi.newRequest(geoApiContext)
                    .mode(TravelMode.DRIVING)
                    .origin(source)
                    .destination(destination)
                    .await();

            if (result.routes.length > 0) {

                List<LatLng> decodedPath = PolyUtil.decode(result.routes[0].overviewPolyline.getEncodedPath());
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(decodedPath)
                        .color(Color.BLUE)
                        .width(8);
                routePolyline = mMap.addPolyline(polylineOptions);


                LatLng sourceLatLng = new LatLng(
                        result.routes[0].legs[0].startLocation.lat,
                        result.routes[0].legs[0].startLocation.lng
                );
                LatLng destLatLng = new LatLng(
                        result.routes[0].legs[0].endLocation.lat,
                        result.routes[0].legs[0].endLocation.lng
                );

                sourceMarker = mMap.addMarker(new MarkerOptions().position(sourceLatLng).title("Start"));
                destinationMarker = mMap.addMarker(new MarkerOptions().position(destLatLng).title("Destination"));

                LatLngBounds.Builder bounds = new LatLngBounds.Builder();
                bounds.include(sourceLatLng);
                bounds.include(destLatLng);
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100));

                double distanceInKm = result.routes[0].legs[0].distance.inMeters / 1000.0;
                long durationInMinutes = result.routes[0].legs[0].duration.inSeconds / 60;
                double estimatedCost = distanceInKm * 0.5;

                distanceText.setText(String.format("Distance: %.1f km", distanceInKm));
                estimatedTimeText.setText(String.format("Estimated Time: %d minutes", durationInMinutes));
                estimatedCostText.setText(String.format("Estimated Cost: $%.2f", estimatedCost));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error calculating route: " + e.getMessage());
            Toast.makeText(this, "Error calculating route", Toast.LENGTH_SHORT).show();
        }
    }


    private void setupAddTripButton() {
        Button addTripButton = findViewById(R.id.add_trip_button);
        final View tripFormLayout = findViewById(R.id.trip_form_layout);
        final FragmentContainerView mapView = findViewById(R.id.map);
        final ScrollView scrollView = findViewById(R.id.scroll_view);
        final LinearLayout savedTripsContainer = findViewById(R.id.saved_trips_container);

        if (addTripButton != null) {
            addTripButton.setOnClickListener(v -> {
                savedTripsContainer.setVisibility(View.GONE);
                tripFormLayout.setVisibility(View.VISIBLE);
                mapView.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                isFormVisible = true;
                resetForm();
            });
        }
    }

    private void setupConfirmTripButton() {
        confirmTripButton.setOnClickListener(v -> {
            saveTripDetails();
            resetForm();
        });
    }

    @SuppressLint("SetTextI18n")
    private void resetForm() {
        fromInput.setText("");
        destinationInput.setText("");
        dateText.setText("");
        adultsInput.setText("");
        childrenInput.setText("");
        breakfastCheck.setChecked(false);
        lunchCheck.setChecked(false);
        dinnerCheck.setChecked(false);
        specialNotesInput.setText("");
        vehicleTypeSpinner.setSelection(0);
        distanceText.setText("Distance: Not calculated");
        estimatedTimeText.setText("Estimated Time: Not calculated");
        estimatedCostText.setText("Estimated Cost: Not calculated");

        if (sourceMarker != null) sourceMarker.remove();
        if (destinationMarker != null) destinationMarker.remove();
        if (routePolyline != null) routePolyline.remove();
    }

    private void saveTripDetails() {
        try {
            if (!validateInputs()) {
                return;
            }

            // Prepare trip details
            String from = fromInput.getText().toString();
            String destination = destinationInput.getText().toString();
            String date = dateText.getText().toString();
            String vehicleType = vehicleTypeSpinner.getSelectedItem().toString();
            int adults = Integer.parseInt(adultsInput.getText().toString());
            int children = Integer.parseInt(childrenInput.getText().toString());
            boolean breakfast = breakfastCheck.isChecked();
            boolean lunch = lunchCheck.isChecked();
            boolean dinner = dinnerCheck.isChecked();
            String notes = specialNotesInput.getText().toString();

            // Format trip details string
            @SuppressLint("DefaultLocale") String tripDetails = String.format(
                    "Trip Details\n" +
                            "Date: %s\n" +
                            "From: %s\n" +
                            "Destination: %s\n" +
                            "Vehicle: %s\n" +
                            "Passengers: %d Adults, %d Children\n" +
                            "Meals: %s%s%s\n" +
                            "Distance: %s\n" +
                            "Estimated Time: %s\n" +
                            "Estimated Cost: %s\n" +
                            "Notes: %s",
                    date, from, destination, vehicleType,
                    adults, children,
                    breakfast ? "Breakfast " : "",
                    lunch ? "Lunch " : "",
                    dinner ? "Dinner " : "",
                    distanceText.getText().toString(),
                    estimatedTimeText.getText().toString(),
                    estimatedCostText.getText().toString(),
                    notes
            );

            // Save to Firebase
            saveTripToFirebase(tripDetails);

            // Reset and update UI
            resetForm();
            savedTripsContainer.setVisibility(View.VISIBLE); // Ensure this is visible
            findViewById(R.id.trip_form_layout).setVisibility(View.GONE);
            findViewById(R.id.scroll_view).setVisibility(View.GONE);

            Toast.makeText(this, "Trip saved!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "Error saving trip details: " + e.getMessage());
            Toast.makeText(this, "Error saving trip details", Toast.LENGTH_LONG).show();
        }
    }


    @SuppressLint("SetTextI18n")
    @NonNull
    private LinearLayout getLinearLayout(String tripDetails) {
        LinearLayout tripCard = new LinearLayout(this);
        tripCard.setOrientation(LinearLayout.VERTICAL);
        tripCard.setPadding(16, 16, 16, 16);
        tripCard.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, 16);
        tripCard.setLayoutParams(cardParams);

        // Add trip title (e.g., "Trip 01")
        TextView tripTitle = new TextView(this);
        tripTitle.setText("Trip #" + (tripDetailsList.size()));
        tripTitle.setTextSize(18);
        tripTitle.setPadding(8, 8, 8, 8);
        tripCard.addView(tripTitle);

        // Add trip details
        TextView tripView = new TextView(this);
        tripView.setText(tripDetails);
        tripView.setPadding(8, 8, 8, 8);
        tripCard.addView(tripView);

        // Add Start Button
        Button startButton = new Button(this);
        startButton.setText("Start Navigation");
        startButton.setOnClickListener(v -> {
            // Extract "from" and "destination" locations from the trip details
            String[] lines = tripDetails.split("\n");
            String from = lines[2].replace("From: ", "").trim(); // Extract "From" location
            String destination = lines[3].replace("Destination: ", "").trim(); // Extract "Destination" location

            // Launch Google Maps with the "from" and "destination" locations
            launchGoogleMapsNavigation(from, destination);
        });
        tripCard.addView(startButton);

        // Add Delete Button
        Button deleteButton = new Button(this);
        deleteButton.setText("Delete Trip");
        deleteButton.setOnClickListener(v -> {
            savedTripsContainer.removeView(tripCard);
            tripDetailsList.remove(tripDetails);
        });
        tripCard.addView(deleteButton);

        return tripCard;


    }

    private boolean validateInputs() {
        if (fromInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Starting location is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (destinationInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Destination is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dateText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Date is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (adultsInput.getText().toString().trim().isEmpty() || Integer.parseInt(adultsInput.getText().toString()) <= 0) {
            Toast.makeText(this, "Number of adults is required and must be greater than 0", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {

                Toast.makeText(this, "Location permission is required to show your current location on the map", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, homeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clears the back stack
        startActivity(intent);


        finish();
    }


    private void performBackgroundTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GeoApiContext geoApiContext = new GeoApiContext.Builder()
                            .apiKey("YOUR_API_KEY")
                            .build();
                    geoApiContext.shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private class ShutdownMapsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            GeoApiContext geoApiContext = new GeoApiContext.Builder()
                    .apiKey("YOUR_API_KEY")
                    .build();
            geoApiContext.shutdown();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
    private void launchGoogleMapsNavigation(String from, String destination) {
        try {
            // Create a URI for Google Maps navigation
            String uri = "https://www.google.com/maps/dir/?api=1" +
                    "&origin=" + Uri.encode(from) +
                    "&destination=" + Uri.encode(destination) +
                    "&travelmode=driving";

            // Create an intent to launch Google Maps
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps"); // Ensure it opens in Google Maps

            // Check if Google Maps is installed
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error launching Google Maps: " + e.getMessage());
            Toast.makeText(this, "Error launching Google Maps", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new ShutdownMapsTask().execute();
    }

}