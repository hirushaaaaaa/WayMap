package com.example.waymap;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class RouteplannerActivity extends AppCompatActivity {
    private static final String TAG = "RouteplannerActivity";
    private GoogleMap mMap;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_routeplanner);

            // Initialize views
            initializeViews();

            // Setup map
            SupportMapFragment MapFragment= (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            if (MapFragment != null) {
                MapFragment.getMapAsync(googleMap -> {
                    mMap = googleMap;

                    // Example: Add a marker
                    LatLng exampleLocation = new LatLng(0, 0);
                    mMap.addMarker(new MarkerOptions().position(exampleLocation).title("Marker at 0, 0"));
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

            // Set up destination input listener
            destinationInput.setOnEditorActionListener((v, actionId, event) -> {
                String destination = destinationInput.getText().toString().trim();
                if (!destination.isEmpty()) {
                    locateDestination(destination);
                }
                return true;
            });

            // Set up text change listener with debounce
            destinationInput.addTextChangedListener(new TextWatcher() {
                private Timer timer = new Timer();

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    timer.cancel();
                    timer = new Timer();
                    // 500ms delay to reduce frequent calls
                    long DELAY = 500;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(() -> {
                                String destination = s.toString().trim();
                                if (!destination.isEmpty()) {
                                    locateDestination(destination);
                                }
                            });
                        }
                    }, DELAY);
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

        } catch (Exception e) {
            Log.e(TAG, "Error initializing views: " + e.getMessage());
            throw e;
        }
    }

    private void locateDestination(String destination) {
        if (destination.isEmpty()) {
            return; // Don't proceed if no text is entered
        }

        if (mMap == null) {
            Toast.makeText(this, "Map is not ready yet!", Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocationName(destination, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.clear(); // Clear existing markers
                mMap.addMarker(new MarkerOptions().position(latLng).title(destination));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            } else {
                Toast.makeText(this, "Location not found for: " + destination, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error locating destination: " + e.getMessage());
            Toast.makeText(this, "Error finding location", Toast.LENGTH_SHORT).show();
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
    }

    private void saveTripDetails() {
        try {
            // Validate input fields
            if (!validateInputs()) {
                return;
            }

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
                    "Date: " + date + "\nDestination: " + destination +
                    "\nVehicle: " + vehicleType + "\nAdults: " + adults +
                    ", Children: " + children + "\nMeals: " +
                    (breakfast ? "Breakfast " : "") +
                    (lunch ? "Lunch " : "") +
                    (dinner ? "Dinner " : "") +
                    "\nNotes: " + notes;

            tripDetailsList.add(tripDetails);

            // Create a card-like view for the trip
            LinearLayout tripCard = getLinearLayout(tripDetails);

            // Add the card to the container
            savedTripsContainer.addView(tripCard, 0); // Add at the top

            // Reset and hide the form
            resetForm();
            findViewById(R.id.trip_form_layout).setVisibility(View.GONE);
            findViewById(R.id.scroll_view).setVisibility(View.GONE); // Hide the ScrollView inside form
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
        if (destinationInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Destination is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dateText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Date is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Integer.parseInt(adultsInput.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Adults must be a number", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Integer.parseInt(childrenInput.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Children must be a number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
