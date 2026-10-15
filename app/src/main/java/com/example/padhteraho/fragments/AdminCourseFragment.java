package com.example.padhteraho.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.padhteraho.R;
import com.example.padhteraho.models.Category;
import com.example.padhteraho.models.Course;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.text.TextUtils;

public class AdminCourseFragment extends Fragment {

    private TextInputEditText edtCourseTitle;
    private TextInputEditText edtTeacher;
    private TextInputEditText edtPlaylistId;
    private TextInputEditText edtDuration;

    private Spinner spinnerCategory;

    private CheckBox checkTrending;
    private CheckBox checkRecommended;

    private MaterialButton btnSaveCourse;

    private ProgressBar progressBar;

    private DatabaseReference categoryRef;
    private DatabaseReference courseRef;

    private ArrayList<String> categoryList;
    private ArrayAdapter<String> categoryAdapter;

    private TextInputEditText edtDescription;
    private TextInputEditText edtThumbnail;

    public AdminCourseFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_admin_course,
                container,
                false);

        // Initialize Views
        edtCourseTitle = view.findViewById(R.id.edtCourseTitle);
        edtTeacher = view.findViewById(R.id.edtTeacher);
        edtPlaylistId = view.findViewById(R.id.edtPlaylistId);
        edtDuration = view.findViewById(R.id.edtDuration);

        spinnerCategory = view.findViewById(R.id.spinnerCategory);

        checkTrending = view.findViewById(R.id.checkTrending);
        checkRecommended = view.findViewById(R.id.checkRecommended);

        btnSaveCourse = view.findViewById(R.id.btnSaveCourse);

        progressBar = view.findViewById(R.id.progressBar);

        edtDescription = view.findViewById(R.id.edtDescription);
        edtThumbnail = view.findViewById(R.id.edtThumbnail);

        // Firebase
        categoryRef = FirebaseDatabase
                .getInstance()
                .getReference("categories");

        courseRef = FirebaseDatabase
                .getInstance()
                .getReference("courses");

        // Spinner
        categoryList = new ArrayList<>();

        categoryAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                categoryList
        );

        spinnerCategory.setAdapter(categoryAdapter);

        // Load Categories
        loadCategories();

        btnSaveCourse.setOnClickListener(v -> saveCourse());

        return view;
    }

    private void loadCategories() {

        categoryRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                categoryList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {

                    Category category = data.getValue(Category.class);

                    if (category != null) {

                        categoryList.add(category.getName());

                    }

                }

                categoryAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(
                        getContext(),
                        error.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

    }
    private String getPlaylistId(String link) {

        if (link == null || link.trim().isEmpty()) {
            return "";
        }

        if (link.contains("list=")) {
            return link.substring(link.indexOf("list=") + 5);
        }

        return link.trim();
    }

    private boolean validate() {

        if (TextUtils.isEmpty(edtCourseTitle.getText().toString().trim())) {
            edtCourseTitle.setError("Enter Course Title");
            edtCourseTitle.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtTeacher.getText().toString().trim())) {
            edtTeacher.setError("Enter Teacher Name");
            edtTeacher.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtPlaylistId.getText().toString().trim())) {
            edtPlaylistId.setError("Enter Playlist Link");
            edtPlaylistId.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtDuration.getText().toString().trim())) {
            edtDuration.setError("Enter Duration");
            edtDuration.requestFocus();
            return false;
        }

        return true;
    }

    private void saveCourse() {

        if (!validate()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        String id = courseRef.push().getKey();

        String title = edtCourseTitle.getText().toString().trim();
        String teacher = edtTeacher.getText().toString().trim();
        String playlistId = getPlaylistId(edtPlaylistId.getText().toString().trim());
        String duration = edtDuration.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String description = edtDescription.getText().toString().trim();
        String thumbnail = edtThumbnail.getText().toString().trim();

        boolean trending = checkTrending.isChecked();
        boolean recommended = checkRecommended.isChecked();

        Course course = new Course(
                id,
                title,
                teacher,
                playlistId,
                category,
                duration
        );

        course.setTrending(trending);
        course.setRecommended(recommended);

        course.setDescription(description);
        course.setThumbnail(thumbnail);

        courseRef.child(id).setValue(course)
                .addOnCompleteListener(task -> {

                    progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()) {

                        Toast.makeText(getContext(),
                                "Course Added Successfully 🚀",
                                Toast.LENGTH_SHORT).show();

                        clearForm();

                    } else {

                        Toast.makeText(getContext(),
                                "Failed to add course",
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void clearForm() {

        edtCourseTitle.setText("");
        edtTeacher.setText("");
        edtPlaylistId.setText("");
        edtDuration.setText("");
        edtDescription.setText("");
        edtThumbnail.setText("");

        checkTrending.setChecked(false);
        checkRecommended.setChecked(false);

        spinnerCategory.setSelection(0);
    }
}