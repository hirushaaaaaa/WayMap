package com.example.waymap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    private AutoCompleteTextView searchBar;
    private final String[] suggestions = {
            "Colombo",
            "Kandy",
            "Galle",
            "Jaffna",
            "Anuradhapura",
            "Negombo",
            "Matara",
            "Nuwara Eliya"
    };

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize the search bar
        searchBar = findViewById(R.id.searchBar);

        // Set up suggestions adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                suggestions
        );
        searchBar.setAdapter(adapter);

        // Handle selection of a suggestion
        searchBar.setOnItemClickListener((parent, view, position, id) -> {
            String selectedSuggestion = parent.getItemAtPosition(position).toString();
            performSearch(selectedSuggestion);
        });

        // Handle search when user presses Enter
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchBar.getText().toString().trim();
            if (!TextUtils.isEmpty(query)) {
                performSearch(query);
            } else {
                Toast.makeText(SearchActivity.this, "Search field cannot be empty!", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }

    /**
     * Perform the search operation with the given query.
     *
     * @param query The search query entered by the user.
     */
    private void performSearch(String query) {
        // For now, show a toast. Replace with actual search logic later.
        Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();

        // Implement your search functionality here
    }
}
