package com.kraken.assesment.FirebaseHelpers;

import android.app.admin.DelegatedAdminReceiver;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.midi.MidiOutputPort;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kraken.assesment.Models.Movie;

public class DatabasePutService {

    private static final String TAG = "DatabasePutService";

    DatabaseReference reference, wishlistRef, purchasedRef, likedRef, rentRef;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    public DatabasePutService() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users").child(firebaseAuth.getCurrentUser().getUid());
    }

    public void wishlistMovie(Movie movie) {
        wishlistRef = reference.child("wishlist").push();
        wishlistRef.setValue(movie).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: Firebase task completed");
            }
        });
    }

    public void purchaseMovie(Movie movie) {
        purchasedRef = reference.child("purchased").push();
        purchasedRef.setValue(movie).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: Purchase task completed");
            }
        });
    }

    public void rentMovie(Movie movie) {
        rentRef = reference.child("rented").push();
        rentRef.setValue(movie).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: Rent task completed");
            }
        });
    }
}
