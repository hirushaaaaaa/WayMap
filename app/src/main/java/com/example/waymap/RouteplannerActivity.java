package com.example.waymap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentContainerView;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.google.maps.android.PolyUtil;
import com.google.maps.GeoApiContext;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RouteplannerActivity extends AppCompatActivity {
    private static final String TAG = "RouteplannerActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String MAPS_API_KEY = "AIzaSyBmDUjdK2diTVVSEuIJF3uwISql9EaibN0"; // Replace with your API key

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
    private TextView savedTripsTitle;
    private final List<String> tripDetailsList = new ArrayList<>();
    private boolean isFormVisible = false;
    private com.google.maps.GeoApiContext geoApiContext;
    private Marker sourceMarker, destinationMarker;
    private Polyline routePolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_routeplanner);

            // Initialize GeoApiContext
            geoApiContext = new GeoApiContext.Builder()
                    .apiKey(MAPS_API_KEY)
                    .connectTimeout(2, TimeUnit.SECONDS)
                    .readTimeout(2, TimeUnit.SECONDS)
                    .writeTimeout(2, TimeUnit.SECONDS)
                    .build();

            // Initialize views
            initializeViews();

            // Check location permission
            checkLocationPermission();

            // Setup map
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

            setupAddTripButton();
            setupConfirmTripButton();

            // Initialize saved trips container
            savedTripsContainer.setVisibility(View.VISIBLE);
            savedTripsTitle.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(this, "Error initializing route planner", Toast.LENGTH_LONG).show();
        }
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
            savedTripsTitle = findViewById(R.id.saved_trips_title);

            // Setup spinner
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

            // Date Picker implementation
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
            // Clear previous markers and route
            if (sourceMarker != null) sourceMarker.remove();
            if (destinationMarker != null) destinationMarker.remove();
            if (routePolyline != null) routePolyline.remove();

            // Get directions
            DirectionsResult result = DirectionsApi.newRequest(geoApiContext)
                    .mode(TravelMode.DRIVING)
                    .origin(source)
                    .destination(destination)
                    .await();

            if (result.routes.length > 0) {
                // Draw route on map
                List<LatLng> decodedPath = PolyUtil.decode(result.routes[0].overviewPolyline.getEncodedPath());
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(decodedPath)
                        .color(Color.BLUE)
                        .width(8);
                routePolyline = mMap.addPolyline(polylineOptions);

                // Add markers
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

                // Move camera to show the entire route
                LatLngBounds.Builder bounds = new LatLngBounds.Builder();
                bounds.include(sourceLatLng);
                bounds.include(destLatLng);
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100));

                // Update distance and time
                double distanceInKm = result.routes[0].legs[0].distance.inMeters / 1000.0;
                long durationInMinutes = result.routes[0].legs[0].duration.inSeconds / 60;

                // Calculate estimated cost (example: $0.5 per km)
                double estimatedCost = distanceInKm * 0.5;

                // Update UI
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

        if (addTripButton != null) {
            addTripButton.setOnClickListener(v -> {
                if (!isFormVisible) {
                    tripFormLayout.setVisibility(View.VISIBLE);
                    mapView.setVisibility(View.VISIBLE);
                    isFormVisible = true;
                    resetForm();
                } else {
                    tripFormLayout.setVisibility(View.GONE);
                    isFormVisible = false;
                }
            });
        }
    }

    private void setupConfirmTripButton() {
        confirmTripButton.setOnClickListener(v -> {
            saveTripDetails();
            // Hide the ScrollView after confirming the trip
            findViewById(R.id.scroll_view).setVisibility(View.GONE);
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

        // Clear map markers and route
        if (sourceMarker != null) sourceMarker.remove();
        if (destinationMarker != null) destinationMarker.remove();
        if (routePolyline != null) routePolyline.remove();
    }

    private void saveTripDetails() {
        try {
            // Validate input fields
            if (!validateInputs()) {
                return;
            }

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

            @SuppressLint("DefaultLocale") String tripDetails = String.format("Trip #%d\n", tripDetailsList.size() + 1) +
                    "Date: " + date + "\nFrom: " + from + "\nDestination: " + destination +
                    "\nVehicle: " + vehicleType + "\nAdults: " + adults +
                    ", Children: " + children + "\nMeals: " +
                    (breakfast ? "Breakfast " : "") +
                    (lunch ? "Lunch " : "") +
                    (dinner ? "Dinner " : "") +
                    "\nDistance: " + distanceText.getText() +
                    "\nEstimated Time: " + estimatedTimeText.getText() +
                    "\nEstimated Cost: " + estimatedCostText.getText() +
                    "\nNotes: " + notes;

            tripDetailsList.add(tripDetails);

            // Create a card-like view for the trip
            LinearLayout tripCard = getLinearLayout(tripDetails);

            // Add the card to the container
            savedTripsContainer.addView(tripCard, 0); // Add at the top

            // Reset and hide the form
            resetForm();
            findViewById(R.id.trip_form_layout).setVisibility(View.GONE);
            findViewById(R.id.scroll_view).setVisibility(View.GONE);
            isFormVisible = false;

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

        // Add trip details to card
        TextView tripView = new TextView(this);
        tripView.setText(tripDetails);
        tripView.setPadding(8, 8, 8, 8);
        tripCard.addView(tripView);

        // Add delete button
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
                if (mMap != null) {
                    try {
                        mMap.setMyLocationEnabled(true);
                    } catch (SecurityException e) {
                        Log.e(TAG, "Error enabling location: " + e.getMessage());
                    }
                }
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (geoApiContext != null) {
            geoApiContext.shutdown();
        }
    }
}