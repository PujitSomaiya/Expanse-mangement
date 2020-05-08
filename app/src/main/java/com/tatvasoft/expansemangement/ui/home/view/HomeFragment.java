package com.tatvasoft.expansemangement.ui.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import com.google.android.material.button.MaterialButton;
import com.tatvasoft.expansemangement.R;

public class HomeFragment extends Fragment {
    private EditText edIncome, edRemark;
    private Spinner spnCategory;
    private MaterialButton btnReport;
    private View rootView;
    private int income;
    private Toolbar toolbar;

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
    }

    private void getIncome() {
        if (getArguments() != null) {
            income = Integer.valueOf(getArguments().getString("income"));
        } else {
            Toast.makeText(getActivity(), "No initialize income", Toast.LENGTH_SHORT).show();
        }
    }

    private void initListeners() {
        edIncome = rootView.findViewById(R.id.edIncome);
        edRemark = rootView.findViewById(R.id.edRemark);
        spnCategory = rootView.findViewById(R.id.spnCategory);
        btnReport = rootView.findViewById(R.id.btnReport);
        toolbar = rootView.findViewById(R.id.toolbar);
    }

    private void spinner() {
        String[] items = new String[]{};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);
    }
}
