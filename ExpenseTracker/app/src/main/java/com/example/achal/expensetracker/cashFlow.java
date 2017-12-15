package com.example.achal.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class cashFlow extends AppCompatActivity {
    private double budget, expense, food, alcohol, living, car, shopping, other;
    private TextView budgetVal, expenseVal, foodVal, alcoholVal, livingVal, carVal, shoppingVal, otherVal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_flow);

        Intent values = getIntent();

        //initialize textviews
        budgetVal = findViewById(R.id.totalBudValue);
        expenseVal = findViewById(R.id.totalSpentValue);
        foodVal = findViewById(R.id.foodSpentValue);
        alcoholVal = findViewById(R.id.alcoholSpentValue);
        livingVal = findViewById(R.id.livingSpentValue);
        carVal = findViewById(R.id.carSpentValue);
        shoppingVal = findViewById(R.id.shoppingSpentValue);
        otherVal = findViewById(R.id.otherSpentValue);

        //initialize values recevied
        budget = values.getDoubleExtra("budget", 0);
        expense =  values.getDoubleExtra("totalExpense", 0);
        food = values.getDoubleExtra("foodSpent", 0);
        alcohol = values.getDoubleExtra("alcoholSpent", 0);
        living = values.getDoubleExtra("livingSpent", 0);
        car = values.getDoubleExtra("carSpent", 0);
        shopping = values.getDoubleExtra("shoppingSpent", 0);
        other = values.getDoubleExtra("otherSpent", 0);

        //update textviews with values
        budgetVal.setText(Double.toString(budget));
        expenseVal.setText(Double.toString(expense));
        foodVal.setText(Double.toString(food));
        alcoholVal.setText(Double.toString(alcohol));
        livingVal.setText(Double.toString(living));
        carVal.setText(Double.toString(car));
        shoppingVal.setText(Double.toString(shopping));
        otherVal.setText(Double.toString(other));

    }
}
