package com.example.achal.expensetracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private PieChart pie;
    private ExpenseDBHelper db;
    double setBudget, totalExpense = 0;
    private double[] individualBudgets, individualExpenses;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        individualBudgets = new double [6];
        individualExpenses = new double[6];

        //Drawer Layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Navigation View Controls
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        pie = findViewById(R.id.mainPieChart);
        db = new ExpenseDBHelper(this);

        if (db.getAllExpenses().size() > 0) {
            expenseDataAvailable();
        }



    }

    //Go back to home screen when back button is pressed
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent showProfile = new Intent(this, UserProfile.class);
            startActivity(showProfile);
        }

        return super.onOptionsItemSelected(item);
    }

    //Start Individual Activities in the Nav Drawer
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.addExpense) {
            showExpenseActivity();

        } else if (id == R.id.editBudget) {
            Intent showBudget = new Intent(this, AddBudget.class);
            startActivityForResult(showBudget, 2);

        } else if (id == R.id.cashFlow) {
            Intent showReport = new Intent(this, cashFlow.class);

            showReport.putExtra("budget", setBudget)
            .putExtra("totalSpent", totalExpense)
            .putExtra("foodSpent", individualExpenses[0])
            .putExtra("alcoholSpent", individualExpenses[1])
            .putExtra("livingSpent", individualExpenses[2])
            .putExtra("carSpent", individualExpenses[3])
            .putExtra("shoppingSpent", individualExpenses[4])
            .putExtra("otherSpent", individualExpenses[5]);

            startActivity(showReport);

        } else if (id == R.id.userInfo) {
            Intent showProfile = new Intent(this, UserProfile.class);
            startActivity(showProfile);

        } else if (id == R.id.banks) {
            Intent showBanks = new Intent(this, Banks.class);
            startActivity(showBanks);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Activity Results once complete
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 1) {
                expenseDataAvailable();
            }
        } else if (requestCode == 2) {
            if (resultCode == 1) {
                setBudget = data.getDoubleExtra("budget", -100);
                Log.d("MainACtivity", "onActivityResult: " + setBudget);
                setupOverallBudgetChart(setBudget);
            }
            if (resultCode == 2) {
                individualBudgets = data.getDoubleArrayExtra("individual");
                setupIndividualBudgetChart();
            }
        }
    }

    //Add pie chart for Expenses
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void expenseDataAvailable() {
        double food = 0, alcohol = 0, living = 0, car = 0, shopping = 0, misc = 0;
        ArrayList<Expense> storedExpenses = db.getAllExpenses();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        //store data from individual expenses
        for (int i = 0; i < storedExpenses.size(); i++) {
            switch (storedExpenses.get(i).getCategory()) {
                case "Food":
                    food += storedExpenses.get(i).getCost();
                    individualExpenses[0] += food;
                    break;
                case "Alcohol":
                    alcohol += storedExpenses.get(i).getCost();
                    individualExpenses[1] += alcohol;
                    break;
                case "Living":
                    living += storedExpenses.get(i).getCost();
                    individualExpenses[2] += living;
                    break;
                case "Car":
                    car += storedExpenses.get(i).getCost();
                    individualExpenses[3] += car;
                    break;
                case "Shopping":
                    shopping += storedExpenses.get(i).getCost();
                    individualExpenses[4] += shopping;
                    break;
                case "Misc":
                    misc += storedExpenses.get(i).getCost();
                    individualExpenses[5] += misc;
                    break;
            }

            setupIndividualBudgetChart();
        }

        //Store Total expenses
        totalExpense += food + alcohol + living + car + shopping + misc;
        setupOverallBudgetChart(setBudget - totalExpense);

        //Set up PIE CHART for expenses
        pie.setUsePercentValues(false);
        pie.getDescription().setEnabled(false);
        pie.getLegend().setEnabled(true);
        pie.setExtraOffsets(10,10, 5,10);

        pie.setDragDecelerationFrictionCoef(0.95f);

        pie.setDrawHoleEnabled(true);
        pie.setHoleColor(Color.WHITE);
        pie.setTransparentCircleRadius(60f);

        if (food != 0) {
            pieEntries.add(new PieEntry((float)food, "food"));
        }
        if (alcohol != 0) {
            pieEntries.add(new PieEntry((float)alcohol, "alcohol"));
        }
        if (living != 0) {
            pieEntries.add(new PieEntry((float)living, "living"));
        }
        if (car != 0) {
            pieEntries.add(new PieEntry((float)car, "car"));
        }
        if (shopping != 0) {
            pieEntries.add(new PieEntry((float) shopping, "shopping"));
        }
        if (misc != 0) {
            pieEntries.add(new PieEntry((float) misc, "Other Expenses"));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(156,254,230));
        colors.add(Color.rgb(159,185,235));
        colors.add(Color.rgb(143,231,161));
        colors.add(Color.rgb(160,239,136));
        colors.add(Color.rgb(200,246,139));
        colors.add(Color.rgb(176,219,233));


        PieDataSet  pieDataSet = new PieDataSet(pieEntries, "Categories");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setColors(colors);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueTextColor(Color.BLACK);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextColor(Color.BLACK);
        pieData.setValueTextSize(17f);

        pie.setData(pieData);
        pie.animateXY(1500, 1500);
    }

    //show expense activity
    public void showExpenseActivity () {
        Intent showExpense = new Intent(this, addExpense.class);
        startActivityForResult(showExpense, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    //Setup overall budget bar
    public void setupOverallBudgetChart(double budget) {
        ProgressBar budgetbar = findViewById(R.id.overallBudgetBar);
        TextView txtBudget = findViewById(R.id.txtOverallBudget);

        txtBudget.setText("Budget: " + Double.toString(budget) + "\nMoney Left: " + (budget - totalExpense));
        budgetbar.setMax((int)budget);
        budgetbar.setProgress((int) (budget - totalExpense), true);

    }

    //TODO: save numbers for use
    //setup individual budget charts
    public void setupIndividualBudgetChart () {
        TextView food, living, car, alcohol, shopping, other;
        ProgressBar foodBar, livingBar, carBar, alcoholBar, shoppingBar, otherBar;

        food = findViewById(R.id.txtFoodBudget);
        living = findViewById(R.id.txtLivingBudget);
        car = findViewById(R.id.txtCarBudget);
        alcohol = findViewById(R.id.txtAlcoBudget);
        shopping = findViewById(R.id.txtShoppingBudget);
        other = findViewById(R.id.txtOtherBudget);

        foodBar = findViewById(R.id.foodpBar);
        livingBar = findViewById(R.id.livingBar);
        carBar = findViewById(R.id.carBar);
        alcoholBar = findViewById(R.id.alcoholBar);
        shoppingBar = findViewById(R.id.shoppingBar);
        otherBar = findViewById(R.id.otherBar);

        foodBar.setMax((int) individualBudgets[0]);
        foodBar.setProgress((int)individualBudgets[0] - (int)individualExpenses[0]);
        food.setText("Food Budget: " + individualBudgets[0]);

        alcoholBar.setMax((int)individualBudgets[1]);
        alcoholBar.setProgress((int)individualBudgets[1] - (int)individualExpenses[1]);
        alcohol.setText("Alcohol Budget: " + individualBudgets[1]);

        livingBar.setMax((int) individualBudgets[2]);
        livingBar.setProgress((int)individualBudgets[2] - (int)individualExpenses[2]);
        living.setText("Living Budget: " + individualBudgets[2]);

        carBar.setMax((int)individualBudgets[3]);
        carBar.setProgress((int)individualBudgets[3] - (int)individualExpenses[3]);
        car.setText("Car Budget: " + individualBudgets[3]);

        shoppingBar.setMax((int)individualBudgets[4]);
        shoppingBar.setProgress((int)individualBudgets[4] - (int)individualExpenses[4]);
        shopping.setText("Shopping Budget: " + individualBudgets[4]);

        otherBar.setMax((int)individualBudgets[5]);
        otherBar.setProgress((int)individualBudgets[5] - (int)individualExpenses[5]);
        other.setText("Other Budget: " + individualBudgets[5]);

        //TODO: Save numbers for next time use

    }
}
