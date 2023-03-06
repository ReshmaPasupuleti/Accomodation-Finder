package com.project.accomodationfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FindAirportPickup extends AppCompatActivity {

    Button find_btnSearch ;
    final Calendar myCalendar= Calendar.getInstance();
    Spinner find_airportPicker;
    EditText find_dropLocation, find_dropDate, find_dropTime;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03DAC5")));
        this.getSupportActionBar().setTitle("Find Airport Pickup");

    } catch (NullPointerException e) {
        Log.e("FindAirportPickupUniversityActivity - onCreate", "Action bar reference NULL");
    }
        setContentView(R.layout.activity_find_airport_pickup);

        db = new DatabaseHelper(this);

        find_airportPicker = findViewById(R.id.find_airport_spinner);
        find_dropLocation = findViewById(R.id.find_dropLocation);
        find_dropDate = findViewById(R.id.find_dropDate);
        find_dropTime = findViewById(R.id.find_dropTime);


        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        find_dropDate.setOnClickListener(v -> {
            new DatePickerDialog(FindAirportPickup.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });

        find_dropTime.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(FindAirportPickup.this, (timePicker, selectedHour, selectedMinute) -> find_dropTime.setText( selectedHour + ":" + selectedMinute), hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });



        find_btnSearch = findViewById(R.id.find_btnSearch);

        find_btnSearch.setOnClickListener(v -> {

            String airport = find_airportPicker.getSelectedItem().toString();
            String dropLocation = find_dropLocation.getText().toString();
            String dropDate = find_dropDate.getText().toString();
            String dropTime = find_dropTime.getText().toString();


                Intent intent = new Intent(FindAirportPickup.this, PickupSearchResults.class);
                intent.putExtra("airportName", airport);
                intent.putExtra("dropLocation", dropLocation);
                intent.putExtra("pickupDate", dropDate);
                intent.putExtra("pickupTime", dropTime);
                intent.putExtra("pickupType", "findPickup");



                startActivity(intent);



        });
    }
    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        find_dropDate.setText(dateFormat.format(myCalendar.getTime()));
    }
}