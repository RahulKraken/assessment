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

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchViewHolder> {

    private static final String TAG = "SearchRecyclerViewAdapt";

    private Context context;
    private List<Movie> movies;

    public SearchRecyclerViewAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_search_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.tv_title.setText(movies.get(position).title);
        holder.tv_year.setText(movies.get(position).release_date);
        holder.tv_runtime.setText(movies.get(position).original_language);

        holder.ratingBar.setRating((float) movies.get(position).vote_average / 2);

        Glide.with(context)
                .load(URLBuilder.imageUrl(movies.get(position).poster_path))
                .apply(new RequestOptions().placeholder(R.color.md_grey_300))
                .into(holder.posterImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView posterImage;
        TextView tv_title, tv_year, tv_runtime;
        RatingBar ratingBar;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.search_item_img);
            tv_title = itemView.findViewById(R.id.search_item_title);
            tv_year = itemView.findViewById(R.id.search_item_release);
            tv_runtime = itemView.findViewById(R.id.search_item_runtime);
            ratingBar = itemView.findViewById(R.id.search_item_rating_bar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: item clicked");
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(context.getResources().getString(R.string.movie_intent_pass_key), movies.get(getAdapterPosition()));
            context.startActivity(intent);
        }
    }
}
