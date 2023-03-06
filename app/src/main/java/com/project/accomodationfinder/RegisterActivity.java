package com.project.accomodationfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    final Calendar myCalendar= Calendar.getInstance();

    TextView loginLabel;
    Button registerButton;
    EditText rgs_etFullName, rgs_etEmail, rgs_etPassword, rgs_etPhone,rgs_dpdob, rgs_etOccupation;
    Spinner rgs_spGender, rgs_spNationality;

    DatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{ this.getSupportActionBar().hide();}
        catch (NullPointerException e){ Log.e("RegisterActivity - onCreate", "Action bar reference NULL");}
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(this);


        loginLabel = findViewById(R.id.rgs_tvGotoLogin);
        registerButton = findViewById(R.id.rgs_btnRegister);

        rgs_etFullName = findViewById(R.id.rgs_etFullName);
        rgs_etEmail = findViewById(R.id.rgs_etEmail);
        rgs_etPassword = findViewById(R.id.rgs_etPassword);
        rgs_etPhone = findViewById(R.id.rgs_etPhone);
        rgs_dpdob = findViewById(R.id.rgs_dpdob);
        rgs_etOccupation = findViewById(R.id.rgs_etOccupation);

        rgs_spGender = findViewById(R.id.rgs_spGender);
        rgs_spNationality = findViewById(R.id.rgs_spNationality);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        rgs_dpdob.setOnClickListener(v -> {
            new DatePickerDialog(RegisterActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });





        loginLabel.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        registerButton.setOnClickListener(v -> {
            String fullName = rgs_etFullName.getText().toString();
            String email = rgs_etEmail.getText().toString();
            String password = rgs_etPassword.getText().toString();
            String phone = rgs_etPhone.getText().toString();
            String dob = rgs_dpdob.getText().toString();
            String occupation = rgs_etOccupation.getText().toString();
            String nationality = rgs_spNationality.getSelectedItem().toString();
            String gender = rgs_spGender.getSelectedItem().toString();



            String creationDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());



            if(fullName.equals("") || email.equals("") || password.equals("") || phone.equals("") || dob.equals("") || occupation.equals("")){
                Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
            else if(!email.contains("@") || !email.contains(".") ){
                Toast.makeText(RegisterActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            }
            else if(fullName.length() < 5)
            {
                Toast.makeText(RegisterActivity.this, "Full Name must be of  5 Letters", Toast.LENGTH_SHORT).show();
            }
            else {
                String tempUserName = fullName.substring(0,4);
                String tempDate = dob.substring(dob.length() - 4);

                String userName = tempUserName + tempDate;
                userName.length();


                Boolean checkUser = db.checkusername(email);
                if(!checkUser) {
                    Boolean insert = db.registerUser(fullName,email,dob,gender,nationality,phone,occupation,userName,password,creationDate);
                    if (insert) {
                       Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                         rgs_etFullName.getText().clear();
                         rgs_etEmail.getText().clear();
                         rgs_etPassword.getText().clear();
                         rgs_etPhone.getText().clear();
                         rgs_dpdob.getText().clear();
                         rgs_etOccupation.getText().clear();
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        rgs_dpdob.setText(dateFormat.format(myCalendar.getTime()));
    }
}