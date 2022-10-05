package com.example.firebaseauthentication.application;

import android.app.Application;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class CustomApplicationClass extends Application {
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate() {
        super.onCreate();
//        Log.i("Dummy12345", "onCreate: "+FirebaseAuth.getInstance().signInAnonymously());
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
}
