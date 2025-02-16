package com.example.waymap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    private List<FeedbackActivity.Feedback> feedbackList;

    public FeedbackAdapter(List<FeedbackActivity.Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feedback, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        FeedbackActivity.Feedback feedback = feedbackList.get(position);
        holder.textViewFeedbackType.setText("Type: " + feedback.getType());
        holder.textViewFeedbackMessage.setText(feedback.getMessage());
        holder.textViewFeedbackEmotion.setText("Emotion: " + getEmotionText(feedback.getEmotion())
                + " " + feedback.getEmotionEmoji());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    private String getEmotionText(int emotion) {
        switch (emotion) {
            case 0: return "Happy";
            case 1: return "Neutral";
            case 2: return "Meh";
            case 3: return "Sad";
            case 4: return "Very Unhappy";
            default: return "Unknown";
        }
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFeedbackType, textViewFeedbackMessage, textViewFeedbackEmotion;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFeedbackType = itemView.findViewById(R.id.textViewFeedbackType);
            textViewFeedbackMessage = itemView.findViewById(R.id.textViewFeedbackMessage);
            textViewFeedbackEmotion = itemView.findViewById(R.id.textViewFeedbackEmotion);
        }
    }
}