package com.project.accomodationfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To hide the title bar
        try
        {this.getSupportActionBar().hide();}
        catch (NullPointerException e){Log.e("MainActivity - onCreate", "Action bar reference NULL");
        }
        setContentView(R.layout.activity_main);



        Thread background = new Thread() {
            public void run() {
                try {

                    sleep(3*1000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };

        background.start();
    }
}