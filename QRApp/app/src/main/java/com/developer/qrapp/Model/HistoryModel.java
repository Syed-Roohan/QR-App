package com.developer.qrapp.Model;

public class HistoryModel {
    String id, Data, Time;

//    public HistoryModel(String data, String time) {
//        Data = data;
//        Time = time;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public HistoryModel() {
    }

    public HistoryModel(String id, String data, String time) {
        this.id = id;
        Data = data;
        Time = time;
    }
}
