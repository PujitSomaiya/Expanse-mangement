package com.tatvasoft.expansemangement.ui.category.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvasoft.expansemangement.R;
import com.tatvasoft.expansemangement.ui.category.model.AddCategoryModel;
import com.tatvasoft.expansemangement.ui.intro.model.CategoryDataBase;


import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.DetailsViewHolder> {

    private final Context context;
    private final ArrayList<AddCategoryModel> dataList;
    private AddCategoryModel details;
    private CategoryDataBase categoryDataBase;


    public CategoryAdapter(Context context, ArrayList<AddCategoryModel> dataList) {
        this.context = context;
        this.dataList = dataList;
        categoryDataBase=new CategoryDataBase(context);
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_categories, parent, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailsViewHolder holder, final int position) {
        details = dataList.get(position);
        {
            holder.tvCategory.setText(details.getCategory());
            holder.imgDelete.setOnClickListener(view -> {
                int positionOfData = position;
                AddCategoryModel data = dataList.get(positionOfData);
                String category=data.category;
                deleteBlog(category,positionOfData);
            });

        }
    }
    private void deleteBlog(String positionOfData,int position) {
        categoryDataBase.deleteCategory(positionOfData);
        dataList.remove(position);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCategory;
        private ImageView imgDelete;

        public DetailsViewHolder(View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

            }
        }
    }
}
