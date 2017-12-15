package com.example.achal.expensetracker;

/**
 * Created by achal on 2017-12-11.
 */
//Create an Expense Class with cost, category, and Date

public class Expense {
    private double cost;
    private String category;
    private long id;
    private int year;
    private int month;
    private int day;

    public Expense(double cost, String category, int year, int month, int day) {
        this.cost = cost;
        this.category = category;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString() {
        return  "Cost: " + cost + "\n" +
                "Category: " + category + "\n" +
                "Date: " + year + ", " + month + ", " + day;
    }
}
