package com.kraken.assesment.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kraken.assesment.Models.Category;
import com.kraken.assesment.Models.Movie;
import com.kraken.assesment.MyApplication;
import com.kraken.assesment.R;
import com.kraken.assesment.Utils.Constants;
import com.kraken.assesment.Utils.Serializer;
import com.kraken.assesment.Utils.URLBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class GenreListRecyclerViewAdapter extends RecyclerView.Adapter<GenreListRecyclerViewAdapter.GenreListViewHolder> {

    private static final String TAG = "GenreListRecyclerViewAd";

    private Context context;
    private List<Category> categories;

    public GenreListRecyclerViewAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public GenreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_main_item, parent, false);
        return new GenreListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreListViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + categories.get(position).title + " : " + position);

        holder.genreTitle.setText(categories.get(position).title);
        getMovieList(categories.get(position).title, holder.recyclerView);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private void getMovieList(final String genre, final RecyclerView recyclerView) {
        Log.d(TAG, "getMovieList: " + URLBuilder.movieListWithGenre(Constants.genres.get(genre), 1));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLBuilder.movieListWithGenre(Constants.genres.get(genre), 1), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String raw = object.getString("results");

                    List<Movie> movies = Serializer.serializeMovies(raw);

                    Log.d(TAG, "onResponse: raw : " + genre + "\n" + raw);

                    ListMovieRecyclerViewAdapter adapter = new ListMovieRecyclerViewAdapter(context, movies);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(layoutManager);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });

        MyApplication.getRequestQueue().add(stringRequest);
    }

    class GenreListViewHolder extends RecyclerView.ViewHolder {

        TextView genreTitle;
        RecyclerView recyclerView;

        GenreListViewHolder(@NonNull View itemView) {
            super(itemView);

            genreTitle = itemView.findViewById(R.id.section_title);
            recyclerView = itemView.findViewById(R.id.category_recycler_view);
        }
    }
}
