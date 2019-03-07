package com.example.samanthakoski.snrproj;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupFragment extends Fragment {

    private static final String TAG = "SignupFragment";
    private static final String BACK_STACK_TAG = "Login_Activity_Root";
    private MyApplication app;
    private LoginActivity activity;
    private EditText emailET;
    private EditText passET;
    private Button signupBtn;
    private ImageButton backBtn;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (LoginActivity) getActivity();
        app = (MyApplication) activity.getApplication();
        mAuth = activity.getmAuth();
        if (app == null) {
            Log.i(TAG, "app is null");
        } else {
            Log.i(TAG, "app is not null");
        }
        return inflater.inflate(
                R.layout.signup_page,
                container,
                false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailET = view.findViewById(R.id.signup_email_et);
        passET = view.findViewById(R.id.signup_password_et);
        signupBtn = view.findViewById(R.id.signup_button);
        backBtn = view.findViewById(R.id.signup_back_button);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeNewUser();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Back button pressed");
                activity.getFragManager().popBackStack(BACK_STACK_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
    }

    private void makeNewUser() {
        if (emailET.getText().toString().isEmpty() || passET.getText().toString().isEmpty()) return;
        mAuth.createUserWithEmailAndPassword(emailET.getText().toString(),
                passET.getText().toString())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i(TAG, "user id: " + user.getUid());
                            if (app.getDatabaseReference() == null) {
                                Log.i(TAG, "ref is null");
                            }
                            app.getDatabaseReference().child("users").setValue(user.getUid());
                            activity.getFragManager().popBackStack(BACK_STACK_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            activity.updateUI(user);
                        } else {
                            Log.i(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity,
                                    "Could not create user.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
