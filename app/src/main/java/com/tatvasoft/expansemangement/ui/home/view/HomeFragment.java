package com.tatvasoft.expansemangement.ui.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.button.MaterialButton;
import com.tatvasoft.expansemangement.R;
import com.tatvasoft.expansemangement.ui.category.model.DataModel;
import com.tatvasoft.expansemangement.ui.category.view.DetailsAdapter;
import com.tatvasoft.expansemangement.ui.intro.view.MainActivity;
import com.tatvasoft.expansemangement.util.CommonUtil;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private EditText edSpend, edRemark;
    private Spinner spnCategory;
    private MaterialButton btnAdd, btnShowList, btnReport;
    private View rootView;
    private int income, spendMoney = 0;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;
    private String[] category;
    private RecyclerView rvDetails;
    private ArrayList<DataModel> detailsModels = new ArrayList<>();
    private DataModel detailModel;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initControls();
        return rootView;
    }

    private void initControls() {

        initListeners();
        getIncome();
//        toolbar();
        spinner();
    }

    private void toolbar() {
        toolbar.getMenu().findItem(R.id.AddCategory).setVisible(true);
        toolbar.setBackgroundColor(R.color.colorAccent);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.AddCategory) {
                    ((MainActivity) getActivity()).selectItem(1, null);
                }
                return false;
            }
        });
    }

    private void getIncome() {
        if (getArguments() != null) {
            income = Integer.valueOf(getArguments().getString("income"));
            spendMoney = income;
        } else {
            Toast.makeText(getActivity(), "No initialize income", Toast.LENGTH_SHORT).show();
        }
    }

    private void initListeners() {
        edSpend = rootView.findViewById(R.id.edSpend);
        edRemark = rootView.findViewById(R.id.edRemark);
        spnCategory = rootView.findViewById(R.id.spnCategory);
        btnAdd = rootView.findViewById(R.id.btnAdd);
        btnReport = rootView.findViewById(R.id.btnReport);
        toolbar = rootView.findViewById(R.id.toolbar);
//        toolbar.inflateMenu(R.menu.menu_add_category);
        rvDetails = rootView.findViewById(R.id.rvDetails);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvDetails.setLayoutManager(linearLayoutManager);
        btnAdd.setOnClickListener(this);
        btnReport.setOnClickListener(this);
    }

    private void spinner() {
        category = new String[]{"Food", "Petrol", "Stationary"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, category);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.spnCategory) {
            spinnerString();
        } else if (id == R.id.btnAdd) {
            addOnList();
        } else if (id == R.id.btnReport) {
            showReport();
        }
    }

    private void showReport() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("details", detailsModels);
        if (bundle != null) {
            ((MainActivity) getActivity()).selectItem(2, bundle);
            CommonUtil.hideKeyboard(getActivity());
        } else {
            Toast.makeText(getActivity(), "Add details", Toast.LENGTH_LONG).show();
        }
    }

    private void addOnList() {
        if (CommonUtil.isNotNull(edRemark)&&CommonUtil.isEmptyEditText(edRemark)){
            edRemark.setError("Add remark");
        }else if (CommonUtil.isEmptyEditText(edSpend)&&CommonUtil.isNotNull(edSpend)){
            edSpend.setError("Add amount");
        }else {
            if (Integer.valueOf(edSpend.getText().toString()) <= income) {
                income = income - Integer.valueOf(edSpend.getText().toString());
                if (spendMoney >= income) {
                    detailModel = new DataModel(edSpend.getText().toString(), edRemark.getText().toString(), spnCategory.getSelectedItem().toString());
                    detailsModels.add(detailModel);
                    edSpend.requestFocus();
                    edRemark.setText("");
                    edSpend.setText("");
                    DetailsAdapter transactionAdapter = new DetailsAdapter(getContext(), detailsModels);
                    rvDetails.setAdapter(transactionAdapter);
                }
            } else {
                Toast.makeText(getActivity(), "Enough money spend", Toast.LENGTH_LONG).show();
            }
        }



    }

    private void spinnerString() {
        if (category.length < 0) {
            Toast.makeText(getActivity(), "Add categories", Toast.LENGTH_SHORT).show();
        }
    }
}
