package com.example.simplecalapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private DailyFragment dailyFragment;
    private WeeklyFragment weeklyFragment;

    private static int day;

    private static DayCalorie[] dayCalorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        dayCalorie = {new DayCalorie(), new DayCalorie(), new DayCalorie(), new DayCalorie(), new DayCalorie(), new DayCalorie(), new DayCalorie()};

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getNameOfWeek(day));
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);


        dailyFragment = new DailyFragment();
        weeklyFragment = new WeeklyFragment();


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(dailyFragment, "daily");
        viewPagerAdapter.addFragment(weeklyFragment, "weekly");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_today_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_event_note_black_24dp);

        if(getNameOfWeek(day) == "Monday" && Memory.loadDelete(this))
        {
            SharedPreferences settings = this.getSharedPreferences(Memory.SHARED_PREFS, Context.MODE_PRIVATE);
            settings.edit().clear().commit();
            Memory.saveDelete(this, false);
        }
        else if (getNameOfWeek(day) == "Tuesday" && !Memory.loadDelete(this))
        {
            Memory.saveDelete(this, true);
        }

        //set up DayCalories
        for (int i  = 0; i < dayCalorie.length; i++)
        {
            if(Memory.loadIndividualNames(this, i) != null) {
                dayCalorie[i].setTotalCalories(Memory.loadTotalCalories(this, i));
                dayCalorie[i].setAllIndividualCalorie((ArrayList<Integer>) Memory.loadCalories(this, i));
                dayCalorie[i].setNameOfFoodAll((ArrayList<String>) Memory.loadIndividualNames(this, i));
                dayCalorie[i].setTotalFoodItems(Memory.loadTotalFoodItems(this, i));
            }
        }
    }

    public static DayCalorie getDay(int index)
    {
        return dayCalorie[index];
    }

    public static int getIndex()
    {
        return day - 1;
    }

    public static String getNameOfWeek(int today)
    {
        String nameOfDay = "";

        switch (today)
        {
            case 1:  nameOfDay = "Monday";
                break;
            case 2:  nameOfDay = "Tuesday";
                break;
            case 3:  nameOfDay = "Wednesday";
                break;
            case 4:  nameOfDay = "Thursday";
                break;
            case 5:  nameOfDay = "Friday";
                break;
            case 6:  nameOfDay = "Saturday";
                break;
            case 7:  nameOfDay = "Sunday";
                break;
            default: nameOfDay = "Calorie Tracker";
                break;
        }
        return nameOfDay;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter
    {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title)
        {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}
