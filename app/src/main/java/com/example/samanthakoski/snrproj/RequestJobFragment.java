package com.example.samanthakoski.snrproj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class RequestJobFragment extends Fragment {

    private static final String TAG = "RequestJobFragment";
    private static final String BACK_STACK_TAG = "Request_Job_Fragment";
    private MyApplication app;
    private BaseMapActivity activity;
    private ImageButton exitBtn;
    private EditText titleET;
    private EditText payET;
    private EditText descET;
    private Button setLocBtn;
    private Button postJobBtn;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        activity = (BaseMapActivity) getActivity();
        app = (MyApplication) activity.getApplication();
        if (app == null) {
            Log.i(TAG, "app is null");
        } else {
            Log.i(TAG, "app is not null");
        }

        return inflater.inflate(
                R.layout.request_job,
                container,
                false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exitBtn = view.findViewById(R.id.exit_button);
        titleET = view.findViewById(R.id.title_et);
        payET = view.findViewById(R.id.pay_et);
        descET = view.findViewById(R.id.desc_et);
        setLocBtn = view.findViewById(R.id.set_loc_button);
        postJobBtn = view.findViewById(R.id.post_job);
    }
}
