package com.kraken.assesment.Adapters;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kraken.assesment.Models.Review;
import com.kraken.assesment.R;

import java.util.List;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder> {

    private static final String TAG = "ReviewRecyclerViewAdapt";

    private Context context;
    private List<Review> reviews;

    public ReviewRecyclerViewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + reviews.get(position).author);
        Log.d(TAG, "onBindViewHolder: " + reviews.get(position).content);
        holder.author.setText(reviews.get(position).author);
        holder.content.setText(reviews.get(position).content);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView author, content;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.rv_review_author);
            content = itemView.findViewById(R.id.rv_review_content);
        }
    }
}
