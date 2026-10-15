package com.example.padhteraho;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.padhteraho.fragments.GuideFragment;
import com.example.padhteraho.fragments.HomeFragment;
import com.example.padhteraho.fragments.NotesFragment;
import com.example.padhteraho.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.content.res.Configuration;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Default Screen
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());

        if (controller != null) {
            boolean isDarkMode =
                    (getResources().getConfiguration().uiMode
                            & Configuration.UI_MODE_NIGHT_MASK)
                            == Configuration.UI_MODE_NIGHT_YES;

            // Light mode = black icons
            // Dark mode = white icons
            controller.setAppearanceLightStatusBars(!isDarkMode);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            Fragment current = getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container);

            if (id == R.id.nav_home && !(current instanceof HomeFragment)) {

                loadFragment(new HomeFragment());

            } else if (id == R.id.nav_notes && !(current instanceof NotesFragment)) {

                loadFragment(new NotesFragment());

            } else if (id == R.id.nav_guide && !(current instanceof GuideFragment)) {

                loadFragment(new GuideFragment());

            } else if (id == R.id.nav_settings && !(current instanceof SettingsFragment)) {

                loadFragment(new SettingsFragment());

            }

            return true;
        });

    }

    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

    }
    public void openFragment(Fragment fragment, Bundle bundle) {


        if (bundle != null) {
            fragment.setArguments(bundle);
        }


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onBackPressed() {

        // Agar koi internal fragment (Admin/Login etc.) hai
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            getSupportFragmentManager().popBackStack();
            return;
        }

        Fragment current = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        // Agar Home nahi hai to Home par aa jao
        if (!(current instanceof HomeFragment)) {

            bottomNavigationView.setSelectedItemId(R.id.nav_home);

        } else {

            super.onBackPressed();

        }
    }
}