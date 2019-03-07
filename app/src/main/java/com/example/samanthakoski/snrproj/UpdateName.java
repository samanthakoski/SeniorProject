package com.example.samanthakoski.snrproj;

import android.os.Bundle;
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
import android.widget.ImageView;

public class UpdateName extends Fragment {

    private static final String TAG = "UpdateNameFragment";
    private static final String BACK_STACK_TAG = "Profile_Fragment_Root";
    private MyApplication application;
    private BaseActivity activity;
    private String name;
    private EditText name_edit_text;
    private ImageView exit_button;
    private ImageButton clear_text;
    private Button update_button;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "Update Fragment created");

        application = (MyApplication) this.getActivity().getApplication();
        if (application != null) {
            name = application.getUser().getDisplayName();
            Log.i(TAG, "Application found");
            if (getActivity().getActionBar() == null) {
                Log.i(TAG, "Action bar is null");
            } else {
                Log.i(TAG, "Action bar is NOT null");
            }
        } else {
            Log.d(TAG, "Application is null");
        }
        activity = (BaseActivity) getActivity();
        return inflater.inflate(
                R.layout.update_name,
                container,
                false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name_edit_text = view.findViewById(R.id.name_edit_text);
        exit_button = view.findViewById(R.id.exit_edit_name);
        clear_text = view.findViewById(R.id.clear_text);
        update_button = view.findViewById(R.id.update_button);

        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_edit_text.setText("");
            }
        });

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Exit button pressed");
                Log.i(TAG, "back stack count: " + activity.getFragManager().getBackStackEntryCount());
                Log.i(TAG, "back stack list: " + activity.getFragManager().getFragments());
                activity.getFragManager().popBackStack(BACK_STACK_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });


    }

}
