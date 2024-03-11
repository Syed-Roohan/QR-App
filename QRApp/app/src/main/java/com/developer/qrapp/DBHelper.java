package com.developer.qrapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.developer.qrapp.Model.UserModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DB_NAME = "QRCODE";
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String UserTable = "CREATE TABLE USERS(id INTEGER PRIMARY KEY, Name TEXT, Email TEXT, Phone TEXT, Password TEXT)";
        String HistoryTable = "CREATE TABLE HISTORY(id INTEGER PRIMARY KEY, Data TEXT, Time TEXT)";
        db.execSQL(UserTable);
        db.execSQL(HistoryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS HISTORY");
        onCreate(db);
    }


    public boolean isUserIsRegistered(String name, String email, String phone, String pass) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("Name", name);
        data.put("Email", email);
        data.put("Phone", phone);
        data.put("Password", pass);
        Long l = sqLiteDatabase.insert("USERS", null, data);
        sqLiteDatabase.close();
        if (l > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUserLoggedIn(String email, String pass) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM USERS WHERE Email='" + email + "' AND Password='" + pass + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            // User is LoggedIn
            return true;

        } else {
            // User is  Not LoggedIn
            return false;
        }
    }
    public Cursor displayUserData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM USERS ";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public ArrayList<UserModel> getLoggedInUserDetails(String email){
        ArrayList<UserModel> userModels = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM USERS WHERE Email='" + email + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(1);
            String user_email = cursor.getString(2);
            String phone = cursor.getString(3);
            userModels.add(new UserModel(name, user_email, phone));
        }
        return userModels;
    }
    public boolean updateUserDetails(String name, String email, String phone){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("Name", name);
        data.put("Email", email);
        data.put("Phone", phone);
        String selection = "email = ?";
        String[] selectionArgs = {email};
        int count = db.update("USERS", data, selection, selectionArgs);
        db.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
//    ------------------------------ History


    public boolean addRecordToHistory(String Data, String time) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("Data", Data);
        data.put("Time", time);
        Long l = sqLiteDatabase.insert("HISTORY", null, data);
        sqLiteDatabase.close();
        if (l > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor viewAllHistoryRecords() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM HISTORY";
        return sqLiteDatabase.rawQuery(query, null);
    }
    public boolean deleteHistory(String data) {
        SQLiteDatabase db = getWritableDatabase();
        long l = db.delete("HISTORY", "Data=?", new String[]{String.valueOf(data)});
        db.close();
        if (l > 0) {
            return true;
        } else {
            return false;
        }
    }
}

