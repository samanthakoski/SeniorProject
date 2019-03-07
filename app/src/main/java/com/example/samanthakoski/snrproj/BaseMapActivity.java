package com.example.samanthakoski.snrproj;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class BaseMapActivity extends FragmentActivity {

    private static String TAG = "BaseMapActivity";
    private static String BACK_STACK_ROOT_TAG = "Base_Map_Activity_Root";
    private MyApplication app;
    private FragmentManager fragManager;
    private DrawerLayout drawerLayout;
    private ImageButton drawerIcon;
    private Button requestJobBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_map_main);

        app = (MyApplication) getApplication();
        fragManager = getSupportFragmentManager();
        drawerLayout = findViewById(R.id.base_map_drawer_layout);
        drawerIcon = findViewById(R.id.nav_button);
        requestJobBtn = findViewById(R.id.requestJob_button);

        drawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        displaySelectedScreen(menuItem.getItemId());
                        return true;
                    }
                }
        );

        requestJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RequestJobFragment();
                fragManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(BACK_STACK_ROOT_TAG)
                        .commit();
            }
        });

    }

    public FragmentManager getFragManager() {
        return fragManager;
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_profile:
                Log.i(TAG, "Activity Profile selected");
                fragment = new ProfileFragment();
                break;
        }
        if (fragment != null) {
            fragManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
