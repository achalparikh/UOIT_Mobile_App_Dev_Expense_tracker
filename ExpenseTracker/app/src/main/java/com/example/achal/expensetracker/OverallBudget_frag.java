package com.example.achal.expensetracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by achal on 2017-12-12.
 */


public class OverallBudget_frag extends Fragment {
    Button btn1;
    EditText budget;
    double budgetValue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overall_budget_frag, container, false);

        btn1 = (Button) view.findViewById(R.id.btnoverallBudget);
        budget = (EditText) view.findViewById(R.id.txtOverallBudget);
        //onClick get overall budget value if available
        btn1.setOnClickListener((View) -> {
            if (budget.getText().length() > 0) {
                budgetValue = Double.parseDouble(budget.getText().toString());
                if (budgetValue > 0) {
                    ((AddBudget)getActivity()).overallBudgetValue(budgetValue);
                } else {
                    Toast.makeText(getActivity(), "Please enter a budget greater than 0", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please enter a budget or back to exit", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}

