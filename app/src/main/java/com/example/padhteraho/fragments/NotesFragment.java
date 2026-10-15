package com.example.padhteraho.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padhteraho.R;
import com.example.padhteraho.adapters.NoteAdapter;
import com.example.padhteraho.models.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotesFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;

    private NoteAdapter adapter;

    private final List<Note> noteList = new ArrayList<>();
    private final List<Note> filteredList = new ArrayList<>();

    private DatabaseReference notesRef;

    public NotesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        recyclerView = view.findViewById(R.id.recyclerNotes);
        searchView = view.findViewById(R.id.searchView);

        // RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new NoteAdapter(requireContext(), filteredList);
        recyclerView.setAdapter(adapter);

        // SearchView Setup
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        searchView.clearFocus();

        // Remove default underline
        View searchPlate = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        if (searchPlate != null) {
            searchPlate.setBackground(null);
        }


        @SuppressLint("RestrictedApi")
        SearchView.SearchAutoComplete searchText =
                searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        searchText.setBackground(null);
        searchText.setHint("Search Notes...");
        searchText.setTextSize(15);

        // Click anywhere on SearchView
        searchView.setOnClickListener(v -> {
            searchView.setIconified(false);
            searchText.requestFocus();
        });

        // Firebase
        notesRef = FirebaseDatabase.getInstance()
                .getReference("notes");

        loadNotes();
        setupSearch();

        return view;
    }

    private void loadNotes() {

        notesRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                noteList.clear();
                filteredList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {

                    Note note = ds.getValue(Note.class);

                    if (note != null) {
                        noteList.add(note);
                    }
                }

                filteredList.addAll(noteList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupSearch() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {

        filteredList.clear();

        if (text == null || text.trim().isEmpty()) {

            filteredList.addAll(noteList);

        } else {

            for (Note note : noteList) {

                if (note.getTitle() != null &&
                        note.getTitle().toLowerCase(Locale.ROOT)
                                .contains(text.toLowerCase(Locale.ROOT))) {

                    filteredList.add(note);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}