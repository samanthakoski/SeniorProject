package com.example.samanthakoski.snrproj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    private static final String TAG = "PROFILE_FRAGMENT";
    private static final String BACK_STACK_TAG = "Profile_Fragment_Root";
    private MyApplication application;
    private BaseActivity activity;
    private String userName;
    private String userEmail;
    private String userPass;
    private RelativeLayout nameBox;
    private TextView nameTV;
    private TextView emailTV;
    private RelativeLayout emailBox;
    private TextInputLayout passTIL;
    private EditText passET;
    private TextView logout;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "Profile Fragment created");

        application = (MyApplication) this.getActivity().getApplication();
        if (application != null) {
            userName = application.getUser().getDisplayName();
            userEmail = application.getUser().getEmail();
            //nameTV.setText(userName);
            Log.i(TAG, "Application found");
        } else {
            Log.d(TAG, "Application is null");
        }
        activity = (BaseActivity) getActivity();
        return inflater.inflate(
                R.layout.profile_fragment,
                container,
                false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameTV = view.findViewById(R.id.name_tv);
        nameBox = view.findViewById(R.id.name_box);

        nameBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUpdateName(nameTV.toString());
            }
        });

        emailTV = view.findViewById(R.id.email_tv);
        emailBox = view.findViewById(R.id.email_box);

        emailBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUpdateEmail(emailTV.toString());
            }
        });
        /*
        editTV = getView().findViewById(R.id.editProfTV);
        nameET = getView().findViewById(R.id.prof_name_et);
        emailET = getView().findViewById(R.id.prof_email_et);
        passET = getView().findViewById(R.id.prof_pass_et);
        passTIL = getView().findViewById(R.id.textInput_pass);
        nameTV = getView().findViewById(R.id.prof_name_tv);
        emailTV = getView().findViewById(R.id.prof_email_tv);
        checkButton = getView().findViewById(R.id.prof_check_button);
        backButton = getView().findViewById(R.id.prof_back_button);
        logout = getView().findViewById(R.id.logout_tv);
        nameTV.setText(userName);
        emailTV.setText(userEmail);
        editTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameET.setVisibility(View.VISIBLE);
                emailET.setVisibility(View.VISIBLE);
                passTIL.setVisibility(View.VISIBLE);
                passET.setVisibility(View.VISIBLE);
                nameET.setText(application.getUser().getDisplayName());
                emailET.setText(application.getUser().getEmail());
                nameTV.setVisibility(View.GONE);
                emailTV.setVisibility(View.GONE);
                editTV.setVisibility(View.GONE);
                checkButton.show();
                logout.setVisibility(View.GONE);
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = nameET.getText().toString();
                userEmail = emailET.getText().toString();
                userPass = passET.getText().toString();
                updateUser();
                nameTV.setVisibility(View.VISIBLE);
                nameTV.setText(userName);
                emailTV.setVisibility(View.VISIBLE);
                emailTV.setText(userEmail);
                editTV.setVisibility(View.VISIBLE);
                nameET.setVisibility(View.GONE);
                emailET.setVisibility(View.GONE);
                passTIL.setVisibility(View.GONE);
                passET.setVisibility(View.GONE);
                checkButton.hide();
                logout.setVisibility(View.VISIBLE);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Back button pressed, ending profile fragment");
                goBack();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Logout button pressed");
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
            }
        });
        */
    }
/*
    private void goBack() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    private void updateUser() {
        FirebaseUser user = application.getUser();
        if (!userPass.isEmpty()) {
            user.updatePassword(userPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.i(TAG, "User password successfully changed");
                    } else {
                        Log.i(TAG, "User password unsuccessfully changed: " +
                                task.getException().getMessage());
                        Toast.makeText(getActivity(),
                                "Password could not be changed",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        if (!userEmail.equals(user.getEmail())) {
            user.updateEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.i(TAG, "User email successfully changed");
                    } else {
                        Log.i(TAG, "User email unsuccessfully changed: " +
                                task.getException().getMessage());
                        Toast.makeText(getActivity(),
                                "Email could not be changed",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        if (!userName.equals(user.getDisplayName())) {
            user.updateProfile(new UserProfileChangeRequest.Builder()
                    .setDisplayName(userName).build())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i(TAG, "User name successfully changed");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i(TAG, "User name unsuccessfully changed: " + e.getMessage());
                            Toast.makeText(getActivity(),
                                    "Name could not be changed",
                                    Toast.LENGTH_LONG).show();
                        }
            });
        }
        application.setUser(user);
        userName = user.getDisplayName();
        userEmail = user.getEmail();
    }
*/

    private void startUpdateName(String name) {
        //Bundle bundle = new Bundle();
        UpdateName updateName = new UpdateName();
        /*
        bundle.putString("cur_name", name);
        updateName.setArguments(bundle);

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, updateName);
        ft.commit();
        */
        activity.getFragManager().beginTransaction()
                .replace(R.id.content_frame, updateName)
                .addToBackStack(BACK_STACK_TAG)
                .commit();
    }

    private void startUpdateEmail(String email) {
        Bundle bundle = new Bundle();
        UpdateEmail updateEmail = new UpdateEmail();
        /*
        bundle.putString("cur_name", name);
        updateName.setArguments(bundle);

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, updateEmail);
        ft.commit();
        */

    }


}