package com.example.simplecalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeeklyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeklyFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeeklyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeeklyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeeklyFragment newInstance(String param1, String param2) {
        WeeklyFragment fragment = new WeeklyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //Create Variables
    private ArrayList<FoodItem> week_foodList = new ArrayList<>();
    private ArrayList[] foodNamesList =  new ArrayList[7];

    private int index = MainActivity.getIndex();
    private String currentDay = MainActivity.getNameOfWeek(index + 1);
    private DayCalorie today = MainActivity.getDay(index);

    //Setup Views
    private Button daySelectorButton;
    private TextView ratioTextView;
    private TextView differenceTextView;

    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_weekly, container, false);

        ratioTextView = view.findViewById(R.id.ratio_textview);
        differenceTextView = view.findViewById(R.id.difference_textview);
        daySelectorButton = view.findViewById(R.id.day_selector_button);
        daySelectorButton.setText(currentDay);

        recyclerView = view.findViewById(R.id.week_recyclelist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new RecyclerAdapter(week_foodList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        daySelectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(daySelectorButton);
            }
        });

        loadMem();

        return view;
    }


    private void loadMem()
    {
        ratioTextView.setText(today.getTotalCalories() + " / 1600 calories");
        differenceTextView.setText((today.getTotalCalories() - 1600) + " Calories from daily limit");

        if (Memory.loadIndividualNames(getContext(), index) != null)
        {
            for (int i = 0; i < Memory.loadIndividualNames(getContext(), index).size(); i++)
            {
                week_foodList.add(new FoodItem(today.getNameOfFood(i), today.getIndividualCalorie(i)));
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void updateRecycleView()
    {
        week_foodList.removeAll(week_foodList);
        index = getDayIndex(currentDay);
        today = MainActivity.getDay(index);

        ratioTextView.setText(today.getTotalCalories() + " / 1600 calories");
        differenceTextView.setText((today.getTotalCalories() - 1600) + " Calories from daily limit");

        if (Memory.loadIndividualNames(getContext(), index) != null)
        {
            for (int i = 0; i < Memory.loadIndividualNames(getContext(), index).size(); i++)
            {
                week_foodList.add(new FoodItem(today.getNameOfFood(i), today.getIndividualCalorie(i)));
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void showPopup(View v)
    {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.select_days_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Monday:
                currentDay = "Monday";
                daySelectorButton.setText(currentDay);
                updateRecycleView();
                return true;
            case R.id.Tuesday:
                currentDay = "Tuesday";
                daySelectorButton.setText(currentDay);
                updateRecycleView();
                return true;
            case R.id.Wednesday:
                currentDay = "Wednesday";
                daySelectorButton.setText(currentDay);
                updateRecycleView();
                return true;
            case R.id.Thursday:
                currentDay = "Thursday";
                daySelectorButton.setText(currentDay);
                updateRecycleView();
                return true;
            case R.id.Friday:
                currentDay = "Friday";
                daySelectorButton.setText(currentDay);
                updateRecycleView();
                return true;
            case R.id.Saturday:
                currentDay = "Saturday";
                daySelectorButton.setText(currentDay);
                updateRecycleView();
                return true;
            case R.id.Sunday:
                currentDay = "Sunday";
                daySelectorButton.setText(currentDay);
                updateRecycleView();
                return true;
            default:
                return false;
        }
    }

    public int getDayIndex(String name)
    {
        switch (name)
        {
            case "Monday":
                return 0;
            case "Tuesday":
                return 1;
            case "Wednesday":
                return 2;
            case "Thursday":
                return 3;
            case "Friday":
                return 4;
            case "Saturday":
                return 5;
            case "Sunday":
                return 6;
            default:
                return index;
        }
    }
}
