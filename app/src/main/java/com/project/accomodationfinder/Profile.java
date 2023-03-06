package com.project.accomodationfinder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends AppCompatActivity {


    TextView profileName, profileUsername, profileEmail, profilePhone, profileDob, profileNationality, profileGender, profileCreatedDate;

    Button profileLogout;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03DAC5")));
            this.getSupportActionBar().setTitle("My Profile");

        } catch (NullPointerException e) {
            Log.e("DashboardActivity - onCreate", "Action bar reference NULL");
        }
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profileFullname);
        profileUsername = findViewById(R.id.profileUsername);
        profileEmail = findViewById(R.id.profileEmail);
        profilePhone = findViewById(R.id.profilePhone);
        profileDob = findViewById(R.id.profileDOB);
        profileNationality = findViewById(R.id.profileNationality);
        profileGender = findViewById(R.id.profileGender);
        profileCreatedDate = findViewById(R.id.profileCreatedDate);

        profileLogout = findViewById(R.id.profileLogout);


        profileLogout.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this, R.style.AlertDialogTheme);
            builder.setTitle("Logout Confirmation");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    SharedPreferences sh = getSharedPreferences("AccommodationsPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(Profile.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();


        });




        db = new DatabaseHelper(this);

        SharedPreferences sh = getSharedPreferences("AccommodationsPref", MODE_PRIVATE);
        int ID = sh.getInt("userID", 0);
        String userID = String.valueOf(ID);

        Cursor profileDetails = db.getUserProfile(userID);

        if (profileDetails.getCount() == 0) {
            Log.d("Profile", "No profile found");

        }
        else {
            profileDetails.moveToFirst();
            String srno = profileDetails.getString(0);
            String fullname = profileDetails.getString(1);
            String email = profileDetails.getString(2);
            String dob = profileDetails.getString(3);
            String gender = profileDetails.getString(4);
            String nationality = profileDetails.getString(5);
            String phone = profileDetails.getString(6);
            String occupation = profileDetails.getString(7);
            String username = profileDetails.getString(8);
            String createdDate = profileDetails.getString(10);

            profileName.setText(fullname);
            profileUsername.setText(username);
            profileEmail.setText(email);
            profilePhone.setText(phone);
            profileDob.setText(dob);
            profileNationality.setText(nationality);
            profileGender.setText(gender);
            profileCreatedDate.setText("Account Created Date : " +createdDate);




        }











    }
}