package com.kraken.assesment.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kraken.assesment.Adapters.SearchRecyclerViewAdapter;
import com.kraken.assesment.Models.Movie;
import com.kraken.assesment.MyApplication;
import com.kraken.assesment.R;
import com.kraken.assesment.Utils.Serializer;
import com.kraken.assesment.Utils.URLBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SearchActivity";

    private EditText et_Search;

    private SearchRecyclerViewAdapter adapter;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        et_Search = findViewById(R.id.et_search_key);
        et_Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    searchMovies(String.valueOf(et_Search.getText()));
                    hideSoftKeyboard();
                    return true;
                }
                return false;
            }
        });

        movies = new ArrayList<>();
        adapter = new SearchRecyclerViewAdapter(this, movies);

        ImageButton btn_clear = findViewById(R.id.clear_btn);
        btn_clear.setOnClickListener(this);

        initRecyclerView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.clear_btn) {
            et_Search.setText("");
        }
    }

    private void searchMovies(String key) {
        StringRequest searchRequest = new StringRequest(Request.Method.GET, URLBuilder.searchMovie(key), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String raw = object.getString("results");
                    Log.d(TAG, "onResponse: raw: \n" + raw);
                    movies.addAll(Serializer.serializeMovies(raw));
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

        MyApplication.getRequestQueue().add(searchRequest);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.search_recycler_view);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
