package com.project.accomodationfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PostAccomodation extends AppCompatActivity {

    final Calendar myCalendar= Calendar.getInstance();

     CheckBox chkBed,chkBath;

     Spinner genderPreference;
     EditText etBed, etBath, etRent, etNoofDays, etNoofPersons, etNearto, etMovinDate, etEmail, etWhatsapp, etFacilities, etCountry, etState, etCity, etPincode, etNearByPlaces, etLocationURL;
     Button postButton;
     DatabaseHelper db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03DAC5")));
            this.getSupportActionBar().setTitle("Post Accommodation");

        } catch (NullPointerException e) {
            Log.e("DashboardActivity - onCreate", "Action bar reference NULL");
        }
        setContentView(R.layout.activity_post_accomodation);

        db = new DatabaseHelper(this);



        genderPreference = findViewById(R.id.pstac_spGender);

        etRent = findViewById(R.id.pstac_etRent);
        etNoofDays = findViewById(R.id.pstac_etNoDays);
        etNoofPersons = findViewById(R.id.pstac_etNoPersons);
        etNearto = findViewById(R.id.pstac_etNearTo);
        etMovinDate = findViewById(R.id.pstac_etMovinDate);
        etEmail = findViewById(R.id.pstac_etEmail);
        etWhatsapp = findViewById(R.id.pstac_etPhone);
        etFacilities = findViewById(R.id.pstac_etFacilities);
        etCountry = findViewById(R.id.pstac_etCountry);
        etState = findViewById(R.id.pstac_etState);
        etCity = findViewById(R.id.pstac_etCity);
        etPincode = findViewById(R.id.pstac_etPincode);
        etNearByPlaces = findViewById(R.id.pstac_etNearby);
        etLocationURL = findViewById(R.id.pstac_etLocation);

        etBed = findViewById(R.id.pstac_BedCount);
        etBath = findViewById(R.id.pstac_BathCount);

        postButton = findViewById(R.id.pstac_btnPost);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        etMovinDate.setOnClickListener(v -> {
            new DatePickerDialog(PostAccomodation.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        postButton.setOnClickListener(v -> {

            String appartmentType = "";


            String genderPref = genderPreference.getSelectedItem().toString();

            String rent = etRent.getText().toString();
            String noOfDays = etNoofDays.getText().toString();
            String noOfPersons = etNoofPersons.getText().toString();
            String nearTo = etNearto.getText().toString();
            String movingDate = etMovinDate.getText().toString();
            String email = etEmail.getText().toString();
            String whatsapp = etWhatsapp.getText().toString();
            String facilities = etFacilities.getText().toString();
            String country = etCountry.getText().toString();
            String state = etState.getText().toString();
            String city = etCity.getText().toString();
            String pincode = etPincode.getText().toString();
            String nearByPlaces = etNearByPlaces.getText().toString();
            String locationURL = etLocationURL.getText().toString();

            String bedCount = etBed.getText().toString();
            String bathCount = etBath.getText().toString();


            if(bedCount.isEmpty() || bathCount.isEmpty() || rent.isEmpty() || noOfDays.isEmpty() || noOfPersons.isEmpty() || nearTo.isEmpty() || movingDate.isEmpty() || email.isEmpty() || whatsapp.isEmpty() || facilities.isEmpty() || country.isEmpty() || state.isEmpty() || city.isEmpty() || pincode.isEmpty() || nearByPlaces.isEmpty() || locationURL.isEmpty())
            {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(bedCount.equals("0") && bathCount.equals("0"))
                {
                    Toast.makeText(this, "Please enter valid number of bed and bath", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String aType = bedCount + " Bed " + ", " + bathCount + " Bath ";


                    String postDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                    SharedPreferences sh = getSharedPreferences("AccommodationsPref", MODE_PRIVATE);
                    int ID = sh.getInt("userID", 0);
                    String userID = String.valueOf(ID);

                    Boolean insert = db.addAccommodation(aType, genderPref, rent, noOfPersons,noOfDays, nearTo, email, whatsapp, facilities, country, state, city, pincode, nearByPlaces, locationURL, postDate, "NO", movingDate, userID, "NONE");
                    if (insert) {
                        Toast.makeText(PostAccomodation.this, "Accommodation Posted Successfully", Toast.LENGTH_SHORT).show();

                        etRent.setText("");
                        etNoofDays.setText("");
                        etNoofPersons.setText("");
                        etNearto.setText("");
                        etMovinDate.setText("");
                        etEmail.setText("");
                        etWhatsapp.setText("");
                        etFacilities.setText("");
                        etCountry.setText("");
                        etState.setText("");
                        etCity.setText("");
                        etPincode.setText("");
                        etNearByPlaces.setText("");
                        etLocationURL.setText("");

                    }
                    else
                    {
                        Toast.makeText(PostAccomodation.this, "Oops...Something went wrong !!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        etMovinDate.setText(dateFormat.format(myCalendar.getTime()));
    }
}