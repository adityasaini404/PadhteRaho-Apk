package com.example.padhteraho.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.padhteraho.R;
import com.example.padhteraho.models.Note;

import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {


    private Context context;
    private List<Note> noteList;


    private final String[] colors = {

            "#FFE082",
            "#A5D6A7",
            "#90CAF9",
            "#CE93D8",
            "#FFAB91",
            "#80CBC4",
            "#F48FB1",
            "#B39DDB"

    };


    public NoteAdapter(Context context, List<Note> noteList) {

        this.context = context;
        this.noteList = noteList;

    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {


        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_note, parent, false);


        return new NoteViewHolder(view);

    }



    @Override
    public void onBindViewHolder(
            @NonNull NoteViewHolder holder,
            int position) {


        Note note = noteList.get(position);



        holder.txtTitle.setText(note.getTitle());



        // Card Color

        holder.colorView.setBackgroundColor(
                Color.parseColor(
                        colors[position % colors.length]
                )
        );



        // Load Note Icon

        if(note.getIconUrl() != null &&
                !note.getIconUrl().isEmpty()){


            Glide.with(context)
                    .load(note.getIconUrl())
                    .placeholder(R.drawable.notes_svg)
                    .into(holder.noteIcon);


        }else{


            holder.noteIcon.setImageResource(
                    R.drawable.notes_svg
            );


        }





        holder.itemView.setOnClickListener(v -> {


            Intent intent = new Intent(
                    Intent.ACTION_VIEW
            );


            intent.setData(
                    Uri.parse(note.getPdfUrl())
            );


            context.startActivity(intent);


        });


    }



    @Override
    public int getItemCount() {

        return noteList.size();

    }



    public void updateList(List<Note> filteredList){

        noteList = filteredList;

        notifyDataSetChanged();

    }




    static class NoteViewHolder extends RecyclerView.ViewHolder{


        ImageView noteIcon;

        TextView txtTitle;

        View colorView;



        public NoteViewHolder(@NonNull View itemView) {

            super(itemView);


            noteIcon = itemView.findViewById(
                    R.id.noteIcon
            );


            txtTitle = itemView.findViewById(
                    R.id.txtTitle
            );


            colorView = itemView.findViewById(
                    R.id.colorView
            );


        }

    }


}