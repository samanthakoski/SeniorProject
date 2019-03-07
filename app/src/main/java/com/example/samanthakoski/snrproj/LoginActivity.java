package com.example.samanthakoski.snrproj;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends FragmentActivity {

    private EditText email;
    private EditText password;
    private Button signInBtn;
    private TextView signUpBtn;
    private static final String TAG = "LoginActivity";
    private static final String BACK_STACK_ROOT_TAG = "Login_Activity_Root";
    private FirebaseAuth mAuth;
    private MyApplication app;
    private FragmentManager fragManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_login);
        app = (MyApplication) getApplication();
        fragManager = getSupportFragmentManager();
        email = findViewById(R.id.login_email_et);
        password = findViewById(R.id.login_password_et);
        signInBtn = findViewById(R.id.signin_button);
        signUpBtn = findViewById(R.id.signup_tv);
        mAuth = FirebaseAuth.getInstance();
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SignupFragment();
                fragManager.popBackStack(BACK_STACK_ROOT_TAG, android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(BACK_STACK_ROOT_TAG)
                        .commit();
            }
        });
        Log.i(TAG, "Login Activity created.");
        FirebaseUser curUser = mAuth.getCurrentUser();
        if (curUser != null) {
            Log.i(TAG, "Current user logged in: " + curUser.getEmail());
            updateUI(curUser);
        }
    }


    public void updateUI(FirebaseUser user) {
        if (user == null) return;
        app.setUser(user);
        Intent intent = new Intent(this, BaseActivity.class);
        startActivity(intent);
    }

    private void validate() {
        if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) return;
        mAuth.signInWithEmailAndPassword(email.getText().toString(),
                password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this,
                                    "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public FragmentManager getFragManager() {
        return fragManager;
    }

}
