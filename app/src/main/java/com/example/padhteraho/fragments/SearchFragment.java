package com.example.padhteraho.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padhteraho.MainActivity;
import com.example.padhteraho.R;
import com.example.padhteraho.adapters.CourseAdapter;
import com.example.padhteraho.listeners.OnCourseClickListener;
import com.example.padhteraho.models.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment
        implements OnCourseClickListener {

    private EditText searchView;
    private RecyclerView recyclerSearch;

    private CourseAdapter adapter;
    private List<Course> courseList;

    private DatabaseReference courseRef;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_search,
                container,
                false);

        searchView = view.findViewById(R.id.searchView);
        recyclerSearch = view.findViewById(R.id.recyclerSearch);

        recyclerSearch.setLayoutManager(
                new GridLayoutManager(getContext(), 2)
        );

        courseList = new ArrayList<>();

        adapter = new CourseAdapter(courseList, this);

        recyclerSearch.setAdapter(adapter);

        courseRef = FirebaseDatabase.getInstance().getReference("courses");

        loadCourses();

        searchView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start,
                                          int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s,
                                      int start,
                                      int before,
                                      int count) {

                adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        searchView.post(() -> {

            searchView.requestFocus();

            InputMethodManager imm =
                    (InputMethodManager) requireContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
            }

        });

        return view;
    }

    private void loadCourses() {

        courseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                List<Course> tempList = new ArrayList<>();


                for (DataSnapshot data : snapshot.getChildren()) {


                    Course course = data.getValue(Course.class);


                    if(course != null){

                        tempList.add(course);


                        android.util.Log.d(
                                "SEARCH",
                                "Course = " + course.getTitle()
                        );

                    }
                }



                android.util.Log.d(
                        "SEARCH",
                        "Total Courses Loaded = " + tempList.size()
                );


                adapter.updateData(tempList);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                android.util.Log.e(
                        "SEARCH",
                        error.getMessage()
                );

            }

        });

    }

    @Override
    public void onCourseClick(Course course) {

        Bundle bundle = new Bundle();

        bundle.putString("courseId", course.getId());
        bundle.putString("courseTitle", course.getTitle());

        ((MainActivity) requireActivity())
                .openFragment(new CourseChannelsFragment(), bundle);

    }
}