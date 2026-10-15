package com.example.padhteraho.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padhteraho.R;
import com.example.padhteraho.adapters.PlaylistAdapter;
import com.example.padhteraho.listeners.OnPlaylistClickListener;
import com.example.padhteraho.models.Playlist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import com.example.padhteraho.repository.VideoItem;
import com.example.padhteraho.repository.YoutubeRepository;

public class CourseChannelsFragment extends Fragment
        implements OnPlaylistClickListener {

    private TextView txtCourseTitle;
    private RecyclerView recyclerPlaylists;
    private ProgressBar progressBar;

    private List<Playlist> playlistList;
    private PlaylistAdapter adapter;

    private DatabaseReference playlistRef;

    private String courseId;
    private String courseTitle;

    public CourseChannelsFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_course_channels,
                container,
                false
        );

        txtCourseTitle = view.findViewById(R.id.txtCourseTitle);
        recyclerPlaylists = view.findViewById(R.id.recyclerPlaylists);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerPlaylists.setLayoutManager(
                new LinearLayoutManager(getContext())
        );

        playlistList = new ArrayList<>();
        adapter = new PlaylistAdapter(playlistList, this);
        recyclerPlaylists.setAdapter(adapter);

        // Firebase
        playlistRef = FirebaseDatabase.getInstance().getReference("playlists");

        // Arguments
        if (getArguments() != null) {

            courseId = getArguments().getString("courseId");
            courseTitle = getArguments().getString("courseTitle");

            android.util.Log.d(
                    "CHANNEL_FRAGMENT",
                    "Received courseId = " + courseId +
                            " | courseTitle = " + courseTitle
            );

            txtCourseTitle.setText(courseTitle);
        }

        loadPlaylists();

        return view;
    }

    private void loadPlaylists() {

        progressBar.setVisibility(View.VISIBLE);

        playlistRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                playlistList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {

                    Playlist playlist = data.getValue(Playlist.class);

                    if (playlist != null) {

                        android.util.Log.d(
                                "PLAYLIST_DEBUG",
                                "Firebase CourseId = " + playlist.getCourseId()
                                        + " | Selected CourseId = " + courseId
                                        + " | CourseName = " + playlist.getCourseName()
                                        + " | Title = " + playlist.getTitle()
                        );

                        if (playlist.getCourseId() != null &&
                                playlist.getCourseId().equals(courseId)) {

                            playlistList.add(playlist);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                if (playlistList.isEmpty()) {
                    Toast.makeText(getContext(),
                            "No playlists found",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressBar.setVisibility(View.GONE);

                Toast.makeText(getContext(),
                        error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void testYoutubeApi(String playlistId){

        YoutubeRepository repository = new YoutubeRepository();

        repository.getPlaylistVideos(

                playlistId,

                new YoutubeRepository.OnVideosLoaded() {

                    @Override
                    public void onSuccess(List<VideoItem> videos) {

                        Log.d("YOUTUBE_API",
                                "Total Videos = " + videos.size());

                        for(VideoItem video : videos){

                            Log.d("VIDEO_TITLE",
                                    video.getTitle());

                            Log.d("VIDEO_ID",
                                    video.getVideoId());

                        }

                    }

                    @Override
                    public void onError(String message) {

                        Log.e("YOUTUBE_API",message);

                    }
                }

        );

    }
    @Override
    public void onPlaylistClick(Playlist playlist) {

        com.example.padhteraho.fragments.VideoListFragment fragment =
                new com.example.padhteraho.fragments.VideoListFragment();

        Bundle bundle = new Bundle();

        bundle.putString(
                "playlistId",
                playlist.getPlaylistId()
        );

        bundle.putString(
                "playlistTitle",
                playlist.getTitle()
        );

        fragment.setArguments(bundle);

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.fragment_container,
                        fragment
                )
                .addToBackStack(null)
                .commit();

    }
}