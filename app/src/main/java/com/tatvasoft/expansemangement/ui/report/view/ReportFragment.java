package com.tatvasoft.expansemangement.ui.report.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textview.MaterialTextView;
import com.tatvasoft.expansemangement.R;
import com.tatvasoft.expansemangement.ui.category.model.DetailsModel;
import com.tatvasoft.expansemangement.ui.category.view.DetailsAdapter;
import com.tatvasoft.expansemangement.ui.intro.model.DetailsDataBase;

import java.util.ArrayList;
import java.util.Calendar;

public class ReportFragment extends Fragment implements View.OnClickListener {
    private ArrayList<DetailsModel> details;
    private RecyclerView rvDetails;
    private DetailsModel detail;
    private ArrayList pieEntries;
    private EditText edDate;
    private ArrayList<String>  pieEntryLabels;
    private PieChart pieChart;
    private PieData pieData;
    private PieDataSet pieDataSet;
    private View rootView;
    private LinearLayoutManager linearLayoutManager;
    private int foodMoney,petrolMoney,stationaryMoney;
    private DetailsDataBase detailsDataBase;
    private Calendar calendar;
    private int mYear, mMonth, mDay;
    private DatePickerDialog datePickerDialog;
    private String fDate;
    private DetailsAdapter detailsAdapter;

    public ReportFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_report, container, false);
        initControls();
        return rootView;
    }

    private void initControls() {
        initListeners();
        getDetails();
        getDataBaseData(fDate);
    }

    private void getDataBaseData(String fDate) {
        details=detailsDataBase.selectedDate(fDate);
        if (details != null) {
            if (details.size()==0){
                detailsAdapter = new DetailsAdapter(getContext(), details);
                rvDetails.setAdapter(detailsAdapter);
                Toast.makeText(getActivity(), "No details ", Toast.LENGTH_SHORT).show();
            }else {
                getEntries();
                setDataInChart();
                setRecyclerAdapter();
            }
        } else {
            Toast.makeText(getActivity(), "No details in database ", Toast.LENGTH_SHORT).show();
        }
    }

    private void setRecyclerAdapter() {
        detailsAdapter = new DetailsAdapter(getContext(), details);
        rvDetails.setAdapter(detailsAdapter);
    }

    private void setDataInChart() {
        pieDataSet = new PieDataSet(pieEntries, "Categories");
        pieData = new PieData(pieEntryLabels,pieDataSet);
        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setSliceSpace(5f);
    }

    private void getDetails() {
//        if (getArguments() != null) {
//            details = (ArrayList<DetailsModel>) getArguments().getSerializable("details");
//            if (details.size()==0){
//                Toast.makeText(getActivity(), "No details ", Toast.LENGTH_SHORT).show();
//            }else {
//                getEntries();
//                setDataInChart();
//                setRecyclerAdapter();
//            }
//        } else {
//            Toast.makeText(getActivity(), "No details ", Toast.LENGTH_SHORT).show();
//        }
    }

    @SuppressLint({"WrongConstant", "SetTextI18n"})
    private void initListeners() {
        pieChart = rootView.findViewById(R.id.pieChart);
        rvDetails = rootView.findViewById(R.id.rvDetails);
        edDate = rootView.findViewById(R.id.edDate);
        edDate.setOnClickListener(this);
        detailsDataBase=new DetailsDataBase(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvDetails.setLayoutManager(linearLayoutManager);
        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        edDate.setText(mDay + "/"
                + (mMonth + 1) + "/" + mYear);
        fDate=String.valueOf(mDay+(mMonth + 1)+mYear);
    }

    private void getEntries() {
        pieEntries = new ArrayList<>();
        pieEntryLabels= new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            detail = details.get(i);
            {
                switch (detail.getCategory()) {
                    case "Food":
                        foodMoney = Integer.parseInt(detail.getMoneySpend()) + foodMoney;
                        pieEntryLabels.add(detail.getCategory());
                        break;
                    case "Petrol":
                        petrolMoney = Integer.parseInt(detail.getMoneySpend()) + petrolMoney;
                        pieEntryLabels.add(detail.getCategory());
                        break;
                    case "Stationary":
                        stationaryMoney = Integer.parseInt(detail.getMoneySpend()) + stationaryMoney;
                        pieEntryLabels.add(detail.getCategory());
                        break;
                }

            }
        }
        if (foodMoney>0){
            pieEntries.add(new Entry(foodMoney, 0));
        }
        if (petrolMoney>0){
            pieEntries.add(new Entry(petrolMoney, 1));
        } if (stationaryMoney>0){
            pieEntries.add(new Entry(stationaryMoney, 2));
        }
//        pieEntries.add(new Entry(2f, 2));
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
       if (id == R.id.edDate) {
            dateChange();
        }
    }
    @SuppressLint("SetTextI18n")
    private void dateChange() {
        datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    edDate.setText(dayOfMonth + "/"
                            + (monthOfYear + 1) + "/" + year);
                    fDate=String.valueOf(dayOfMonth+(monthOfYear + 1)+year);
                    getDataBaseData(fDate);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
