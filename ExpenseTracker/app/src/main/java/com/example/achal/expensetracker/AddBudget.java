package com.example.achal.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class AddBudget extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAapter;
    private ViewPager viewPager;

    //container activity for the budget fragments
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);

        sectionsPageAapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager (ViewPager vp) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverallBudget_frag(), "Overall");
        adapter.addFragment(new IndividualBudget_frag(), "Individual");
        vp.setAdapter(adapter);
    }

    //send data from overall budget fragment
    public void overallBudgetValue (double result) {
        double overallBudget = result;
        Intent overallBudgetIntent = new Intent();
        overallBudgetIntent.putExtra("budget", overallBudget);
        setResult(1, overallBudgetIntent);
        finish();
    }

    //send data from individual budget fragment
    public void individualBudgetValue (double[] result) {
        double [] individualBudget = result;
        Intent individualBudgetIntent = new Intent();
        individualBudgetIntent.putExtra("individual", individualBudget);
        setResult(2, individualBudgetIntent);
        finish();
    }

}
