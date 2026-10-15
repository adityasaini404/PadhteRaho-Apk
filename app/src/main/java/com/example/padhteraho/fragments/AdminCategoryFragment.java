package com.example.padhteraho.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.padhteraho.R;
import com.example.padhteraho.firebase.FirebaseHelper;
import com.example.padhteraho.models.Category;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AdminCategoryFragment extends Fragment {

    private TextInputEditText edtCategoryName;
    private MaterialButton btnSaveCategory;

    private DatabaseReference categoryRef;

    public AdminCategoryFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_admin_category,
                container,
                false);

        edtCategoryName = view.findViewById(R.id.edtCategoryName);
        btnSaveCategory = view.findViewById(R.id.btnSaveCategory);

        categoryRef = FirebaseHelper.getCategoriesRef();

        btnSaveCategory.setOnClickListener(v -> saveCategory());

        return view;
    }

    private void saveCategory() {

        String categoryName = edtCategoryName.getText().toString().trim();

        if (TextUtils.isEmpty(categoryName)) {

            edtCategoryName.setError("Enter Category Name");
            edtCategoryName.requestFocus();
            return;
        }

        btnSaveCategory.setEnabled(false);

        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Category category = dataSnapshot.getValue(Category.class);

                    if (category != null &&
                            category.getName().equalsIgnoreCase(categoryName)) {

                        btnSaveCategory.setEnabled(true);

                        Toast.makeText(getContext(),
                                "Category Already Exists",
                                Toast.LENGTH_SHORT).show();

                        return;
                    }
                }

                String id = categoryRef.push().getKey();

                Category category = new Category(
                        id,
                        categoryName,
                        ""
                );

                categoryRef.child(id)
                        .setValue(category);

                categoryRef.child(id)
                        .setValue(category)
                        .addOnSuccessListener(unused -> {

                            btnSaveCategory.setEnabled(true);

                            edtCategoryName.setText("");

                            Toast.makeText(getContext(),
                                    "Category Added Successfully",
                                    Toast.LENGTH_SHORT).show();

                        })
                        .addOnFailureListener(e -> {

                            btnSaveCategory.setEnabled(true);

                            Toast.makeText(getContext(),
                                    e.getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                btnSaveCategory.setEnabled(true);

                Toast.makeText(getContext(),
                        error.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }

        });

    }

}