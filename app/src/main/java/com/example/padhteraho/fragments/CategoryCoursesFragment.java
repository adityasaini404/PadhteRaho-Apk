package com.example.padhteraho.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

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
import com.example.padhteraho.models.Playlist;

import java.util.ArrayList;
import java.util.List;

public class CategoryCoursesFragment extends Fragment
        implements OnCourseClickListener {

    private TextView txtCategoryTitle;
    private RecyclerView recyclerCourses;

    private List<Course> courseList;
    private CourseAdapter courseAdapter;

    private DatabaseReference courseRef;

    public CategoryCoursesFragment() {
        // Required Empty Constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_category_courses,
                container,
                false);

        txtCategoryTitle = view.findViewById(R.id.txtCategoryTitle);
        recyclerCourses = view.findViewById(R.id.recyclerCourses);

        recyclerCourses.setLayoutManager(
                new GridLayoutManager(getContext(), 2));

        courseList = new ArrayList<>();

        courseAdapter = new CourseAdapter(courseList, this);

        recyclerCourses.setAdapter(courseAdapter);

        // Firebase
        courseRef = FirebaseDatabase
                .getInstance()
                .getReference("courses");

        if (getArguments() != null) {

            String category = getArguments().getString("category");

            txtCategoryTitle.setText(category);

            loadCourses(category);
        }

        return view;
    }

    private void loadCourses(String category) {

        courseRef.addValueEventListener(new ValueEventListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                courseList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {

                    Course course = data.getValue(Course.class);

                    android.util.Log.d(
                            "COURSE_LOAD",
                            "Key = " + data.getKey()
                                    + " | Id = " + course.getId()
                                    + " | Title = " + course.getTitle()
                    );

                    if (course == null)
                        continue;

                    if (course.getCategory() == null)
                        continue;

                    if (course.getCategory().equalsIgnoreCase(category)) {

                        courseList.add(course);

                    }

                }

                courseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    @Override
    public void onCourseClick(Course course) {

        Bundle bundle = new Bundle();
        bundle.putString("courseId", course.getId());
        bundle.putString("courseTitle", course.getTitle());

        CourseChannelsFragment fragment = new CourseChannelsFragment();

        android.util.Log.d(
                "COURSE_CLICK",
                "Clicked Id = " + course.getId()
                        + " | Title = " + course.getTitle()
        );

        ((MainActivity) requireActivity())
                .openFragment(fragment, bundle);
    }
}