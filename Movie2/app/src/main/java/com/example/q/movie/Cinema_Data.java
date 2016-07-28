package com.example.q.movie;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by q on 2016-07-25.
 */
public class Cinema_Data {
    private String cinema;
    private ArrayList<Time_Data> cinemaSection;

    Cinema_Data(){}

    Cinema_Data(String cinema, ArrayList<Time_Data> cinemaSection){
        this.cinema=cinema;
        this.cinemaSection=cinemaSection;
    }

    public String getCinema(){
        return cinema;
    }
    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public ArrayList<Time_Data> getcinemaSection() {
        return cinemaSection;
    }

    public void setcinemaSection(ArrayList<Time_Data> cinemaSection) {
        this.cinemaSection = cinemaSection;
    }

}
