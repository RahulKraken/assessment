package com.kraken.assesment.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.kraken.assesment.R;

public class ExploreActivity extends AppCompatActivity {

    private static final String TAG = "ExploreActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: explore activity launched");
        setContentView(R.layout.activity_explore);
    }
}
