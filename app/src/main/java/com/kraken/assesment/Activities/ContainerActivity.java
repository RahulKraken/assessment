package com.kraken.assesment.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").child(firebaseAuth.getCurrentUser().getUid());

        setContentView(R.layout.activity_container);

        String action = getIntent().getStringExtra(getResources().getString(R.string.action_intent_pass_key));

        recyclerView = findViewById(R.id.container_recycler_view);
        movies = new ArrayList<>();
        adapter = new ListMovieRecyclerViewAdapter(this, movies);

        if (action != null) {
            switch (action) {
                case "PURCHASED":
                    Log.d(TAG, "onCreate: PURCHASED");
                    getResults(reference.child("purchased"));
                    break;
                case "WISHLIST":
                    Log.d(TAG, "onCreate: WISHLIST");
                    getResults(reference.child("wishlist"));
                    break;
                case "RENTED":
                    Log.d(TAG, "onCreate: RENTED");
                    getResults(reference.child("rented"));
                    break;
            }
        }

        initRecyclerView();
    }

    private void getResults(DatabaseReference ref) {
        Log.d(TAG, "getResults: " + ref.getParent().getKey());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot.getValue());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: " + snapshot);
                    movies.add(snapshot.getValue(Movie.class));
                }
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onDataChange: " + movies.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        final int space = 16;

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.bottom = space;
                outRect.left = space;
                outRect.right = space;

                if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1)
                    outRect.top = 2 * space;
                else
                    outRect.top = 0;
            }
        });
    }
}
