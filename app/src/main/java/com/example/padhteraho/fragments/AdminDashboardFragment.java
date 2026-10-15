package com.example.padhteraho.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.padhteraho.MainActivity;
import com.example.padhteraho.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboardFragment extends Fragment {

    private MaterialCardView cardCategory;
    private MaterialCardView cardCourse;
    private MaterialCardView cardPlaylist;
    MaterialCardView cardAdminNotes;

    private MaterialButton btnLogout;


    public AdminDashboardFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.fragment_admin_dashboard,
                container,
                false
        );


        cardCategory = view.findViewById(R.id.cardCategory);
        cardCourse = view.findViewById(R.id.cardCourse);
        cardPlaylist = view.findViewById(R.id.cardPlaylist);
        cardAdminNotes = view.findViewById(R.id.cardAdminNotes);
        btnLogout = view.findViewById(R.id.btnLogout);


        cardCategory.setOnClickListener(v ->
                ((MainActivity) requireActivity())
                        .openFragment(new AdminCategoryFragment(), null)
        );


        cardCourse.setOnClickListener(v ->
                ((MainActivity) requireActivity())
                        .openFragment(new AdminCourseFragment(), null)
        );


        cardPlaylist.setOnClickListener(v ->
                ((MainActivity) requireActivity())
                        .openFragment(new AdminPlaylistFragment(), null)
        );

        cardAdminNotes.setOnClickListener(v -> ((MainActivity) requireActivity())
                .openFragment(new AdminNotesFragment(), null)
        );

        btnLogout.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            ((MainActivity) requireActivity())
                    .openFragment(new SettingsFragment(), null);

        });


        return view;
    }

}