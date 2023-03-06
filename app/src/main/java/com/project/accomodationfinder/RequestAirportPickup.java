package com.project.accomodationfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RequestAirportPickup extends AppCompatActivity {
    
    Button add_button;
    final Calendar myCalendar= Calendar.getInstance();

    Spinner airportPicker;
    EditText dropLocation, dropDate, dropTime;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03DAC5")));
            this.getSupportActionBar().setTitle("Post Airport Pickup Service");

        } catch (NullPointerException e) {
            Log.e("AddAirportPickupUniversityActivity - onCreate", "Action bar reference NULL");
        }
        setContentView(R.layout.activity_request_airport_pickup);

        db = new DatabaseHelper(this);
        
        add_button = findViewById(R.id.rap_btnSearch);

        airportPicker = findViewById(R.id.rap_airport_spinner);
        dropLocation = findViewById(R.id.rap_dropLocation);
        dropDate = findViewById(R.id.rap_dropDate);
        dropTime = findViewById(R.id.rap_dropTime);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        dropDate.setOnClickListener(v -> {
            new DatePickerDialog(RequestAirportPickup.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });

        dropTime.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(RequestAirportPickup.this, (timePicker, selectedHour, selectedMinute) -> dropTime.setText( selectedHour + ":" + selectedMinute), hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

                });

        
        add_button.setOnClickListener(v -> {

            String selectedAirport = airportPicker.getSelectedItem().toString();
            String selectedLocation = dropLocation.getText().toString();
            String selectedDate = dropDate.getText().toString();
            String selectedTime = dropTime.getText().toString();

            if(selectedLocation.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }

            else
            {


                SharedPreferences sh = getSharedPreferences("AccommodationsPref", MODE_PRIVATE);
                int ID = sh.getInt("userID", 0);
                String userID = String.valueOf(ID);

                try
                    {

                //Log.d("AddAirportPickupUniversityActivity", "User ID: " + userID + " Airport: " + selectedAirport + " Location: " + selectedLocation + " Date: " + selectedDate + " Time: " + selectedTime);
                Boolean insert = db.addAirportPickup(selectedAirport, selectedDate, selectedTime, selectedLocation, "NO", userID, "NONE");
                if (insert) {
                    Toast.makeText(RequestAirportPickup.this, "Pickup Created Successfully", Toast.LENGTH_SHORT).show();

                    dropLocation.setText("");
                    dropDate.setText("");
                    dropTime.setText("");
                    dropLocation.clearFocus();
                }
                else
                {
                    Toast.makeText(RequestAirportPickup.this, "Oops...Something went wrong !!!", Toast.LENGTH_SHORT).show();
                }
                }
                catch (Exception e)
                {
                    Log.d("AddAirportPickupUniversityActivity", "User ID: " + userID + " Airport: " + selectedAirport + " Location: " + selectedLocation + " Date: " + selectedDate + " Time: " + selectedTime);


                    Log.d("AddAirportPickupUniversityActivity", "Error: " + e.getMessage());

                }

            }



            
        });


    }

    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dropDate.setText(dateFormat.format(myCalendar.getTime()));
    }
}