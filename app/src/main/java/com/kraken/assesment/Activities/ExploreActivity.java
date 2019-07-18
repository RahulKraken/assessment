package com.kraken.assesment.Activities;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.kraken.assesment.Adapters.GenreListRecyclerViewAdapter;
import com.kraken.assesment.Models.Category;
import com.kraken.assesment.R;
import com.kraken.assesment.Utils.Constants;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExploreActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // base URL - https://api.themoviedb.org/3/movie/550?api_key=e72b4559ffdb15dbb9c5de5c94948f25

    private static final String TAG = "ExploreActivity";

    private RecyclerView recyclerViewMain;
    private NavigationView navigationView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        Log.d(TAG, "onCreate: Explore activity started");

        setContentView(R.layout.activity_explore);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        inflateNavDrawerMenu();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // recycler view stuff
        initRecyclerView();

        // add logout button to nav drawer
        addLogoutButton();
    }

    private void inflateNavDrawerMenu() {
        if (firebaseAuth.getCurrentUser() != null) navigationView.inflateMenu(R.menu.activity_explore_drawer_login);
        else navigationView.inflateMenu(R.menu.activity_explore_drawer_logout);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.explore, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Log.d(TAG, "onOptionsItemSelected: Navigating to search activity");
            startActivity(new Intent(this, SearchActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = new Intent(this, ContainerActivity.class);

        switch (id) {
            case R.id.nav_genre:
                Log.d(TAG, "onNavigationItemSelected: Expanding ListView.");
                break;

            case R.id.nav_purchased:
                Log.d(TAG, "onNavigationItemSelected: Purchased movies");
                intent.putExtra(getResources().getString(R.string.action_intent_pass_key), "PURCHASED");
                startActivity(intent);
                break;

            case R.id.nav_wishlist:
                Log.d(TAG, "onNavigationItemSelected: Wishlisted movies");
                intent.putExtra(getResources().getString(R.string.action_intent_pass_key), "WISHLIST");
                startActivity(intent);
                break;

            case R.id.nav_rented:
                Log.d(TAG, "onNavigationItemSelected: Likes movies");
                intent.putExtra(getResources().getString(R.string.action_intent_pass_key), "RENTED");
                startActivity(intent);
                break;

            case R.id.nav_share:
                Log.d(TAG, "onNavigationItemSelected: Sharing");
                break;

            case R.id.nav_feedback_help:
                Log.d(TAG, "onNavigationItemSelected: Helping and sending Feedback");
                break;

            case R.id.nav_settings:
                Log.d(TAG, "onNavigationItemSelected: Settings");
                break;

            case 10:
                Log.d(TAG, "onNavigationItemSelected: Logout button clicked");
                firebaseAuth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initRecyclerView() {
        recyclerViewMain = findViewById(R.id.recycler_view_main);
        List<Category> categories = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : Constants.genres.entrySet()) {
            categories.add(new Category(entry.getKey()));
        }

        Log.d(TAG, "initRecyclerView: categories -> " + categories.toString());

        GenreListRecyclerViewAdapter adapter = new GenreListRecyclerViewAdapter(this, categories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        recyclerViewMain.setAdapter(adapter);
        recyclerViewMain.setLayoutManager(layoutManager);
    }

    private void addLogoutButton() {
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            Menu menu = navigationView.getMenu();
            menu.add(0, 10, Menu.NONE, "Logout");
        }
    }
}
