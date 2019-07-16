package com.kraken.assesment;

import android.app.Application;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    // app wide user instance
    public static FirebaseUser currentUser;

    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Application Started");

        // get auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        // check if user is signed in
        if (firebaseAuth.getCurrentUser() != null) {
            currentUser = firebaseAuth.getCurrentUser();
        } else {
            currentUser = null;
        }
    }
}
