package com.pucmm.loginandmainpage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pucmm.loginandmainpage.database.RoomDB;
import com.pucmm.loginandmainpage.database.UserData;

public class ForgotPasswordActivity extends AppCompatActivity {

    private RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Initialize database
        database = RoomDB.getInstance(ForgotPasswordActivity.this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        final EditText emailText = findViewById(R.id.editTextTextEmailAddress2);
        Button getPassword = findViewById(R.id.button2);

        getPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = emailText.getText().toString().trim();

                if(!sEmail.equals("")){
                    String password;
                    password = database.userDao().getPasswordFromEmail(sEmail);
                    if(!password.equals("")){
                        Toast.makeText(ForgotPasswordActivity.this, "The password is: " + password, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ForgotPasswordActivity.this, "Password doesn't exist.", Toast.LENGTH_SHORT).show();
                    }
                }
                if(sEmail.equals("")){
                    emailText.setError("Please enter an email!");
                }
            }
        });

        TextView loginNow = findViewById(R.id.loginNow);
        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, HomeActivity.class));
            }
        });

        TextView registerNow = findViewById(R.id.registerNowText2);
        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, RegisterActivity2.class));
            }
        });
    }
}