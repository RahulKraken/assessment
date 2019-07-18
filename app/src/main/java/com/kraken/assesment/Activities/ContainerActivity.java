package com.kraken.assesment.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.kraken.assesment.Adapters.ListMovieRecyclerViewAdapter;
import com.kraken.assesment.Models.Movie;
import com.kraken.assesment.R;

import java.util.ArrayList;
import java.util.List;

public class ContainerActivity extends AppCompatActivity {

    private static final String TAG = "ContainerActivity";

    private RecyclerView recyclerView;
    private ListMovieRecyclerViewAdapter adapter;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        String action = getIntent().getStringExtra(getResources().getString(R.string.action_intent_pass_key));

        recyclerView = findViewById(R.id.container_recycler_view);
        movies = new ArrayList<>();
        adapter = new ListMovieRecyclerViewAdapter(this, movies);

        if (action != null) {
            switch (action) {
                case "PURCHASED":
                    Log.d(TAG, "onCreate: PURCHASED");
                    break;
                case "WISHLIST":
                    Log.d(TAG, "onCreate: WISHLIST");
                    break;
                case "LIKED":
                    Log.d(TAG, "onCreate: LIKED");
                    break;
            }
        }
    }
}
