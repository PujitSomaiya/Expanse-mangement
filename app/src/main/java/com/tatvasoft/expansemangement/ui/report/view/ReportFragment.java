package com.tatvasoft.expansemangement.ui.report.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tatvasoft.expansemangement.R;
import com.tatvasoft.expansemangement.ui.category.model.DataModel;
import com.tatvasoft.expansemangement.ui.category.view.DetailsAdapter;

import java.util.ArrayList;

public class ReportFragment extends Fragment {
    private ArrayList<DataModel> details;
    private RecyclerView rvDetails;
    private DataModel detail;
    private ArrayList pieEntries;
    private ArrayList<String>  pieEntryLabels;
    private PieChart pieChart;
    private PieData pieData;
    private PieDataSet pieDataSet;
    private View rootView;
    private LinearLayoutManager linearLayoutManager;
    private int foodMoney,petrolMoney,stationaryMoney;

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

    }

    private void setRecyclerAdapter() {
        DetailsAdapter transactionAdapter = new DetailsAdapter(getContext(), details);
        rvDetails.setAdapter(transactionAdapter);
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
        if (getArguments() != null) {
            details = (ArrayList<DataModel>) getArguments().getSerializable("details");
            if (details.size()==0){
                Toast.makeText(getActivity(), "No details ", Toast.LENGTH_SHORT).show();
            }else {
                getEntries();
                setDataInChart();
                setRecyclerAdapter();
            }
        } else {
            Toast.makeText(getActivity(), "No details ", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("WrongConstant")
    private void initListeners() {
        pieChart = rootView.findViewById(R.id.pieChart);
        rvDetails = rootView.findViewById(R.id.rvDetails);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvDetails.setLayoutManager(linearLayoutManager);
    }

    private void getEntries() {
        pieEntries = new ArrayList<>();
        pieEntryLabels= new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            detail = details.get(i);
            {
                if (detail.getCategory().equals("Food")){
                    foodMoney=Integer.valueOf(detail.getMoneySpend().toString())+foodMoney;
                    pieEntryLabels.add(detail.getCategory());
                }else if (detail.getCategory().equals("Petrol")){
                    petrolMoney=Integer.valueOf(detail.getMoneySpend().toString())+petrolMoney;
                    pieEntryLabels.add(detail.getCategory());
                }else if (detail.getCategory().equals("Stationary")){
                    stationaryMoney=Integer.valueOf(detail.getMoneySpend().toString())+stationaryMoney;
                    pieEntryLabels.add(detail.getCategory());
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
}
