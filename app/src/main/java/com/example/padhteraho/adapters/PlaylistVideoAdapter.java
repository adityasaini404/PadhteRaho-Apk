package com.example.padhteraho.adapters;


import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.padhteraho.R;
import com.example.padhteraho.repository.VideoItem;
import com.google.android.material.card.MaterialCardView;


import java.util.List;



public class PlaylistVideoAdapter extends RecyclerView.Adapter<PlaylistVideoAdapter.VideoViewHolder> {



    private final List<VideoItem> videoList;


    private int playingPosition;


    private final OnVideoClickListener listener;





    public interface OnVideoClickListener {


        void onVideoClick(int position);


    }





    public PlaylistVideoAdapter(

            List<VideoItem> videoList,

            int playingPosition,

            OnVideoClickListener listener

    ){

        this.videoList = videoList;

        this.playingPosition = playingPosition;

        this.listener = listener;

    }








    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(

            @NonNull ViewGroup parent,

            int viewType

    ){


        View view =
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(
                                R.layout.item_player_playlist,
                                parent,
                                false
                        );



        return new VideoViewHolder(view);


    }









    @Override
    public void onBindViewHolder(

            @NonNull VideoViewHolder holder,

            int position

    ){



        VideoItem video =
                videoList.get(position);




        holder.title.setText(
                video.getTitle()
        );





        if(video.getTeacher()!=null &&
                !video.getTeacher().isEmpty()){


            holder.teacher.setText(
                    video.getTeacher()
            );


        }
        else{


            holder.teacher.setText(
                    "PadhteRaho"
            );


        }







        holder.duration.setText(
                "Video Lecture"
        );






        // Thumbnail


        Glide.with(holder.itemView.getContext())

                .load(video.getThumbnail())

                .placeholder(
                        R.drawable.padhteraho
                )

                .error(
                        R.drawable.padhteraho
                )

                .into(
                        holder.thumbnail
                );










        // Current playing design


        if(position == playingPosition){



            holder.card.setStrokeColor(
                    Color.parseColor("#2563EB")
            );



            holder.card.setStrokeWidth(
                    3
            );



            holder.playIcon.setVisibility(
                    View.VISIBLE
            );



            holder.itemView
                    .setScaleX(1.02f);



            holder.itemView
                    .setScaleY(1.02f);




            animatePlayIcon(
                    holder.playIcon
            );



        }

        else {



            holder.card.setStrokeWidth(
                    1
            );



            holder.card.setStrokeColor(
                    Color.parseColor("#E5E7EB")
            );



            holder.playIcon.setVisibility(
                    View.GONE
            );



            holder.itemView
                    .setScaleX(1f);



            holder.itemView
                    .setScaleY(1f);



        }









        holder.itemView.setOnClickListener(v -> {



            if(listener!=null){


                listener.onVideoClick(position);


            }



        });



    }








    private void animatePlayIcon(ImageView icon){



        ObjectAnimator animator =

                ObjectAnimator.ofFloat(

                        icon,

                        "alpha",

                        0.5f,

                        1f

                );



        animator.setDuration(700);

        animator.setRepeatCount(
                ObjectAnimator.INFINITE
        );

        animator.setRepeatMode(
                ObjectAnimator.REVERSE
        );

        animator.setInterpolator(
                new DecelerateInterpolator()
        );


        animator.start();



    }









    public void setPlayingPosition(int position){



        int oldPosition =
                playingPosition;



        playingPosition =
                position;



        notifyItemChanged(
                oldPosition
        );



        notifyItemChanged(
                playingPosition
        );



    }









    @Override
    public int getItemCount(){

        return videoList == null ?

                0 :

                videoList.size();

    }









    static class VideoViewHolder
            extends RecyclerView.ViewHolder {



        MaterialCardView card;


        ImageView thumbnail;

        ImageView playIcon;


        TextView title;

        TextView teacher;

        TextView duration;






        public VideoViewHolder(
                @NonNull View itemView
        ){

            super(itemView);



            card =
                    itemView.findViewById(
                            R.id.cardVideo
                    );



            thumbnail =
                    itemView.findViewById(
                            R.id.imgThumbnail
                    );



            playIcon =
                    itemView.findViewById(
                            R.id.imgPlaying
                    );



            title =
                    itemView.findViewById(
                            R.id.txtVideoTitle
                    );



            teacher =
                    itemView.findViewById(
                            R.id.txtTeacher
                    );



            duration =
                    itemView.findViewById(
                            R.id.txtDuration
                    );


        }


    }


}