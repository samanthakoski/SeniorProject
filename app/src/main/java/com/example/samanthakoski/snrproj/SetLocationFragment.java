package com.example.samanthakoski.snrproj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.GoogleMap;

public class SetLocationFragment extends Fragment {

    private static final String TAG = "SetLocationFragment";
    private static final String BACK_STACK_TAG_ROOT = "Request_Job_Fragment";
    private MyApplication app;
    private BaseMapActivity activity;
    private ImageButton backBtn;
    private Button selectLocBtn;

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
                R.layout.set_job_location_fragment,
                container,
                false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       backBtn = view.findViewById(R.id.set_loc_back_button);
       selectLocBtn = view.findViewById(R.id.select_loc);

    }
}
