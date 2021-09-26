package com.example.simplecalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ExampleViewHolder> {

    private ArrayList<FoodItem> mfoodList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView foodName;
        public TextView calories;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.name_textView);
            calories = itemView.findViewById(R.id.calories_textView);
        }
    }

    public RecyclerAdapter(ArrayList<FoodItem> foodList)
    {
        mfoodList = foodList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_food_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position)
    {
        FoodItem currentItem = mfoodList.get(position);

        holder.foodName.setText(currentItem.getName());
        holder.calories.setText(currentItem.getCalories());
    }

    @Override
    public int getItemCount() {
        return mfoodList.size();
    }
}
