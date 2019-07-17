package com.kraken.assesment.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kraken.assesment.Activities.MovieDetailsActivity;
import com.kraken.assesment.Models.Movie;
import com.kraken.assesment.R;
import com.kraken.assesment.Utils.URLBuilder;

import java.util.List;

public class ListMovieRecyclerViewAdapter extends RecyclerView.Adapter<ListMovieRecyclerViewAdapter.MovieViewHolder> {

    private static final String TAG = "ListMovieRecyclerViewAd";

    private Context context;
    private List<Movie> movies;

    public ListMovieRecyclerViewAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_category_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.title.setText(movies.get(position).title);
        holder.ratingBar.setRating((float) movies.get(position).vote_average / 2);

        Log.d(TAG, "onBindViewHolder: " + URLBuilder.imageUrl(movies.get(position).poster_path));
        Glide.with(context)
                .load(URLBuilder.imageUrl(movies.get(position).poster_path))
                .apply(new RequestOptions().placeholder(R.color.md_blue_grey_300))
                .into(holder.posterImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView posterImage;
        TextView title;
        RatingBar ratingBar;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.item_poster);
            title = itemView.findViewById(R.id.item_title);
            ratingBar = itemView.findViewById(R.id.item_rating_bar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + movies.get(getAdapterPosition()).title);
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(context.getString(R.string.movie_intent_pass_key), movies.get(getAdapterPosition()));
            context.startActivity(intent);
        }
    }
}
