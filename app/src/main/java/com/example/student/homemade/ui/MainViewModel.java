package com.example.student.homemade.ui;

import android.arch.lifecycle.ViewModel;

import com.example.student.homemade.R;

//ViewModel has a longer lifecycle than fragments and activities and can be used to store data which are required by
//multiple activities/ fragments
public class MainViewModel extends ViewModel {
    public String current_location = "Here";
    public int num_of_restaurants_available = 6;
    String restaurantNames[] = {"Laziz", "Ocean Pearl", "RedRock", "ChineseCafe", "Ideals", "Pabbas"};
    int images[] = {R.drawable.default_rest};
}

