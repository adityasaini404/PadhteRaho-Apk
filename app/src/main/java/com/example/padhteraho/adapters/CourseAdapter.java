package com.example.padhteraho.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padhteraho.R;
import com.example.padhteraho.listeners.OnCourseClickListener;
import com.example.padhteraho.models.Course;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>
        implements Filterable {

    private final List<Course> courseList;
    private final List<Course> courseListFull;
    private final OnCourseClickListener listener;

    public CourseAdapter(List<Course> courseList,
                         OnCourseClickListener listener) {

        this.courseList = courseList;
        this.listener = listener;

        courseListFull = new ArrayList<>(courseList);
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);

        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {

        Course course = courseList.get(position);

        holder.txtTitle.setText(course.getTitle());

        holder.txtTeacher.setText(course.getTeacher());

        holder.txtDuration.setText(
                course.getDuration() == null
                        ? "--:--"
                        : course.getDuration()
        );

        holder.txtCategory.setText(course.getCategory());

        switch (course.getTitle()) {

            case "Java":
                holder.imgThumbnail.setImageResource(R.drawable.padhteraho);
                break;

            case "Python":
                holder.imgThumbnail.setImageResource(R.drawable.padhteraho);
                break;

            case "C":
                holder.imgThumbnail.setImageResource(R.drawable.padhteraho);
                break;

            case "C++":
                holder.imgThumbnail.setImageResource(R.drawable.padhteraho);
                break;

            case "HTML":
                holder.imgThumbnail.setImageResource(R.drawable.padhteraho);
                break;

            case "CSS":
                holder.imgThumbnail.setImageResource(R.drawable.padhteraho);
                break;

            case "JavaScript":
                holder.imgThumbnail.setImageResource(R.drawable.padhteraho);
                break;

            case "Android":
                holder.imgThumbnail.setImageResource(R.drawable.padhteraho);
                break;

            case "DSA":
                holder.imgThumbnail.setImageResource(R.drawable.padhteraho);
                break;

            case "AI":
                holder.imgThumbnail.setImageResource(R.drawable.padhteraho);
                break;

            default:
                holder.imgThumbnail.setImageResource(R.drawable.padhteraho);
                break;
        }

        holder.itemView.setOnClickListener(v -> {

            v.animate()
                    .scaleX(0.97f)
                    .scaleY(0.97f)
                    .setDuration(70)
                    .withEndAction(() ->

                            v.animate()
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(70)
                                    .withEndAction(() -> {

                                        if (listener != null) {
                                            listener.onCourseClick(course);
                                        }

                                    }));

        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void updateData(List<Course> newList) {

        courseList.clear();
        courseList.addAll(newList);

        courseListFull.clear();
        courseListFull.addAll(newList);

        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return courseFilter;
    }

    private final Filter courseFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Course> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                filteredList.addAll(courseListFull);

            } else {

                String pattern = constraint.toString()
                        .toLowerCase()
                        .trim();

                for (Course course : courseListFull) {

                    String title = course.getTitle() == null ? "" : course.getTitle().toLowerCase();
                    String teacher = course.getTeacher() == null ? "" : course.getTeacher().toLowerCase();
                    String category = course.getCategory() == null ? "" : course.getCategory().toLowerCase();

                    if (title.contains(pattern)
                            || teacher.contains(pattern)
                            || category.contains(pattern)) {

                        filteredList.add(course);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            courseList.clear();

            if (results.values != null) {
                courseList.addAll((List<Course>) results.values);
            }

            notifyDataSetChanged();
        }
    };

    static class CourseViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardCourse;

        ImageView imgThumbnail;
        TextView txtCategory;
        TextView txtTitle;
        TextView txtTeacher;
        TextView txtDuration;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            cardCourse = itemView.findViewById(R.id.cardCourse);

            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);

            txtCategory = itemView.findViewById(R.id.txtCategory);

            txtTitle = itemView.findViewById(R.id.txtTitle);

            txtTeacher = itemView.findViewById(R.id.txtTeacher);

            txtDuration = itemView.findViewById(R.id.txtDuration);
        }
    }
}