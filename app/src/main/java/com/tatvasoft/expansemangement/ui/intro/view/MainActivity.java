package com.tatvasoft.expansemangement.ui.intro.view;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


import com.google.android.material.button.MaterialButton;
import com.tatvasoft.expansemangement.R;
import com.tatvasoft.expansemangement.adapter.DrawerItemCustomAdapter;
import com.tatvasoft.expansemangement.ui.category.view.CategoryFragment;
import com.tatvasoft.expansemangement.ui.home.view.HomeFragment;
import com.tatvasoft.expansemangement.ui.intro.model.DataModel;
import com.tatvasoft.expansemangement.ui.report.view.ReportFragment;
import com.tatvasoft.expansemangement.util.CommonUtil;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ActionBarDrawerToggle mDrawerToggle;
    private String finalIncome;
    private Bundle bundleIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);

        setupToolbar();

        DataModel[] drawerItem = new DataModel[3];

        drawerItem[0] = new DataModel(R.drawable.ic_home_black_24dp, "Home");
        drawerItem[1] = new DataModel(R.drawable.ic_format_list_bulleted_black_24dp, "Add Category");
        drawerItem[2] = new DataModel(R.drawable.ic_report_black_24dp, "Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();
        addIncome();
    }

    private void addIncome() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_income);
        final MaterialButton btnSubmit = dialog.findViewById(R.id.btnSubmit);
        final EditText edMonthlyIncome = dialog.findViewById(R.id.edMonthlyIncome);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CommonUtil.isEmptyEditText(edMonthlyIncome) && CommonUtil.isNotNull(edMonthlyIncome)) {
                    edMonthlyIncome.setError("Add income");
                } else {
                    finalIncome = edMonthlyIncome.getText().toString();
                    bundleIncome = new Bundle();
                    bundleIncome.putString("income", finalIncome);
                    selectItem(0,null);
                    dialog.dismiss();
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position,null);
        }

    }

    public void selectItem(int position,Bundle bundle) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                if (bundleIncome!=null){
                    fragment.setArguments(bundleIncome);
                }
                break;
            case 1:
                fragment = new CategoryFragment();
                if (bundle!=null){
                    fragment.setArguments(bundle);
                }
                break;
            case 2:
                fragment = new ReportFragment();
                if (bundle!=null){
                    fragment.setArguments(bundle);
                }
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new androidx.appcompat.app.ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }
}
