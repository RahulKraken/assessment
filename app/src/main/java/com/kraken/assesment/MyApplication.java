package com.kraken.assesment;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    // app wide user instance
    public static FirebaseUser currentUser;

    private FirebaseAuth firebaseAuth;

    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Application Started");

        // get auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        requestQueue = Volley.newRequestQueue(this);

        // check if user is signed in
        if (firebaseAuth.getCurrentUser() != null) {
            currentUser = firebaseAuth.getCurrentUser();
        } else {
            currentUser = null;
        }
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
