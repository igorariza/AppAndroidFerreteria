package com.pisky.pisky.util;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class setPersistenceEnabled extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
