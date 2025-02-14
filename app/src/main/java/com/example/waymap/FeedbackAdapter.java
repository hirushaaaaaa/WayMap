package com.example.waymap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    private List<Feedback> feedbackList;

    // Feedback model class
    public static class Feedback {
        private String userName;
        private String feedbackText;
        private int rating;
        private String emotion;
        private String feedbackType;

        public Feedback(String userName, String feedbackText, int rating, String emotion, String feedbackType) {
            this.userName = userName;
            this.feedbackText = feedbackText;
            this.rating = rating;
            this.emotion = emotion;
            this.feedbackType = feedbackType;
        }

        // Getters
        public String getUserName() { return userName; }
        public String getFeedbackText() { return feedbackText; }
        public int getRating() { return rating; }
        public String getEmotion() { return emotion; }
        public String getFeedbackType() { return feedbackType; }
    }

    // ViewHolder class
    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView userNameText;
        TextView feedbackText;
        RatingBar ratingBar;
        TextView emotionText;
        TextView feedbackTypeText;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameText = itemView.findViewById(R.id.feedbackUserName);
            feedbackText = itemView.findViewById(R.id.feedbackText);
            ratingBar = itemView.findViewById(R.id.feedbackRatingBar);
            emotionText = itemView.findViewById(R.id.feedbackEmotion);
            feedbackTypeText = itemView.findViewById(R.id.feedbackType);
        }
    }

    public FeedbackAdapter(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_feedbackshow, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);

        holder.userNameText.setText(feedback.getUserName());
        holder.feedbackText.setText(feedback.getFeedbackText());
        holder.ratingBar.setRating(feedback.getRating());
        holder.emotionText.setText(feedback.getEmotion());
        holder.feedbackTypeText.setText(feedback.getFeedbackType());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }
}