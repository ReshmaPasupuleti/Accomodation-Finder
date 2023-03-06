package com.project.accomodationfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class PickupSearchResults extends AppCompatActivity {

    DatabaseHelper db;
    ArrayList<String>  airportSrno, airportName, dropLocation, pickupDate, pickupTime, postedBy;
    String paramAiportName, paramDropLocation, paramPickupDate, paramPickupTime;
    String type="";
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    PickupAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03DAC5")));
            this.getSupportActionBar().setTitle("Pickup Requests");

        } catch (NullPointerException e) {
            Log.e("Pickup Search Results - onCreate", "Action bar reference NULL");
        }
        setContentView(R.layout.activity_pickup_search_results);

        db = new DatabaseHelper(this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                paramAiportName= null;
            } else {
                paramAiportName= extras.getString("airportName");
                paramDropLocation= extras.getString("dropLocation");
                paramPickupDate= extras.getString("pickupDate");
                paramPickupTime= extras.getString("pickupTime");
                type= extras.getString("pickupType");

            }
        } else {
            paramAiportName= (String) savedInstanceState.getSerializable("airportName");
            paramDropLocation= (String) savedInstanceState.getSerializable("dropLocation");
            paramPickupDate= (String) savedInstanceState.getSerializable("pickupDate");
            paramPickupTime= (String) savedInstanceState.getSerializable("pickupTime");
            type= (String) savedInstanceState.getSerializable("pickupType");


        }

        airportSrno = new ArrayList<>();
        airportName = new ArrayList<>();
        dropLocation = new ArrayList<>();
        pickupDate = new ArrayList<>();
        pickupTime = new ArrayList<>();
        postedBy = new ArrayList<>();

        SharedPreferences sh = getSharedPreferences("AccommodationsPref", MODE_PRIVATE);
        int userID = sh.getInt("userID", 0);



        if(type.equals("myPickupOrders")) {

            Cursor cursor = db.fetchUserPickup(userID);
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No Pickup Found !!!", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {

                    airportSrno.add(cursor.getString(0));
                    airportName.add(cursor.getString(1));
                    dropLocation.add(cursor.getString(4));
                    pickupDate.add(cursor.getString(2));
                    pickupTime.add(cursor.getString(3));


                }
            }

            recyclerView = findViewById(R.id.rc_recyler_view);
            linearLayout = findViewById(R.id.rc_notFoundView);

            adapter = new PickupAdapter(this, airportSrno, airportName, dropLocation, pickupDate, pickupTime, postedBy, "myPickupOrders");
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


        else {

            Cursor data = db.fetchPickup(paramAiportName, paramPickupDate, userID);
            if (data.getCount() == 0) {
                Toast.makeText(this, "No Pickup Found !", Toast.LENGTH_SHORT).show();
            } else {
                while (data.moveToNext()) {

                    airportSrno.add(data.getString(0));
                    airportName.add(data.getString(1));
                    dropLocation.add(data.getString(4));
                    pickupDate.add(data.getString(2));
                    pickupTime.add(data.getString(3));


                }
            }

            recyclerView = findViewById(R.id.rc_recyler_view);
            linearLayout = findViewById(R.id.rc_notFoundView);

            adapter = new PickupAdapter(this, airportSrno, airportName, dropLocation, pickupDate, pickupTime, postedBy, "SEARCH");
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
    }
}