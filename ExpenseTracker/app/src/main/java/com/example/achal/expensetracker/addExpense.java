package com.example.achal.expensetracker;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class addExpense extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private ExpenseDBHelper db;
    private EditText costText;
    private Button btnCategory;
    private Button btnDate;
    private Button complete;
    private Spinner spinner;

    private double cost;
    private String category;
    private int year;
    private int month;
    private int day;
    //when location service is added use this
    private String location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Resources res = getResources();

        costText = findViewById(R.id.cost);
        btnCategory = findViewById(R.id.category);
        btnDate = findViewById(R.id.dateEntry);
        complete = findViewById(R.id.addExpense);
        spinner = findViewById(R.id.spinner);
        db = new ExpenseDBHelper(this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, res.getStringArray(R.array.categories));
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);

    }

    //Spinner Methods for Categories
    public void showCategories(View view) {
        spinner.setVisibility(View.VISIBLE);
        btnDate.setVisibility(View.INVISIBLE);
    }

    //make other buttons invisible for spinner data
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        spinner.setVisibility(View.INVISIBLE);
        btnDate.setVisibility(View.VISIBLE);
        category = parent.getItemAtPosition(i).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //DO NOTHING
    }

    //Date Picker Methods
    public void pickDate (View v) {
        DialogFragment datePicker = new DatePickerFrag();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    //select and store date values
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        this.year = year;
        this.month = month + 1;
        this.day = day;
    }

    //Retrieve cost and add expense to database
    public void addExpToDB(View view) {
        if (costText.getText().toString().length() > 0) {
            cost = Double.parseDouble(costText.getText().toString());
            Expense expense = new Expense(cost, category, year, month, day);

            Log.d("addExpense", "addExpToDB: " + expense.toString());
            db.createExpense(expense.getCost(), expense.getCategory(), expense.getYear(), expense.getMonth(), expense.getDay());
            Log.d("addExpense", "addExpToDB: " + db.getAllExpenses().toString());
            setResult(1);
            finish();
        } else {
            Toast.makeText(this, "Please Enter A Budget", Toast.LENGTH_SHORT).show();
        }
    }

}
