package com.example.qrcodescanner;

public class uploadInformation {

    public String time;
    public String info;
    public long epochtime;


    public uploadInformation(){
    }

    public long getEpochtime() {
        return epochtime;
    }

    public void setEpochtime(long epochtime) {
        this.epochtime = epochtime;
    }

    public uploadInformation(String time, String info, long epochtime) {
        this.time = time;
        this.info = info;
        this.epochtime = epochtime;
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
