package com.example.simplecalapp;

import android.app.Application;

import java.util.ArrayList;

public class DayCalorie extends Application {

    //Variables
    private int totalCalories;
    private int dailyGoal;
    private ArrayList<Integer> individualCalories = new ArrayList<>();
    private ArrayList<String> nameOfFood = new ArrayList<>();
    private int totalFoodItems = 0;

    //Constructors
    public DayCalorie()
    {
        totalCalories = 0;
    }

    public DayCalorie(int totalCalories, int dailyGoal, ArrayList<Integer> individualCalories, ArrayList<String> nameOfFood, int totalFoodItems)
    {
        this.totalCalories = totalCalories;
        this.dailyGoal = dailyGoal;
        this.individualCalories = individualCalories;
        this.nameOfFood = nameOfFood;
        this.totalFoodItems = totalFoodItems;
    }

    //Getter and Setters
    public int getTotalCalories()
    {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void setNameOfFood(String name)
    {
        nameOfFood.add(name);
    }

    public void setNameOfFoodAll(ArrayList<String> list)
    {
        nameOfFood = list;
    }

    public String getNameOfFood(int index)
    {
        return nameOfFood.get(index);
    }

    public String getIndividualCalorie(int index)
    {
        return String.valueOf(individualCalories.get(index));
    }

    public ArrayList<Integer> getIndividualCaloriesAll()
    {
        return individualCalories;
    }

    public void setIndividualCalorie(int amount)
    {
        individualCalories.add(amount);
    }

    public void setAllIndividualCalorie(ArrayList<Integer> list)
    {
        individualCalories = list;
    }

    public ArrayList<String> getAllFoodNames()
    {
        return nameOfFood;
    }

    public int getTotalFoodItems()
    {
        return totalFoodItems;
    }

    public void setTotalFoodItems(int amount)
    {
        totalFoodItems = amount;
    }
}
