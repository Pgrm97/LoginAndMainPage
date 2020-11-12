package com.pucmm.loginandmainpage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView forgotPasswordRegister = findViewById(R.id.forgotPasswordRegister);
        forgotPasswordRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity2.this, HomeActivity.class));
            }
        });

        TextView loginRegister = findViewById(R.id.loginNowRegister);
        loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity2.this, RegisterActivity2.class));
            }
        });
    }
}