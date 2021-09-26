package com.example.simplecalapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Memory extends Application
{
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String DELETE = "delete";
    public static final String GOAL = "goal";

    public static final String[] TOTALS = {"total1", "total2", "total3", "total4", "total5", "total6", "total7"};
    public static final String[] CALORIES = {"cal1", "cal2", "cal3", "cal4", "cal5", "cal6", "cal7"};

    public static final String[] FOODNAME = {"food1", "food2", "food3", "food4", "food5", "food6", "food7"};
    public static final String[] INDIVIDUAL_CALORIES = {"ical1", "ical2", "ical3", "ical4", "ical5", "ical6", "ical7"};

    public static void saveIndividualFoodNames(Context context, int index, DayCalorie today, String nameOfFood)
    {
        Gson gson = new Gson();
        String jsonString = gson.toJson(today.getAllFoodNames());

        SharedPreferences sharedPreferences = context.getSharedPreferences(Memory.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Memory.FOODNAME[index], jsonString);
        editor.apply();
    }

    public static List<String> loadIndividualNames(Context context, int index)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Memory.SHARED_PREFS, Context.MODE_PRIVATE);
        String jsonString = sharedPreferences.getString(Memory.FOODNAME[index], "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        List<String> list = gson.fromJson(jsonString, type);

        return list;
    }

    public static void saveCalorie(Context context, int index, DayCalorie today)
    {
        List<Integer> list = today.getIndividualCaloriesAll();


        Gson gson = new Gson();
        String jsonString = gson.toJson(list);

        SharedPreferences sharedPreferences = context.getSharedPreferences(Memory.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(INDIVIDUAL_CALORIES[index], jsonString);
        editor.apply();
    }

    public static List<Integer> loadCalories(Context context, int index)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Memory.SHARED_PREFS, Context.MODE_PRIVATE);
        String jsonString = sharedPreferences.getString(INDIVIDUAL_CALORIES[index], "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        List<Integer> list = gson.fromJson(jsonString, type);

        return list;
    }

    public static void saveTotalCalories(Context context, int index, int amount)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Memory.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(Memory.TOTALS[index], amount);
        editor.apply();
    }

    public static int loadTotalCalories(Context context, int index)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Memory.SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Memory.TOTALS[index], 0);
    }

    public static void saveTotalFoodItems(Context context, int index, DayCalorie today)
    {
        today.setTotalFoodItems(Memory.loadTotalFoodItems(context, 0));

        SharedPreferences sharedPreferences = context.getSharedPreferences(Memory.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(Memory.CALORIES[index], loadTotalFoodItems(context,index) + 1);
        editor.apply();

    }

    public static int loadTotalFoodItems(Context context, int index)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Memory.SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Memory.CALORIES[index], 0);
    }

    public static void saveDelete(Context context, boolean amount)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Memory.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(Memory.DELETE, amount);
        editor.apply();
    }

    public static boolean loadDelete(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Memory.SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Memory.DELETE, true);
    }
}
