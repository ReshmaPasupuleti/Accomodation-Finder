package com.project.accomodationfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AccomodationSearchResultsUniversity extends AppCompatActivity {
    DatabaseHelper db;
    Spinner spType;
    ArrayList<String> accSrno, accType, accGenderPref, accRent, accNoPersons, accNoDays, accNearTo,
            accEmail, accWhatsapp, accFacilities, accCountry, accState, accCity, accPincode,
            accNearByPlaces, accLocationURL, accPostDate, accOccupied, accMovinDate, accPostedBy, accOccupiedBy;
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    RecyclerViewAdapter adapter;
    String searchType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03DAC5")));
            this.getSupportActionBar().setTitle("Search Results");

        } catch (NullPointerException e) {
            Log.e("SearchbyUniversityActivity - onCreate", "Action bar reference NULL");
        }
        setContentView(R.layout.activity_accomodation_search_results_university);
        spType = findViewById(R.id.rc_sp_bookingType);

        db = new DatabaseHelper(this);


        String userID, searchTerm, country = "", state = "", city = "", pincode = "", nearby = "", budget = "", movin = "", noofPerson = "", stayPreference = "";
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                searchTerm = null;
            } else {
                searchTerm = extras.getString("searchTerm");
                searchType = extras.getString("searchType");
                userID = extras.getString("userID");


                country = extras.getString("country");
                state = extras.getString("state");
                city = extras.getString("city");
                pincode = extras.getString("pincode");
                nearby = extras.getString("nearby");
                budget = extras.getString("budget");
                movin = extras.getString("movin");
                noofPerson = extras.getString("noofPerson");
                stayPreference = extras.getString("stayPreference");


            }
        } else {

            userID = (String) savedInstanceState.getSerializable("userID");
            searchTerm = (String) savedInstanceState.getSerializable("searchTerm");
            searchType = (String) savedInstanceState.getSerializable("searchType");

            country = (String) savedInstanceState.getSerializable("country");
            state = (String) savedInstanceState.getSerializable("state");
            city = (String) savedInstanceState.getSerializable("city");
            pincode = (String) savedInstanceState.getSerializable("pincode");
            nearby = (String) savedInstanceState.getSerializable("nearby");
            budget = (String) savedInstanceState.getSerializable("budget");
            movin = (String) savedInstanceState.getSerializable("movin");
            noofPerson = (String) savedInstanceState.getSerializable("noofPerson");
            stayPreference = (String) savedInstanceState.getSerializable("stayPreference");

        }


        accSrno = new ArrayList<>();
        accType = new ArrayList<>();
        accGenderPref = new ArrayList<>();
        accRent = new ArrayList<>();
        accNoPersons = new ArrayList<>();
        accNoDays = new ArrayList<>();
        accNearTo = new ArrayList<>();
        accEmail = new ArrayList<>();
        accWhatsapp = new ArrayList<>();
        accFacilities = new ArrayList<>();
        accCountry = new ArrayList<>();
        accState = new ArrayList<>();
        accCity = new ArrayList<>();
        accPincode = new ArrayList<>();
        accNearByPlaces = new ArrayList<>();
        accLocationURL = new ArrayList<>();
        accPostDate = new ArrayList<>();
        accOccupied = new ArrayList<>();
        accMovinDate = new ArrayList<>();
        accPostedBy = new ArrayList<>();
        accOccupiedBy = new ArrayList<>();


        SharedPreferences sh = getSharedPreferences("AccommodationsPref", MODE_PRIVATE);
        int userIDs = sh.getInt("userID", 0);

        if (searchType.equals("University")) {
            singleSearch(searchTerm, userIDs);
        } else if (searchType.equals("Area")) {

            multiSearch(userIDs, country, state, city, pincode, nearby, budget, movin, noofPerson, stayPreference);
        } else if (searchType.equals("myOrders")) {


            try {
                this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03DAC5")));
                this.getSupportActionBar().setTitle("My Posts");

            } catch (NullPointerException e) {
                Log.e("SearchbyUniversityActivity - onCreate", "Action bar reference NULL");
            }


            // spType.setVisibility(View.VISIBLE);
            orders(userIDs);

        }


    }
    void singleSearch(String searchTerm, int userID) {

        Cursor data = db.fetchAccommodations(searchTerm, userID);
        if (data.getCount() == 0) {
            Toast.makeText(this, "No Accommodations Found !", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {

                accSrno.add(data.getString(0));
                accType.add(data.getString(1));
                accGenderPref.add(data.getString(2));
                accRent.add(data.getString(3));
                accNoPersons.add(data.getString(4));
                accNoDays.add(data.getString(5));
                accNearTo.add(data.getString(6));
                accEmail.add(data.getString(7));
                accWhatsapp.add(data.getString(8));
                accFacilities.add(data.getString(9));
                accCountry.add(data.getString(10));
                accState.add(data.getString(11));
                accCity.add(data.getString(12));
                accPincode.add(data.getString(13));
                accNearByPlaces.add(data.getString(14));
                accLocationURL.add(data.getString(15));
                accPostDate.add(data.getString(16));
                accOccupied.add(data.getString(17));
                accMovinDate.add(data.getString(18));
                accPostedBy.add(data.getString(19));
                accOccupiedBy.add(data.getString(20));

            }
        }

        recyclerView = findViewById(R.id.rc_recyler_view);
        linearLayout = findViewById(R.id.rc_notFoundView);

        adapter = new RecyclerViewAdapter(this, accSrno, accType, accGenderPref, accRent, accNoPersons, accNoDays, accNearTo, accEmail, accWhatsapp, accFacilities,
                accCountry, accState, accCity, accPincode, accNearByPlaces, accLocationURL, accPostDate, accOccupied, accMovinDate, accPostedBy, accOccupiedBy, "singleSearch");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            spType.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);

        }
    }
    void multiSearch(int userID, String country, String state, String city, String pincode, String nearby,
                     String budget, String movin, String noofPerson, String stayPreference) {
        Cursor data = db.fetchAreaAccommodations(userID, country, state, city, pincode, nearby, budget, movin, noofPerson, stayPreference);
        if (data.getCount() == 0) {
            Toast.makeText(this, "No Accommodations Found !", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {

                accSrno.add(data.getString(0));
                accType.add(data.getString(1));
                accGenderPref.add(data.getString(2));
                accRent.add(data.getString(3));
                accNoPersons.add(data.getString(4));
                accNoDays.add(data.getString(5));
                accNearTo.add(data.getString(6));
                accEmail.add(data.getString(7));
                accWhatsapp.add(data.getString(8));
                accFacilities.add(data.getString(9));
                accCountry.add(data.getString(10));
                accState.add(data.getString(11));
                accCity.add(data.getString(12));
                accPincode.add(data.getString(13));
                accNearByPlaces.add(data.getString(14));
                accLocationURL.add(data.getString(15));
                accPostDate.add(data.getString(16));
                accOccupied.add(data.getString(17));
                accMovinDate.add(data.getString(18));
                accPostedBy.add(data.getString(19));
                accOccupiedBy.add(data.getString(20));

            }
        }

        recyclerView = findViewById(R.id.rc_recyler_view);
        linearLayout = findViewById(R.id.rc_notFoundView);

        adapter = new RecyclerViewAdapter(this, accSrno, accType, accGenderPref, accRent, accNoPersons, accNoDays, accNearTo, accEmail, accWhatsapp, accFacilities,
                accCountry, accState, accCity, accPincode, accNearByPlaces, accLocationURL, accPostDate, accOccupied, accMovinDate, accPostedBy, accOccupiedBy, "multiSearch");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }
    }
    void orders(int userID) {
        Cursor data = db.fetchUserOrders(userID);

        if (data.getCount() == 0) {
            Toast.makeText(this, "No Posts Found !", Toast.LENGTH_SHORT).show();

        } else {
            while (data.moveToNext()) {

                accSrno.add(data.getString(0));
                accType.add(data.getString(1));
                accGenderPref.add(data.getString(2));
                accRent.add(data.getString(3));
                accNoPersons.add(data.getString(4));
                accNoDays.add(data.getString(5));
                accNearTo.add(data.getString(6));
                accEmail.add(data.getString(7));
                accWhatsapp.add(data.getString(8));
                accFacilities.add(data.getString(9));
                accCountry.add(data.getString(10));
                accState.add(data.getString(11));
                accCity.add(data.getString(12));
                accPincode.add(data.getString(13));
                accNearByPlaces.add(data.getString(14));
                accLocationURL.add(data.getString(15));
                accPostDate.add(data.getString(16));
                accOccupied.add(data.getString(17));
                accMovinDate.add(data.getString(18));
                accPostedBy.add(data.getString(19));
                accOccupiedBy.add(data.getString(20));

            }

        }

        recyclerView = findViewById(R.id.rc_recyler_view);
        linearLayout = findViewById(R.id.rc_notFoundView);


        adapter = new RecyclerViewAdapter(this, accSrno, accType, accGenderPref, accRent, accNoPersons, accNoDays, accNearTo, accEmail, accWhatsapp, accFacilities, accCountry, accState, accCity, accPincode, accNearByPlaces, accLocationURL, accPostDate, accOccupied, accMovinDate, accPostedBy, accOccupiedBy, "myOrders");


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (adapter.getItemCount() == 0) {
            //   Toast.makeText(this, "Voila !!!! No Accommodations Found !", Toast.LENGTH_SHORT).show();

            recyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);

        } else {
            recyclerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);

        }
    }
}