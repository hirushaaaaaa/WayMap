package com.example.waymap;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FeedbackAdapter adapter;
    private List<FeedbackActivity.Feedback> feedbackList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfeedback);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("feedbacks");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewFeedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize feedback list and adapter
        feedbackList = new ArrayList<>();
        adapter = new FeedbackAdapter(feedbackList);
        recyclerView.setAdapter(adapter);

        // Load feedback
        loadFeedback();
    }

    private void loadFeedback() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                feedbackList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FeedbackActivity.Feedback feedback = snapshot.getValue(FeedbackActivity.Feedback.class);
                    if (feedback != null) {
                        feedbackList.add(feedback);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewFeedbackActivity.this,
                        "Failed to load feedback: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}