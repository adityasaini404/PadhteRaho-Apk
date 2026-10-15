package com.example.padhteraho.fragments;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padhteraho.MainActivity;
import com.example.padhteraho.R;
import com.example.padhteraho.adapters.CategoryAdapter;
import com.example.padhteraho.firebase.FirebaseHelper;
import com.example.padhteraho.listeners.OnCategoryClickListener;
import com.example.padhteraho.listeners.OnCourseClickListener;
import com.example.padhteraho.models.Category;
import com.example.padhteraho.models.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.widget.EditText;
import androidx.core.widget.NestedScrollView;

import com.facebook.shimmer.ShimmerFrameLayout;

public class HomeFragment extends Fragment
        implements OnCategoryClickListener {

    private RecyclerView recyclerCategories;

    private CategoryAdapter categoryAdapter;

    private List<Category> categoryList;

    private DatabaseReference categoryRef;

    private EditText searchBar;
    private ShimmerFrameLayout shimmerLayout;
    private NestedScrollView contentLayout;

    public HomeFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        shimmerLayout = view.findViewById(R.id.shimmerLayout);
        contentLayout = view.findViewById(R.id.contentLayout);

        contentLayout.setVisibility(View.GONE);
        shimmerLayout.startShimmer();

        searchBar = view.findViewById(R.id.searchView);
        searchBar.setOnClickListener(v -> {

            ((MainActivity) requireActivity())
                    .openFragment(new SearchFragment(), null);

        });

        // ================= UI INIT =================
        recyclerCategories = view.findViewById(R.id.recyclerCategories);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler,
                                         RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        };

        recyclerCategories.setLayoutManager(layoutManager);


        // ================= FIREBASE =================
        categoryRef = FirebaseHelper.getCategoriesRef();

        // ================= LIST INIT FIRST =================
        categoryList = new ArrayList<>();

        // ================= ADAPTER INIT =================
        categoryAdapter = new CategoryAdapter(categoryList, this);

        // ================= SET ADAPTERS =================
        recyclerCategories.setAdapter(categoryAdapter);

        // ================= LOAD DATA LAST =================
        loadCategories();

        return view;
    }



    // ================= CATEGORIES =================
    private void loadCategories() {

        categoryRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                categoryList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Category category = data.getValue(Category.class);

                    if (category != null) {
                        categoryList.add(category);
                    }
                }

                categoryAdapter.notifyDataSetChanged();

                shimmerLayout.stopShimmer();
                shimmerLayout.setVisibility(View.GONE);
                contentLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("HOME", error.getMessage());
                shimmerLayout.stopShimmer();
                shimmerLayout.setVisibility(View.GONE);
                contentLayout.setVisibility(View.VISIBLE);
            }
        });
    }


    // ================= CLICK EVENTS =================
    @Override
    public void onCategoryClick(Category category) {

        Bundle bundle = new Bundle();
        bundle.putString("category", category.getName());

        ((MainActivity) requireActivity())
                .openFragment(new CategoryCoursesFragment(), bundle);
    }

}