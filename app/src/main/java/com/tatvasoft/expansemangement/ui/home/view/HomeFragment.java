package com.tatvasoft.expansemangement.ui.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.button.MaterialButton;
import com.tatvasoft.expansemangement.R;
import com.tatvasoft.expansemangement.ui.category.model.DetailsModel;
import com.tatvasoft.expansemangement.ui.category.view.DetailsAdapter;
import com.tatvasoft.expansemangement.ui.intro.model.DetailsDataBase;
import com.tatvasoft.expansemangement.ui.intro.view.MainActivity;
import com.tatvasoft.expansemangement.util.CommonUtil;

import java.util.ArrayList;

import static com.tatvasoft.expansemangement.ui.intro.view.MainActivity.MyPREFERENCES;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private EditText edSpend, edRemark, edIncome;
    private Spinner spnCategory;
    private MaterialButton btnAdd, btnChangIncome, btnReport;
    private View rootView;
    private int income, spendMoney = 0;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;
    private String[] category;
    private RecyclerView rvDetails;
    private ArrayList<DetailsModel> detailsModels = new ArrayList<>();
    private DetailsModel detailModel;
    private DetailsDataBase detailsDataBase;
    private SharedPreferences incomePreference;
    private DetailsAdapter detailsAdapter;
    private static final String MyPREFERENCES = "incomePref";
    private static final String Income = "incomeKey";

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

    @SuppressLint("ResourceAsColor")
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
        edIncome = rootView.findViewById(R.id.edIncome);
        spnCategory = rootView.findViewById(R.id.spnCategory);
        btnAdd = rootView.findViewById(R.id.btnAdd);
        btnReport = rootView.findViewById(R.id.btnReport);
        btnChangIncome = rootView.findViewById(R.id.btnChangIncome);
        toolbar = rootView.findViewById(R.id.toolbar);
//        toolbar.inflateMenu(R.menu.menu_add_category);
        rvDetails = rootView.findViewById(R.id.rvDetails);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvDetails.setLayoutManager(linearLayoutManager);
        btnAdd.setOnClickListener(this);
        btnReport.setOnClickListener(this);
        btnChangIncome.setOnClickListener(this);
        detailsDataBase = new DetailsDataBase(getActivity());
        incomePreference = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

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
        } else if (id == R.id.btnChangIncome) {
            changeIncome();
        }
    }

    private void changeIncome() {
        if (CommonUtil.isEmptyEditText(edIncome) && CommonUtil.isNotNull(edIncome)) {
            edIncome.setError("Add income");
        } else {
            SharedPreferences.Editor incomeEditor = incomePreference.edit();
            incomeEditor.putString(Income, edIncome.getText().toString());
            incomeEditor.apply();
            income = Integer.parseInt(edIncome.getText().toString());
            spendMoney = income;
            detailsModels.clear();
            detailsAdapter = new DetailsAdapter(getContext(), detailsModels);
            rvDetails.setAdapter(detailsAdapter);
            Toast.makeText(getActivity(), "Income changed,Your current income is :"+income, Toast.LENGTH_LONG).show();
            edIncome.setText("");
            edSpend.requestFocus();
        }
    }

    private void showReport() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("details", detailsModels);
        if (detailsModels != null) {
            ((MainActivity) getActivity()).selectItem(2, null);
            CommonUtil.hideKeyboard(getActivity());
        } else {
            Toast.makeText(getActivity(), "Add details", Toast.LENGTH_LONG).show();
        }
    }

    private void setDetails() {
        detailsDataBase.addDetails(detailModel);
    }

    private void addOnList() {
        if (CommonUtil.isNotNull(edRemark) && CommonUtil.isEmptyEditText(edRemark)) {
            edRemark.setError("Add remark");
        } else if (CommonUtil.isEmptyEditText(edSpend) && CommonUtil.isNotNull(edSpend)) {
            edSpend.setError("Add amount");
        } else {
            if (Integer.parseInt(edSpend.getText().toString()) <= income) {
                income = income - Integer.parseInt(edSpend.getText().toString());
                if (spendMoney >= income) {
                    detailModel = new DetailsModel(edSpend.getText().toString(), edRemark.getText().toString(), spnCategory.getSelectedItem().toString(), null);
                    detailsModels.add(detailModel);
                    setDetails();
                    edSpend.requestFocus();
                    edRemark.setText("");
                    edSpend.setText("");
                    detailsAdapter = new DetailsAdapter(getContext(), detailsModels);
                    rvDetails.setAdapter(detailsAdapter);
                }
            } else {
                Toast.makeText(getActivity(), "Enough money spend,Remaining income is :" + income, Toast.LENGTH_LONG).show();
            }
        }


    }

    private void spinnerString() {
        if (category.length < 0) {
            Toast.makeText(getActivity(), "Add categories", Toast.LENGTH_SHORT).show();
        }
    }
}
