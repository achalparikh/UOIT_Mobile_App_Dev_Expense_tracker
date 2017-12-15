package com.example.achal.expensetracker;

/**
 * Created by achal on 2017-12-11.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by achal on 2017-11-07.
 */

public class ExpenseDBHelper extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;

    static final String TABLE = "expenses";

    static final String CREATE_TABLE = "CREATE TABLE expenses (\n" +
            "      _id integer primary key autoincrement,\n" +
            "       cost double not null, \n" +
            "       category text not null, \n" +
            "       year integer not null,\n" +
            "       month integer not null,\n" +
            "       day integer not null\n" +
            ")\n";

    static final String DROP_TABLE = "DROP TABLE expenses";


    public ExpenseDBHelper (Context context) {
        super(context, "expenses", null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // delete the old database
        db.execSQL(DROP_TABLE);

        // re-create the database
        db.execSQL(CREATE_TABLE);
    }

    //CRUD Functions

    //create a database and table
    public Expense createExpense(double cost, String category, int year, int month, int day) {

        // create a new entity object (Expense)
        Expense expense = new Expense(cost, category, year, month, day);

        // put that data into the database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("cost", cost);
        newValues.put("category", category);
        newValues.put("year", year);
        newValues.put("month", month);
        newValues.put("day", day);

        long id = db.insert(TABLE, null, newValues);

        // update the contact's id
        expense.setId(id);

        return expense;
    }

    public ArrayList<Expense> getAllExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"_id", "cost", "category", "year", "month", "day"};
        String where = "";
        String[] whereArgs = new String[] {  };
        Cursor cursor = db.query(TABLE, columns, where, whereArgs, "", "", "");

        cursor.moveToFirst();
        do {
            if (!cursor.isAfterLast()) {
                long id = cursor.getLong(0);
                double cost = cursor.getDouble(1);
                String category = cursor.getString(2);
                int year = cursor.getInt(3);
                int month = cursor.getInt(4);
                int day = cursor.getInt(5);

                Expense expense = new Expense(cost, category, year, month, day);
                expense.setId(id);

                expenses.add(expense);
            }

            cursor.moveToNext();
        } while (!cursor.isAfterLast());

        return expenses;
    }

    // DELETE
    public boolean deleteExpense(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numRows = db.delete(TABLE, "_id = ?", new String[] { "" + id });

        return (numRows == 1);
    }

    public void deleteAllExpenses() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, "", new String[] { });
    }
}
