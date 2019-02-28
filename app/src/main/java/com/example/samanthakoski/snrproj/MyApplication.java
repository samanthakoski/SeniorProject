package com.example.samanthakoski.snrproj;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;

public class MyApplication extends Application {

    private static final String TAG = "MY_APP";
    public static final String USER_TAG = "USER";
    private FirebaseUser user;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp app = FirebaseApp.initializeApp(this);
        user = null;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public FirebaseUser getUser() {
        return this.user;
    }
}
