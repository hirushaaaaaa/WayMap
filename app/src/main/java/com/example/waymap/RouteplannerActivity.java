package com.example.waymap;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RouteplannerActivity extends AppCompatActivity {

    private LinearLayout destinationContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeplanner);

        destinationContainer = findViewById(R.id.destinationContainer);

        // Add initial "Add Destination" button
        Button addDestinationButton = findViewById(R.id.addDestinationButton);
        addDestinationButton.setOnClickListener(v -> showAddDestinationDialog());
    }

    private void showAddDestinationDialog() {
        // Create a dialog to input destination details
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Destination");

        // Create a layout for input fields
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Destination name input
        EditText destinationNameInput = new EditText(this);
        destinationNameInput.setHint("Destination Name");
        destinationNameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(destinationNameInput);

        // Destination details input
        EditText destinationDetailsInput = new EditText(this);
        destinationDetailsInput.setHint("Destination Details");
        destinationDetailsInput.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(destinationDetailsInput);

        builder.setView(layout);

        // Add "OK" and "Cancel" buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = destinationNameInput.getText().toString();
            String details = destinationDetailsInput.getText().toString();
            if (!name.isEmpty() && !details.isEmpty()) {
                addDestinationToList(name, details);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Show the dialog
        builder.show();
    }

    private void addDestinationToList(String name, String details) {
        // Create a new layout to display the destination
        LinearLayout destinationLayout = new LinearLayout(this);
        destinationLayout.setOrientation(LinearLayout.VERTICAL);
        destinationLayout.setPadding(16, 16, 16, 16);

        // Create TextView for the destination name
        TextView destinationName = new TextView(this);
        destinationName.setText("Name: " + name);
        destinationName.setTextSize(16);
        destinationName.setPadding(0, 0, 0, 8);
        destinationLayout.addView(destinationName);

        // Create TextView for the destination details
        TextView destinationDetails = new TextView(this);
        destinationDetails.setText("Details: " + details);
        destinationDetails.setTextSize(14);
        destinationLayout.addView(destinationDetails);

        // Create a new "Add Destination" button
        Button addDestinationButton = new Button(this);
        addDestinationButton.setText("Add Destination");
        addDestinationButton.setOnClickListener(v -> showAddDestinationDialog());
        destinationLayout.addView(addDestinationButton);

        // Remove the old "Add Destination" button
        destinationContainer.removeViewAt(destinationContainer.getChildCount() - 1);

        // Add the new destination layout and new button
        destinationContainer.addView(destinationLayout);
        destinationContainer.addView(addDestinationButton);
    }
}
