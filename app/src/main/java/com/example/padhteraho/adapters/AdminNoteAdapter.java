package com.example.padhteraho.adapters;


import android.content.Context;
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
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;



public class AdminNoteAdapter extends RecyclerView.Adapter<AdminNoteAdapter.ViewHolder> {


    private Context context;
    private ArrayList<Note> noteList;



    public AdminNoteAdapter(Context context, ArrayList<Note> noteList){

        this.context = context;
        this.noteList = noteList;

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {


        View view = LayoutInflater.from(context)
                .inflate(
                        R.layout.item_admin_note,
                        parent,
                        false
                );


        return new ViewHolder(view);

    }





    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {


        Note note = noteList.get(position);



        holder.title.setText(note.getTitle());



        // Load Icon

        if(note.getIconUrl()!=null &&
                !note.getIconUrl().isEmpty()){


            Glide.with(context)
                    .load(note.getIconUrl())
                    .placeholder(R.drawable.notes_svg)
                    .into(holder.icon);


        }else{

            holder.icon.setImageResource(R.drawable.notes_svg);

        }




        holder.delete.setOnClickListener(v -> {


            if(note.getId()!=null){


                FirebaseDatabase.getInstance()
                        .getReference("notes")
                        .child(note.getId())
                        .removeValue();



            }


        });



    }





    @Override
    public int getItemCount(){

        return noteList.size();

    }





    public static class ViewHolder extends RecyclerView.ViewHolder{


        ImageView icon;

        TextView title, delete;



        public ViewHolder(@NonNull View itemView) {

            super(itemView);


            icon = itemView.findViewById(R.id.noteIcon);

            title = itemView.findViewById(R.id.noteTitle);

            delete = itemView.findViewById(R.id.deleteNote);


        }

    }


}