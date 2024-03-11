package com.developer.qrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText edt_email, edt_pass;
    TextView tv_visit_signup;
    String email, password;
    Button btn_login;
    boolean isloggedIn;

    DBHelper db = new DBHelper(Login.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        tv_visit_signup = findViewById(R.id.tv_visit_signup);
        btn_login = findViewById(R.id.btn_login);
        tv_visit_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Signup.class));
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edt_email.getText().toString();
                password = edt_pass.getText().toString();
                if (email.isEmpty()) {
                    edt_email.setError("Required");
                } else if (password.isEmpty()) {
                    edt_pass.setError("Required");
                } else {
                    isloggedIn = db.isUserLoggedIn(email, password);
                    if (isloggedIn) {
                        Intent intent= new Intent(getApplicationContext(), Dashboard.class);
                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.apply();
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}