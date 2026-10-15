package com.example.padhteraho.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padhteraho.R;
import com.example.padhteraho.adapters.VideoAdapter;
import com.example.padhteraho.player.PlayerActivity;
import com.example.padhteraho.repository.VideoItem;
import com.example.padhteraho.repository.YoutubeRepository;

import java.util.ArrayList;
import java.util.List;

public class VideoListFragment extends Fragment
        implements VideoAdapter.OnVideoClickListener {

    private RecyclerView recyclerVideos;

    private TextView txtPlaylistTitle;

    private VideoAdapter adapter;

    private List<VideoItem> videoList;

    private String playlistId;

    private String playlistTitle;

    public VideoListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(

            @NonNull LayoutInflater inflater,

            ViewGroup container,

            Bundle savedInstanceState

    ) {

        View view = inflater.inflate(
                R.layout.fragment_video_list,
                container,
                false
        );

        recyclerVideos =
                view.findViewById(R.id.recyclerVideos);

        txtPlaylistTitle =
                view.findViewById(R.id.txtPlaylistTitle);

        recyclerVideos.setLayoutManager(
                new LinearLayoutManager(getContext())
        );

        videoList = new ArrayList<>();

        adapter = new VideoAdapter(
                videoList,
                this
        );

        recyclerVideos.setAdapter(adapter);

        if (getArguments() != null) {

            playlistId =
                    getArguments().getString("playlistId");

            playlistTitle =
                    getArguments().getString("playlistTitle");

            txtPlaylistTitle.setText(playlistTitle);

        }

        loadVideos();

        return view;

    }

    private void loadVideos() {

        YoutubeRepository repository =
                new YoutubeRepository();

        repository.getPlaylistVideos(

                playlistId,

                new YoutubeRepository.OnVideosLoaded() {

                    @Override
                    public void onSuccess(List<VideoItem> videos) {

                        videoList.clear();

                        videoList.addAll(videos);

                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(String message) {

                        Toast.makeText(
                                getContext(),
                                message,
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                }

        );

    }

    @Override
    public void onVideoClick(VideoItem video) {

        Intent intent = new Intent(getContext(), PlayerActivity.class);

        intent.putParcelableArrayListExtra(
                "videos",
                new ArrayList<>(videoList)
        );

        intent.putExtra(
                "position",
                videoList.indexOf(video)
        );

        startActivity(intent);

    }

}