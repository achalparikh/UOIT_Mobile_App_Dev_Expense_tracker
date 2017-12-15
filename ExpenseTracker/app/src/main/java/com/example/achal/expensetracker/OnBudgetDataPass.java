package com.example.achal.expensetracker;

/**
 * Created by achal on 2017-12-12.
 */

public interface OnBudgetDataPass {
    //used by fragments to send data to container activity for budgets
    public void onDataPass(double result);
}