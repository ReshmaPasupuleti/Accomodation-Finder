package com.project.accomodationfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

public class AccomodationSearchResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03DAC5")));
            this.getSupportActionBar().setTitle("Search Results");

        } catch (NullPointerException e) {
            Log.e("FindAirportPickupUniversityActivity - onCreate", "Action bar reference NULL");
        }
        setContentView(R.layout.activity_accomodation_search_results);
    }
}