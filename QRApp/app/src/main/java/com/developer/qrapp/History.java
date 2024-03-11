package com.developer.qrapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.developer.qrapp.Adapter.HistoryAdapter;
import com.developer.qrapp.Model.HistoryModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class History extends AppCompatActivity {

    ImageView btn_back;
    RecyclerView rv_history;
    ArrayList<HistoryModel> historyModels = new ArrayList<>();
    DBHelper dbHelper = new DBHelper(this);
    String data, time;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        btn_back = findViewById(R.id.btn_back);
        rv_history = findViewById(R.id.rv_history);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        historyModels.clear();
        Cursor cursor = dbHelper.viewAllHistoryRecords();
        while (cursor.moveToNext()){
            System.out.println("CUR "+cursor.getString(0));
            HistoryModel historyModel = new HistoryModel(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            historyModels.add(historyModel);
        }
        HistoryAdapter adapter = new HistoryAdapter(this, historyModels, dbHelper);
        rv_history.setLayoutManager(new LinearLayoutManager(this));
        rv_history.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}