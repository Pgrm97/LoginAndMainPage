package com.pucmm.loginandmainpage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pucmm.loginandmainpage.database.RoomDB;

public class HomeActivity extends AppCompatActivity {

    private RoomDB database;
    EditText emailText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize database
        database = RoomDB.getInstance(HomeActivity.this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView forgotPassword = findViewById(R.id.forgotPasswordText);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ForgotPasswordActivity.class));
            }
        });

        TextView registerNow = findViewById(R.id.registerNowText);
        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RegisterActivity2.class));
            }
        });


        emailText = findViewById(R.id.editTextTextEmailAddress);
        passwordText = findViewById(R.id.editTextTextPassword);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(HomeActivity.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                String sEmail = emailText.getText().toString().trim();
                String sPassword = passwordText.getText().toString().trim();
                if(!sEmail.equals("") && !sPassword.equals("")){
                    String password;
                    password = database.userDao().getPasswordFromEmail(sEmail);

                    if(password == null){

                        Toast.makeText(HomeActivity.this, "Password doesn't exist.", Toast.LENGTH_SHORT).show();
                        emailText.setText("");
                        passwordText.setText("");
                    }
                    else if (!password.equals("")) {
                        if(password.equals(sPassword)){
                            emailText.setText("");
                            passwordText.setText("");
                            CharSequence text = "Signing in..";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(HomeActivity.this, text, duration);
                            toast.show();
                            new Handler().postDelayed(new Runnable(){
                                @Override
                                public void run() {
                                    startActivity(new Intent(HomeActivity.this, DrawerActivity.class));
                                }
                            }, 2000);
                        }
                        else{
                            Toast.makeText(HomeActivity.this, "Password doesn't match.", Toast.LENGTH_SHORT).show();
                            emailText.setText("");
                            passwordText.setText("");
                        }
                    }
                }
            }
        });

    }
}