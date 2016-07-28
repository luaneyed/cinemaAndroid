package com.example.q.movie;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by q on 2016-07-25.
 */
public class Time_Data {
    private String time;

    Time_Data(){}

    Time_Data(String time){
        this.time=time;
    }

    public String getTime(){
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

}
