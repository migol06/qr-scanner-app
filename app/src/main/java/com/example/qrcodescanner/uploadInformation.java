package com.example.qrcodescanner;

public class uploadInformation {

    public String time;
    public String info;


    public uploadInformation(){
    }

    public uploadInformation(String time, String info) {
        this.time = time;
        this.info = info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
