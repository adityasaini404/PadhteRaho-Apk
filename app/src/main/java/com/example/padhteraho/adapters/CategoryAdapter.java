package com.example.padhteraho.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padhteraho.R;
import com.example.padhteraho.listeners.OnCategoryClickListener;
import com.example.padhteraho.models.Category;
import com.google.android.material.card.MaterialCardView;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {


    private final List<Category> categoryList;
    private final OnCategoryClickListener listener;

    private final int[] lightColors = {

            Color.parseColor("#E8F0FF"), // Blue
            Color.parseColor("#DCFCE7"), // Green
            Color.parseColor("#F3E8FF"), // Purple
            Color.parseColor("#FEF3C7"), // Orange
            Color.parseColor("#FCE7F3"), // Pink
            Color.parseColor("#E0F2FE"), // Sky
            Color.parseColor("#ECFCCB"), // Lime
            Color.parseColor("#FEE2E2"), // Red
            Color.parseColor("#EDE9FE"), // Indigo
            Color.parseColor("#CCFBF1"), // Teal
            Color.parseColor("#FAF5FF"), // Violet
            Color.parseColor("#FFF7ED"), // Amber
            Color.parseColor("#F0FDF4"), // Emerald
            Color.parseColor("#F5F3FF"), // Deep Purple
            Color.parseColor("#FDF2F8")  // Rose

    };

    private final int[] darkColors = {

            Color.parseColor("#2563EB"),
            Color.parseColor("#10B981"),
            Color.parseColor("#9333EA"),
            Color.parseColor("#F59E0B"),
            Color.parseColor("#DB2777"),
            Color.parseColor("#0284C7"),
            Color.parseColor("#65A30D"),
            Color.parseColor("#DC2626"),
            Color.parseColor("#4F46E5"),
            Color.parseColor("#0F766E"),
            Color.parseColor("#7C3AED"),
            Color.parseColor("#EA580C"),
            Color.parseColor("#16A34A"),
            Color.parseColor("#6D28D9"),
            Color.parseColor("#E11D48")

    };


    public CategoryAdapter(List<Category> categoryList,
                           OnCategoryClickListener listener) {

        this.categoryList = categoryList;
        this.listener = listener;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Category category = categoryList.get(position);


        holder.txtCategory.setText(category.getName());

        holder.txtCount.setText("Learn Now");



        int index = position % lightColors.length;

        holder.iconContainer.setCardBackgroundColor(
                lightColors[index]);

        holder.cardCategory.setCardBackgroundColor(
                Color.WHITE);

        holder.imgCategory.setColorFilter(
                darkColors[index]);

        holder.categoryLine.setBackgroundColor(
                darkColors[index]);

        switch (category.getName()) {

            case "Programming":
                holder.imgCategory.setImageResource(R.drawable.programming);
                break;

            case "Web Development":
                holder.imgCategory.setImageResource(R.drawable.webdevelopment);
                break;

            case "Android":
                holder.imgCategory.setImageResource(R.drawable.android);
                break;

            case "DSA":
                holder.imgCategory.setImageResource(R.drawable.dsa);
                break;

            case "Ai":
                holder.imgCategory.setImageResource(R.drawable.ai);
                break;

            case "Data Analytics":
                holder.imgCategory.setImageResource(R.drawable.analytics);
                break;

            case "English Speaking":
                holder.imgCategory.setImageResource(android.R.drawable.ic_btn_speak_now);
                break;

            case "Interview":
                holder.imgCategory.setImageResource(R.drawable.interview);
                break;

            case "Video Editing":
                holder.imgCategory.setImageResource(R.drawable.video_editing);
                break;

            case "Git & Github":
                holder.imgCategory.setImageResource(R.drawable.github);
                break;

            case "Databases":
                holder.imgCategory.setImageResource(R.drawable.database);
                break;

            case "UI/UX Design":
                holder.imgCategory.setImageResource(R.drawable.ui_ux);
                break;

            case "DevOps":
                holder.imgCategory.setImageResource(R.drawable.devops);
                break;

            case "Software Testing":
                holder.imgCategory.setImageResource(R.drawable.testing);
                break;

            default:
                holder.imgCategory.setImageResource(R.drawable.course_svg);
                break;
        }



        holder.itemView.setOnClickListener(v -> {


            v.animate()
                    .scaleX(0.96f)
                    .scaleY(0.96f)
                    .setDuration(80)
                    .withEndAction(() -> {


                        v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(80);


                        if(listener != null){

                            listener.onCategoryClick(category);

                        }

                    });


        });


    }



    @Override
    public int getItemCount() {

        return categoryList.size();

    }





    static class ViewHolder extends RecyclerView.ViewHolder {


        MaterialCardView iconContainer;
        MaterialCardView cardCategory;


        ImageView imgCategory;

        TextView txtCategory;

        TextView txtCount;

        View categoryLine;



        public ViewHolder(@NonNull View itemView) {

            super(itemView);


            iconContainer =
                    itemView.findViewById(R.id.iconContainer);


            imgCategory =
                    itemView.findViewById(R.id.imgCategory);


            txtCategory =
                    itemView.findViewById(R.id.txtCategory);


            txtCount =
                    itemView.findViewById(R.id.txtCount);


            categoryLine =
                    itemView.findViewById(R.id.categoryLine);

            cardCategory =
                    itemView.findViewById(R.id.cardCategory);

        }

    }

}