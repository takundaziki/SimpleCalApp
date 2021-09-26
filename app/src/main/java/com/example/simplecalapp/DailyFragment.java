package com.example.simplecalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DailyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyFragment newInstance(String param1, String param2) {
        DailyFragment fragment = new DailyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //My variables
    private int totalCaloriesAmount;
    private int index = MainActivity.getIndex();

    private FloatingActionButton fab;
    private TextView totalCalories;
    private MaterialCardView addCard;
    private Button doneButton;
    private EditText foodName;
    private EditText addCalorie;
    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<FoodItem> foodList = new ArrayList<>();

    private DayCalorie today;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily, container, false);

//        SharedPreferences settings = getContext().getSharedPreferences(Memory.SHARED_PREFS, Context.MODE_PRIVATE);
//        settings.edit().clear().commit();

        fab = view.findViewById(R.id.floatingActionButton);
        totalCalories = view.findViewById(R.id.totalCalories_textView);
        addCard = view.findViewById(R.id.addCard);
        doneButton = view.findViewById(R.id.done_button);
        foodName = view.findViewById(R.id.foodName_textView);
        addCalorie = view.findViewById(R.id.calorieAdd_textView);
        progressBar = view.findViewById(R.id.percent_bar);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new RecyclerAdapter(foodList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        addCard.setVisibility(View.GONE);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabOnClick(fab);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneOnClick(doneButton);
            }
        });

        today = MainActivity.getDay(index);

        loadMem();

        return view;
    }

    private void loadMem()
    {
        //Set TextView to the amount of calories total and the local variable
        totalCalories.setText(today.getTotalCalories() + "");
        totalCaloriesAmount = today.getTotalCalories();
        updateProgressBar(1600, today.getTotalCalories());

        //Sets the recycle view up
        Memory.loadCalories(getContext(), index);
        if (Memory.loadIndividualNames(getContext(), index) != null)
        {
            for (int i = 0; i < Memory.loadIndividualNames(getContext(), index).size(); i++)
            {
                foodList.add(new FoodItem(today.getNameOfFood(i), today.getIndividualCalorie(i)));
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void doneOnClick(View v)
    {
        if (addCalorie.getText().toString().equals(""))
        {
            addCard.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Update local variables
            totalCaloriesAmount += Integer.parseInt(addCalorie.getText().toString());

            //Save to today's DayCalorie Object
            today.setTotalCalories(totalCaloriesAmount);
            today.setNameOfFood(foodName.getText().toString());
            today.setIndividualCalorie(Integer.parseInt(addCalorie.getText().toString()));

            //Save to memory
            Memory.saveTotalCalories(getContext(), index, today.getTotalCalories());
            Memory.saveIndividualFoodNames(getContext(), index, today, foodName.getText().toString());
            Memory.saveCalorie(getContext(), index, today);

            //Enter into recycle view
            insertItem();

            //Update Views
            updateProgressBar(1600, today.getTotalCalories());
            totalCalories.setText(today.getTotalCalories()+ "");
            foodName.setText("");
            addCalorie.setText("");
            addCard.setVisibility(View.INVISIBLE);

        }
    }

    private void insertItem()
    {
        foodList.add(new FoodItem(today.getNameOfFood(Memory.loadTotalFoodItems(getContext(), index)), addCalorie.getText().toString()));
        Memory.saveTotalFoodItems(getContext(), index, today);
        adapter.notifyItemInserted(foodList.size());
    }

    private void fabOnClick(View v)
    {
        addCard.setVisibility(View.VISIBLE);
    }

    private void updateProgressBar(int goal, int progress)
    {
        double amount = (double) progress / goal;
        progressBar.setProgress((int) (amount * 100));
    }
}
