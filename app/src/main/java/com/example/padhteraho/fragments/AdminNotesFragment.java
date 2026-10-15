package com.example.padhteraho.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padhteraho.R;
import com.example.padhteraho.adapters.AdminNoteAdapter;
import com.example.padhteraho.models.Note;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AdminNotesFragment extends Fragment {


    private TextInputEditText etIconUrl, etNoteTitle, etPdfUrl;

    private MaterialButton btnAddNote;

    private RecyclerView recyclerView;


    private DatabaseReference notesRef;


    private ArrayList<Note> noteList;


    private AdminNoteAdapter adapter;



    public AdminNotesFragment() {

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.fragment_admin_notes,
                container,
                false
        );


        etIconUrl = view.findViewById(R.id.etIconUrl);
        etNoteTitle = view.findViewById(R.id.etNoteTitle);
        etPdfUrl = view.findViewById(R.id.etPdfUrl);


        btnAddNote = view.findViewById(R.id.btnAddNote);


        recyclerView = view.findViewById(R.id.adminNotesRecycler);


        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );


        noteList = new ArrayList<>();


        adapter = new AdminNoteAdapter(
                requireContext(),
                noteList
        );


        recyclerView.setAdapter(adapter);



        notesRef = FirebaseDatabase.getInstance()
                .getReference("notes");



        btnAddNote.setOnClickListener(v -> addNote());



        loadNotes();



        return view;

    }




    private void addNote(){


        String title =
                etNoteTitle.getText()
                        .toString()
                        .trim();


        String pdfUrl =
                etPdfUrl.getText()
                        .toString()
                        .trim();


        String iconUrl =
                etIconUrl.getText()
                        .toString()
                        .trim();



        if(TextUtils.isEmpty(title)){

            etNoteTitle.setError("Enter note name");
            return;

        }



        if(TextUtils.isEmpty(pdfUrl)){

            etPdfUrl.setError("Enter PDF URL");
            return;

        }



        String id =
                notesRef.push().getKey();



        Note note = new Note(
                title,
                pdfUrl,
                iconUrl
        );



        if(id != null){

            notesRef.child(id)
                    .setValue(note)
                    .addOnSuccessListener(unused -> {


                        Toast.makeText(
                                getContext(),
                                "Note Added",
                                Toast.LENGTH_SHORT
                        ).show();



                        etNoteTitle.setText("");
                        etPdfUrl.setText("");
                        etIconUrl.setText("");

                    });


        }


    }





    private void loadNotes(){


        notesRef.addValueEventListener(
                new com.google.firebase.database.ValueEventListener() {


                    @Override
                    public void onDataChange(
                            @NonNull com.google.firebase.database.DataSnapshot snapshot) {


                        noteList.clear();



                        for(com.google.firebase.database.DataSnapshot data :
                                snapshot.getChildren()){


                            Note note =
                                    data.getValue(Note.class);



                            if(note != null){

                                note.setId(data.getKey());

                                noteList.add(note);

                            }


                        }



                        adapter.notifyDataSetChanged();



                    }



                    @Override
                    public void onCancelled(
                            @NonNull com.google.firebase.database.DatabaseError error) {


                    }

                }
        );

    }

}