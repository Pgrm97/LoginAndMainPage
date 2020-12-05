package com.pucmm.loginandmainpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pucmm.loginandmainpage.database.RoomDB;
import com.pucmm.loginandmainpage.database.UserData;

public class RegisterActivity2 extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    private RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        //Initialize database
        database = RoomDB.getInstance(RegisterActivity2.this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView forgotPasswordRegister = findViewById(R.id.forgotPasswordRegister);
        forgotPasswordRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity2.this, ForgotPasswordActivity.class));
            }
        });

        TextView loginRegister = findViewById(R.id.loginNowRegister);
        loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity2.this, HomeActivity.class));
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        final TextView usernameText, passwordText, password2Text, person_nameText, phone_numberText, emailText;
        usernameText = findViewById(R.id.editTextUserRegister);
        passwordText = findViewById(R.id.editTextPasswordRegister);
        password2Text = findViewById(R.id.editTextPasswordRegister2);
        person_nameText = findViewById(R.id.editTextNameRegister);
        phone_numberText = findViewById(R.id.editTextPhone);
        emailText = findViewById(R.id.editTextEmailRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get String from edit text
                String sUsername = usernameText.getText().toString().trim();
                String sPassword = passwordText.getText().toString().trim();
                String s2Password = password2Text.getText().toString().trim();
                String sName = person_nameText.getText().toString().trim();
                String sPhone = phone_numberText.getText().toString().trim();
                String sEmail = emailText.getText().toString().trim();
                //Check condition
                if(!sUsername.equals("") && !sPassword.equals("") && !sName.equals("") && !sPhone.equals("") && !sEmail.equals("") && !s2Password.equals("")){
                    //Initialize main data
                    UserData data = new UserData();
                    //Set text on main data
                    data.setUserName(sUsername);
                    //Set text on main data
                    data.setPersonName(sName);
                    //Set text on main data
                    data.setEmail(sEmail);
                    //Set text on main data
                    data.setPassword(sPassword);
                    //Set text on main data
                    data.setPhoneNumber(sPhone);
                    //Insert text in database
                    database.userDao().insert(data);
                    //Clear edit text
                    usernameText.setText("");
                    passwordText.setText("");
                    password2Text.setText("");
                    person_nameText.setText("");
                    emailText.setText("");
                    phone_numberText.setText("");

                    startActivity(new Intent(RegisterActivity2.this, HomeActivity.class));
                }
                if(sUsername.equals("")){
                    usernameText.setError("Please enter a username!");
                }
                if(sEmail.equals("")){
                    emailText.setError("Please enter an email!");
                }
                if(sPassword.equals("")){
                    passwordText.setError("Please enter a password!");
                }
                if(s2Password.equals("")){
                    password2Text.setError("Please enter password again!");
                }
                if(sPhone.equals("")){
                    phone_numberText.setError("Please enter a phone!");
                }
                if(sName.equals("")){
                    person_nameText.setError("Please enter a name!");
                }
            }
        });

        ImageView imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(RegisterActivity2.this, "Opening File System", Toast.LENGTH_SHORT).show();
                    Uri selected = Uri.parse(Environment.getExternalStorageState());
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*") ;
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                }
                else{
                    requestStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                }
            }
        });
    }

    public void requestStoragePermission(final String permission, final int CODE) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity2.this, permission)){
            new AlertDialog.Builder(RegisterActivity2.this)
                    .setTitle("Permission needed")
                    .setMessage("Permission is needed for app to work.")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(RegisterActivity2.this, new String[]{permission}, CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
        else{
            ActivityCompat.requestPermissions(RegisterActivity2.this, new String[]{permission},CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Storage Permission GRANTED", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Storage Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}