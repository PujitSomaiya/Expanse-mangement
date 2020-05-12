package com.tatvasoft.expansemangement.ui.category.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.tatvasoft.expansemangement.R;
import com.tatvasoft.expansemangement.ui.category.model.AddCategoryModel;
import com.tatvasoft.expansemangement.ui.intro.model.CategoryDataBase;
import com.tatvasoft.expansemangement.util.CommonUtil;

import java.util.ArrayList;

public class CategoryFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private RecyclerView rvCategories;
    private AddCategoryModel addCategoryModel;
    private CategoryAdapter categoryAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MaterialButton btnAdd;
    private EditText edCategory;
    private CategoryDataBase categoryDataBase;
    private ArrayList<AddCategoryModel> addCategoryModels = new ArrayList<>();
    private ArrayList<AddCategoryModel> categoryModels;

    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_catagory, container, false);
        initControls();
        return rootView;
    }

    private void initControls() {
        initListeners();
        setCategories();
    }

    private void setCategories() {
        categoryModels = categoryDataBase.listData();
        if (categoryModels != null) {
            categoryAdapter = new CategoryAdapter(getContext(), categoryModels);
            rvCategories.setAdapter(categoryAdapter);
        }
    }

    private void initListeners() {
        btnAdd = rootView.findViewById(R.id.btnAdd);
        edCategory = rootView.findViewById(R.id.edCategory);
        rvCategories = rootView.findViewById(R.id.rvCategories);
        categoryDataBase = new CategoryDataBase(getActivity());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvCategories.setLayoutManager(linearLayoutManager);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAdd) {
            addCategory();
        }
    }

    private void addCategory() {
        if (CommonUtil.isNotNull(edCategory) && CommonUtil.isEmptyEditText(edCategory)) {
            edCategory.setError("add category");
        } else {
//            if (categoryDataBase.selectedCategory(edCategory.getText().toString()).equals(edCategory.getText().toString())){
//                Toast.makeText(getActivity(),"Already added",Toast.LENGTH_LONG).show();
//            }else {
                addCategoryModel = new AddCategoryModel(edCategory.getText().toString());
                addCategoryModels.add(addCategoryModel);
                categoryDataBase.addDetails(addCategoryModel);
                edCategory.requestFocus();
                edCategory.setText("");
//            }
        }
        setCategories();
    }
}
