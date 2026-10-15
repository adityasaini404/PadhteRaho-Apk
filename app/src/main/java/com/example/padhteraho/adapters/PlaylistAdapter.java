package com.example.padhteraho.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padhteraho.R;
import com.example.padhteraho.listeners.OnPlaylistClickListener;
import com.example.padhteraho.models.Playlist;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    private final List<Playlist> list;
    private final OnPlaylistClickListener listener;

    // Light Background Colors
    private final int[] lightColors = {

            Color.parseColor("#E8F0FF"), // Blue
            Color.parseColor("#DCFCE7"), // Green
            Color.parseColor("#F3E8FF"), // Purple
            Color.parseColor("#FEF3C7"), // Orange
            Color.parseColor("#FCE7F3"), // Pink
            Color.parseColor("#E0F2FE"), // Sky
            Color.parseColor("#ECFCCB"), // Lime
            Color.parseColor("#FEE2E2"), // Red
            Color.parseColor("#EDE9FE"), // Indigo
            Color.parseColor("#CCFBF1"), // Teal
            Color.parseColor("#FAF5FF"), // Violet
            Color.parseColor("#FFF7ED"), // Amber
            Color.parseColor("#F0FDF4"), // Emerald
            Color.parseColor("#F5F3FF"), // Deep Purple
            Color.parseColor("#FDF2F8")  // Rose

    };

    private final int[] darkColors = {

            Color.parseColor("#2563EB"),
            Color.parseColor("#10B981"),
            Color.parseColor("#9333EA"),
            Color.parseColor("#F59E0B"),
            Color.parseColor("#DB2777"),
            Color.parseColor("#0284C7"),
            Color.parseColor("#65A30D"),
            Color.parseColor("#DC2626"),
            Color.parseColor("#4F46E5"),
            Color.parseColor("#0F766E"),
            Color.parseColor("#7C3AED"),
            Color.parseColor("#EA580C"),
            Color.parseColor("#16A34A"),
            Color.parseColor("#6D28D9"),
            Color.parseColor("#E11D48")

    };

    public PlaylistAdapter(List<Playlist> list,
                           OnPlaylistClickListener listener) {

        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_playlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {

        Playlist playlist = list.get(position);

        holder.title.setText(playlist.getTitle());
        holder.teacher.setText(playlist.getTeacher());

        // Number
        holder.txtNumber.setText(
                String.format("%02d", position + 1));

        // Color Rotation
        int index = position % lightColors.length;

        holder.leftBar.setBackgroundColor(
                darkColors[index]);

        holder.numberCard.setCardBackgroundColor(
                lightColors[index]);

        holder.txtNumber.setTextColor(
                darkColors[index]);

        holder.chipPlaylist.setChipBackgroundColor(
                ColorStateList.valueOf(lightColors[index]));

        holder.chipPlaylist.setTextColor(
                ColorStateList.valueOf(darkColors[index]));

        holder.arrowCard.setCardBackgroundColor(
                lightColors[index]);

        holder.arrowIcon.setColorFilter(
                darkColors[index]);

        // Click Animation
        holder.itemView.setOnClickListener(v -> {

            v.animate()
                    .scaleX(0.96f)
                    .scaleY(0.96f)
                    .setDuration(80)
                    .withEndAction(() -> {

                        v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setInterpolator(new OvershootInterpolator())
                                .setDuration(180);

                        if (listener != null) {
                            listener.onPlaylistClick(playlist);
                        }

                    });

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View leftBar;

        MaterialCardView numberCard;
        MaterialCardView arrowCard;

        TextView txtNumber;
        TextView title;
        TextView teacher;

        Chip chipPlaylist;

        ImageView arrowIcon;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            leftBar = itemView.findViewById(R.id.leftBar);

            numberCard = itemView.findViewById(R.id.imgThumbnail);

            txtNumber = itemView.findViewById(R.id.txtNumber);

            title = itemView.findViewById(R.id.txtPlaylistTitle);

            teacher = itemView.findViewById(R.id.txtTeacher);

            chipPlaylist = itemView.findViewById(R.id.chipPlaylist);

            arrowCard = itemView.findViewById(R.id.arrowCard);

            arrowIcon = itemView.findViewById(R.id.arrowIcon);
        }
    }
}