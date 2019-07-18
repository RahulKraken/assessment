package com.kraken.assesment.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kraken.assesment.Adapters.ReviewRecyclerViewAdapter;
import com.kraken.assesment.FirebaseHelpers.DatabasePutService;
import com.kraken.assesment.Models.Movie;
import com.kraken.assesment.Models.Review;
import com.kraken.assesment.MyApplication;
import com.kraken.assesment.R;
import com.kraken.assesment.Utils.Serializer;
import com.kraken.assesment.Utils.URLBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MovieDetailsActivity";

    // Widgets
    ImageView posterImg;
    TextView tvTitle, tvYear, tvRuntime, tvOverview;
    RatingBar ratingBar;
    Button btnRent, btnBuy;
    RecyclerView reviewRecyclerView;

    // adapters
    private ReviewRecyclerViewAdapter adapter;
    private List<Review> reviews;

    DatabasePutService service = new DatabasePutService();

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        service = new DatabasePutService();

        // set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get movie
        movie = (Movie) getIntent().getSerializableExtra(getResources().getString(R.string.movie_intent_pass_key));

        reviews = new ArrayList<>();

        // reference to widgets
        posterImg = findViewById(R.id.detail_poster_img);
        tvTitle = findViewById(R.id.detail_title);
        tvYear = findViewById(R.id.detail_year);
        tvRuntime = findViewById(R.id.detail_runtime);
        tvOverview = findViewById(R.id.detail_overview);
        ratingBar = findViewById(R.id.detail_rating_bar);
        btnRent = findViewById(R.id.detail_btn_rent);
        btnBuy = findViewById(R.id.detail_btn_purchase);
        reviewRecyclerView = findViewById(R.id.detail_review_rv);

        initRecyclerView();

        getMovie();
        getReviews();

        btnBuy.setOnClickListener(this);
        btnRent.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.movie_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Log.d(TAG, "onOptionsItemSelected: Sharing movie");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, movie.title);
                intent.putExtra(Intent.EXTRA_TEXT, "check out on my new app.\n" + movie.overview);
                startActivity(Intent.createChooser(intent, "Share via"));
                break;
            case R.id.action_wishlist:
                Log.d(TAG, "onOptionsItemSelected: Adding to wishlist");
                service.wishlistMovie(movie);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_btn_purchase:
                Log.d(TAG, "onClick: Purchasing movie");
                service.purchaseMovie(movie);
                break;

            case R.id.detail_btn_rent:
                Log.d(TAG, "onClick: Renting movie");
                service.rentMovie(movie);
                break;
        }
    }

    private void getMovie() {
        StringRequest movieRequest = new StringRequest(Request.Method.GET, URLBuilder.getMovie(movie.id), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: 200 OK\n" + response);
                movie = Serializer.serializeMovie(response);
                populateFields();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });

        MyApplication.getRequestQueue().add(movieRequest);
    }

    private void getReviews() {
        StringRequest reviewRequest = new StringRequest(Request.Method.GET, URLBuilder.getReviews(movie.id), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String raw = object.getString("results");
                    reviews.addAll(Serializer.serializeReviews(raw));

                    Log.d(TAG, "onResponse: \n" + raw);

                    adapter.notifyDataSetChanged();
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

        MyApplication.getRequestQueue().add(reviewRequest);
    }

    private void populateFields() {
        tvTitle.setText(movie.title);
        tvYear.setText(movie.release_date);
        tvRuntime.setText(movie.runtime + " mins");
        tvOverview.setText(movie.overview);

        ratingBar.setRating((float) movie.vote_average / 2);

        Glide.with(this)
                .load(URLBuilder.imageUrl(movie.poster_path))
                .apply(new RequestOptions().placeholder(R.color.md_blue_grey_300))
                .into(posterImg);
    }

    private void initRecyclerView() {
        adapter = new ReviewRecyclerViewAdapter(this, reviews);
        reviewRecyclerView.setAdapter(adapter);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
