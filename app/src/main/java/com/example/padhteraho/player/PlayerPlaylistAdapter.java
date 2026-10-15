package com.example.padhteraho.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padhteraho.R;
import com.example.padhteraho.repository.VideoItem;

import java.util.List;

public class PlayerPlaylistAdapter
        extends RecyclerView.Adapter<PlayerPlaylistAdapter.ViewHolder> {

    public interface OnVideoSelected{

        void onVideoSelected(int position);

    }

    private List<VideoItem> list;

    private int playingPosition;

    private OnVideoSelected listener;

    public PlayerPlaylistAdapter(
            List<VideoItem> list,
            int playingPosition,
            OnVideoSelected listener
    ){

        this.list=list;
        this.playingPosition=playingPosition;
        this.listener=listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_player_playlist,
                        parent,
                        false
                );

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        VideoItem item=list.get(position);

        holder.title.setText(item.getTitle());

        holder.teacher.setText(item.getTeacher());

        if(position==playingPosition){

            holder.playing.setVisibility(View.VISIBLE);

        }else{

            holder.playing.setVisibility(View.GONE);

        }

        holder.itemView.setOnClickListener(v->{

            listener.onVideoSelected(position);

        });

    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    public void setPlayingPosition(int position){

        playingPosition=position;

        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        TextView teacher;

        ImageView playing;

        ViewHolder(View itemView){

            super(itemView);

            title=itemView.findViewById(R.id.txtVideoTitle);

            teacher=itemView.findViewById(R.id.txtTeacher);

            playing=itemView.findViewById(R.id.imgPlaying);

        }

    }

}