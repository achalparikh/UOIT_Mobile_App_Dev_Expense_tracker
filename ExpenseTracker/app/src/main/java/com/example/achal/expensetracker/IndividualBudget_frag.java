package com.example.achal.expensetracker;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by achal on 2017-12-12.
 */

public class IndividualBudget_frag extends Fragment implements AdapterView.OnItemSelectedListener{

    private Button btn1, finish;
    private EditText budgetValueText;
    private Spinner spinner;
    private String category;
    private double budgetValue;
    private double [] categoryBudget = {0,0,0,0,0,0};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.individual_budget_frag, container, false);
        btn1= (Button) view.findViewById(R.id.btnCategoryBudget);
        finish = view.findViewById(R.id.finishBudgetActivity);
        budgetValueText = view.findViewById(R.id.txtCategoryBudget);
        Resources res = getResources();

        spinner = view.findViewById(R.id.categoryBudgetSpinner);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, res.getStringArray(R.array.categories));
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);

        //onclick store the budget according to category in array
        btn1.setOnClickListener((View) -> {
            if (budgetValueText.getText().length() > 0) {
                budgetValue = Double.parseDouble(budgetValueText.getText().toString());
                if (budgetValue > 0) {
                    switch (category) {
                        case "Food":
                            categoryBudget[0] += budgetValue;
                            break;
                        case "Alcohol":
                            categoryBudget[1] += budgetValue;
                            break;
                        case "Living":
                            categoryBudget[2] += budgetValue;
                            break;
                        case "Car":
                            categoryBudget[3] += budgetValue;
                            break;
                        case "Shopping":
                            categoryBudget[4] += budgetValue;
                            break;
                        case "Misc":
                            categoryBudget[5] += budgetValue;
                            break;
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter a budget greater than 0", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please enter a budget or back to exit", Toast.LENGTH_LONG).show();
            }

            finish.setOnClickListener((View1) -> {
                ((AddBudget)getActivity()).individualBudgetValue(categoryBudget);
            });
        });

        return view;
    }

    //get the category item
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        category = parent.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing
    }
}

