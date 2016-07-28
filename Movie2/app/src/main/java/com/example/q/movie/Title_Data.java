package com.example.q.movie;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by q on 2016-07-25.
 */
public class Title_Data {
    private String title;
    private ArrayList<Cinema_Data> movieSection;

    Title_Data(){}

    Title_Data(String title, ArrayList<Cinema_Data> movieSection){
        this.title=title;
        this.movieSection=movieSection;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Cinema_Data> getmovieSection() {
        return movieSection;
    }

    public void setmovieSection(ArrayList<Cinema_Data> movieSection) {
        this.movieSection = movieSection;
    }

}
