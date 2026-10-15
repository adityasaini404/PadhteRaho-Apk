package com.example.padhteraho.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.padhteraho.R;
import com.example.padhteraho.models.Playlist;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class AdminPlaylistFragment extends Fragment {

    private TextInputEditText edtPlaylistTitle, edtTeacher, edtPlaylistLink;
    private Spinner spinnerCourse;
    private MaterialButton btnSavePlaylist;
    private ProgressBar progressBar;

    private DatabaseReference courseRef, playlistRef;

    private ArrayList<String> courseList;
    private ArrayAdapter<String> adapter;

    private ArrayList<String> courseIdList;
    private ArrayList<String> courseNameList;

    public AdminPlaylistFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_playlist, container, false);

        edtPlaylistTitle = view.findViewById(R.id.edtPlaylistTitle);
        edtTeacher = view.findViewById(R.id.edtTeacher);
        edtPlaylistLink = view.findViewById(R.id.edtPlaylistLink);

        spinnerCourse = view.findViewById(R.id.spinnerCourse);
        btnSavePlaylist = view.findViewById(R.id.btnSavePlaylist);
        progressBar = view.findViewById(R.id.progressBar);

        courseRef = FirebaseDatabase.getInstance().getReference("courses");
        playlistRef = FirebaseDatabase.getInstance().getReference("playlists");

        courseIdList = new ArrayList<>();
        courseNameList = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                courseNameList
        );

        spinnerCourse.setAdapter(adapter);

        loadCourses();

        btnSavePlaylist.setOnClickListener(v -> savePlaylist());

        return view;
    }

    private void loadCourses() {

        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                courseIdList.clear();
                courseNameList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {

                    String id = data.child("id").getValue(String.class);
                    String title = data.child("title").getValue(String.class);

                    if (id != null && title != null) {

                        courseIdList.add(id);
                        courseNameList.add(title);

                    }
                }

                adapter.notifyDataSetChanged();

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savePlaylist() {

        String title = edtPlaylistTitle.getText().toString().trim();
        String teacher = edtTeacher.getText().toString().trim();
        String playlistId = edtPlaylistLink.getText().toString().trim();
        int position = spinnerCourse.getSelectedItemPosition();

        String courseId = courseIdList.get(position);
        String courseName = courseNameList.get(position);

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(playlistId)) {
            Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = playlistRef.push().getKey();

        Playlist playlist = new Playlist(
                id,
                courseId,
                courseName,
                title,
                teacher,
                getPlaylistId(playlistId),
                ""
        );

        playlistRef.child(id).setValue(playlist)
                .addOnSuccessListener(unused ->
                        Toast.makeText(getContext(), "Playlist Added", Toast.LENGTH_SHORT).show()
                );
    }

    private String getPlaylistId(String link) {

        if (link == null || link.trim().isEmpty()) {
            return "";
        }

        if (link.contains("list=")) {
            return link.substring(link.indexOf("list=") + 5);
        }

        return link.trim();
    }
}