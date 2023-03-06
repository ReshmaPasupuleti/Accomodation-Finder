package com.project.accomodationfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView registerLabel;
    Button loginButton;

    EditText lgn_etUsername, lgn_etPassword;

    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To hide the title bar
        try{ this.getSupportActionBar().hide();}
        catch (NullPointerException e){ Log.e("LoginActivity - onCreate", "Action bar reference NULL");}
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        registerLabel = findViewById(R.id.lgn_tvGotoRegister);
        loginButton = findViewById(R.id.lgn_btnLogin);


        lgn_etUsername = findViewById(R.id.lgn_etUsername);
        lgn_etPassword = findViewById(R.id.lgn_etPassword);




        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String user = lgn_etUsername.getText().toString();
                String pass = lgn_etPassword.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = db.checkusernamepassword(user, pass);
                    if (checkuserpass == true) {


                        int user_id = db.getUserID(user);


                       // Toast.makeText(LoginActivityClass.this, "User ID" + user_id , Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("AccommodationsPref",MODE_PRIVATE);
                        SharedPreferences.Editor sObj = sharedPreferences.edit();
                        sObj.putInt("userID", user_id);
                        sObj.commit();

                        Toast.makeText(LoginActivity.this, "Login Successful, Welcome Back", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registerLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(intent);
            }
        });
    }
}