package com.developer.qrapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dashboard extends AppCompatActivity {
    CodeScannerView scanner_view;
    CodeScanner codeScanner;
    TextView tv_data;
    Button btn_save, btn_viewHistory, btn_profile;
    String data;
    boolean isHistoryAdded;
    int PERMISSION_ALL = 1;
    DBHelper dbHelper = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        scanner_view = findViewById(R.id.scanner_view);
        tv_data = findViewById(R.id.tv_data);
        btn_save = findViewById(R.id.btn_save);
        btn_viewHistory = findViewById(R.id.btn_viewHistory);
        btn_profile = findViewById(R.id.btn_profile);
        checkPermission();
        scanCode();
        scanner_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });
        btn_viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), History.class));
            }
        });
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

//        rv_history.setAdapter();
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss yyyy");
                String customDateString = currentDateTime.format(formatter);
                System.out.println(customDateString);
                System.out.println("DATA "+data);
                if(data!=null){
                    isHistoryAdded = dbHelper.addRecordToHistory(data, customDateString);
                    if(!isHistoryAdded){
                        Toast.makeText(Dashboard.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Dashboard.this, "History Saved", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        codeScanner.stopPreview();
    }

    private void scanCode() {
        codeScanner = new CodeScanner(this, scanner_view);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        data = result.getText().toString();
                        tv_data.setText(data);
                    }
                });
            }

        });
    }

    public void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode!=12){
            checkPermission();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}