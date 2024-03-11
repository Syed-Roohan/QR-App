package com.developer.qrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
    TextView tv_visit_login;
    String name, email, password, phone;
    EditText edt_name, edt_email, edt_pass, edt_phone;
    boolean isRegistered;
    DBHelper db = new DBHelper(Signup.this);
    Button btn_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_pass = findViewById(R.id.edt_pass);
        btn_signup = findViewById(R.id.btn_signup);
        tv_visit_login = findViewById(R.id.tv_visit_login);
        tv_visit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edt_name.getText().toString();
                email = edt_email.getText().toString();
                phone = edt_phone.getText().toString();
                password = edt_pass.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    edt_name.setError("Required");
                    edt_email.setError("Required");
                    edt_phone.setError("Required");
                    edt_pass.setError("Required");
                }
                else{
                    isRegistered  = db.isUserIsRegistered(name, email, phone, password);
                    if(isRegistered){
                        edt_name.setText("");
                        edt_email.setText("");
                        edt_phone.setText("");
                        edt_pass.setText("");
                        Toast.makeText(Signup.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    }
                    else{
                        Toast.makeText(Signup.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}