package com.project.accomodationfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchByUniversity extends AppCompatActivity {


    EditText university;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03DAC5")));
            this.getSupportActionBar().setTitle("Search By University");

        } catch (NullPointerException e) {
            Log.e("SearchbyUniversityActivity - onCreate", "Action bar reference NULL");
        }
        setContentView(R.layout.activity_search_by_university);

        university = findViewById(R.id.sbu_etUniversity);


        searchButton = findViewById(R.id.sbu_btnSearch);
        searchButton.setOnClickListener(v -> {

            if(university.getText().toString().isEmpty()) {
                university.setError("Please enter a university name");
                Toast.makeText(this, "Please Enter University Name", Toast.LENGTH_SHORT).show();

            }
            else
            {
                Intent intent = new Intent(SearchByUniversity.this, AccomodationSearchResultsUniversity.class);
                String strName = university.getText().toString();
                intent.putExtra("searchTerm", strName);
                intent.putExtra("searchType", "University");
                startActivity(intent);

            }

        });

    }
}