package com.example.padhteraho.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.padhteraho.R;
import com.example.padhteraho.repository.VideoItem;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    public interface OnVideoClickListener{

        void onVideoClick(VideoItem video);

    }

    private final List<VideoItem> videoList;
    private final OnVideoClickListener listener;

    public VideoAdapter(List<VideoItem> videoList,
                        OnVideoClickListener listener){

        this.videoList = videoList;
        this.listener = listener;

    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video,
                        parent,
                        false);

        return new VideoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(
            @NonNull VideoViewHolder holder,
            int position
    ) {

        VideoItem video = videoList.get(position);

        holder.txtTitle.setText(video.getTitle());

        holder.txtSubtitle.setText(
                "Lecture " + (position + 1)
        );

        Glide.with(holder.itemView.getContext())
                .load(video.getThumbnail())
                .placeholder(R.drawable.placeholder)
                .into(holder.imgThumbnail);

        holder.itemView.setOnClickListener(v ->
                listener.onVideoClick(video));

    }

    @Override
    public int getItemCount() {

        return videoList.size();

    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{

        ImageView imgThumbnail;

        TextView txtTitle;

        TextView txtSubtitle;

        public VideoViewHolder(@NonNull View itemView) {

            super(itemView);

            imgThumbnail =
                    itemView.findViewById(R.id.imgThumbnail);

            txtTitle =
                    itemView.findViewById(R.id.txtTitle);

            txtSubtitle =
                    itemView.findViewById(R.id.txtSubtitle);

        }

    }

}