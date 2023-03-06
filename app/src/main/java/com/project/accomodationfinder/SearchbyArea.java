package com.project.accomodationfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchbyArea extends AppCompatActivity {
    Button btn_search;
    EditText etCountry, etState, etCity , etPincode, etNearby, etBudget ,  etMovin, etNoPersons;
    Spinner sba_spStayPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03DAC5")));
            this.getSupportActionBar().setTitle("Search By Area");

        } catch (NullPointerException e) {
            Log.e("SearchbyUniversityActivity - onCreate", "Action bar reference NULL");
        }
        setContentView(R.layout.activity_searchby_area);


        etCountry = findViewById(R.id.sba_etCountry);
        etState = findViewById(R.id.sba_etState);
        etCity = findViewById(R.id.sba_etCity);
        etPincode = findViewById(R.id.sba_etZip);
        etNearby = findViewById(R.id.sba_etNearBy);
        etBudget = findViewById(R.id.sba_etBudget);
        etMovin = findViewById(R.id.sba_etDate);
        etNoPersons = findViewById(R.id.sba_etSharing);

         sba_spStayPreference = findViewById(R.id.sba_spStayPreference);



        btn_search = findViewById(R.id.sba_btnSearch);
        btn_search.setOnClickListener(v -> {

            String country = etCountry.getText().toString();
            String state = etState.getText().toString();
            String city = etCity.getText().toString();
            String pincode = etPincode.getText().toString();
            String nearby = etNearby.getText().toString();
            String budget = etBudget.getText().toString();
            String movin = etMovin.getText().toString();
            String noPersons = etNoPersons.getText().toString();
            String stayPreference = sba_spStayPreference.getSelectedItem().toString();


            Intent intent = new Intent(SearchbyArea.this, AccomodationSearchResultsUniversity.class);

            intent.putExtra("searchType", "Area");
            intent.putExtra("country", country);
            intent.putExtra("state", state);
            intent.putExtra("city", city);
            intent.putExtra("pincode", pincode);
            intent.putExtra("nearby", nearby);
            intent.putExtra("budget", budget);
            intent.putExtra("movin", movin);
            intent.putExtra("noofPerson", noPersons);
            intent.putExtra("stayPreference", stayPreference);

            startActivity(intent);



        });
    }
}