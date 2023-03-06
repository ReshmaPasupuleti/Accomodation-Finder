package com.project.accomodationfinder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

public class DashboardActivity extends AppCompatActivity {


    CardView postAccomodationCard, searchAccomodationCard, requestAirportCard, findAirportCard;
    ImageButton menuButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
            Log.e("DashboardActivity - onCreate", "Action bar reference NULL");
        }
        setContentView(R.layout.activity_dashboard);

        postAccomodationCard = findViewById(R.id.postAccomodationCard);
        searchAccomodationCard = findViewById(R.id.searchAccomodationCard);
        requestAirportCard = findViewById(R.id.requestAirportCard);
        findAirportCard = findViewById(R.id.findAirportCard);

        menuButton = findViewById(R.id.menu_button);

        SharedPreferences sh = getSharedPreferences("AccommodationsPref", MODE_PRIVATE);

        int userID = sh.getInt("userID", 0);


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(DashboardActivity.this, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.myProfile:
                                Intent profile = new Intent(DashboardActivity.this, Profile.class);;
                                startActivity(profile);
                                break;

                            case R.id.myOrders:
                                Intent intent = new Intent(DashboardActivity.this, AccomodationSearchResultsUniversity.class);
                                intent.putExtra("searchType", "myOrders");
                                intent.putExtra("userID", userID);
                                startActivity(intent);
                                break;

                            case R.id.mypickupOrders:
                                Intent pickup = new Intent(DashboardActivity.this, PickupSearchResults.class);
                                pickup.putExtra("pickupType", "myPickupOrders");
                                pickup.putExtra("userID", userID);
                                startActivity(pickup);
                                break;
                            case R.id.logOut:
                                logout();


                        }
                        return false;
                    }
                });



                popup.inflate(R.menu.dashboard_menu);
                popup.show();

            }
        });



        postAccomodationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, PostAccomodation.class);
                startActivity(intent);

            }
        });

        requestAirportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, RequestAirportPickup.class);
                startActivity(intent);

            }
        });

        searchAccomodationCard.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this, R.style.AlertDialogTheme);
                builder.setTitle("Search Accommodation Type");
                builder.setMessage("Please select the type of accomodation you want to search");
                builder.setPositiveButton("Search By Area", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(DashboardActivity.this, SearchbyArea.class);
                        intent.putExtra("type", "Hotel");
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Search By University", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(DashboardActivity.this, SearchByUniversity.class);
                        intent.putExtra("type", "Hostel");
                        startActivity(intent);
                    }
                        });

                builder.show();

            }
        });

        findAirportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, FindAirportPickup.class);
                startActivity(intent);

            }
        });
    }

    public void logout()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this, R.style.AlertDialogTheme);
        builder.setTitle("Logout Confirmation");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SharedPreferences sh = getSharedPreferences("AccommodationsPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sh.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
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


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }
}
