package com.kraken.assesment.FirebaseHelpers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kraken.assesment.Models.Movie;

public class DatabasePutService {

    private static final String TAG = "DatabasePutService";

    private DatabaseReference reference;

    public DatabasePutService() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users").child(firebaseAuth.getCurrentUser().getUid());
    }

    public void wishlistMovie(Movie movie) {
        DatabaseReference wishlistRef = reference.child("wishlist").push();
        wishlistRef.setValue(movie).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: Firebase task completed");
            }
        });
    }

    public void likeMovie(Movie movie) {
        DatabaseReference rentRef = reference.child("liked").push();
        rentRef.setValue(movie).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: Rent task completed");
            }
        });
    }
}
