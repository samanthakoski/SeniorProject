package com.example.samanthakoski.snrproj;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {

    private static final String TAG = "MY_APP";
    public static final String USER_TAG = "USER";
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp app = FirebaseApp.initializeApp(this);
        user = null;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void setUser(FirebaseUser user) {
        Log.i(TAG, "User set");
        this.user = user;
    }

    public FirebaseUser getUser() {
        return this.user;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
