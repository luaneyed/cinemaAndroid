package com.example.q.movie;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by q on 2016-07-26.
 */
public class Cinema_View_Adapter extends RecyclerView.Adapter<Cinema_View_Adapter.View_Holder>{
    private ArrayList<Cinema_Data> cinemaData;
    private Context context;

    public Cinema_View_Adapter(Context context, ArrayList<Cinema_Data> cinemaData) {
        this.cinemaData = cinemaData;
        this.context = context;
    }
    @Override
    public View_Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_time, viewGroup,false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }
    @Override
    public void onBindViewHolder(View_Holder holder, int i) {
        final String sectionName = cinemaData.get(i).getCinema();

        ArrayList singleSectionItems = cinemaData.get(i).getcinemaSection();

        holder.cinemaName.setText(sectionName);

        Time_View_Adapter itemListDataAdapter = new Time_View_Adapter(context, singleSectionItems);

        holder.time_view_list.setHasFixedSize(true);
        holder.time_view_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.time_view_list.setAdapter(itemListDataAdapter);

    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return (null != cinemaData ? cinemaData.size() : 0);
    }

    public class View_Holder extends RecyclerView.ViewHolder{
        protected TextView cinemaName;

        protected RecyclerView time_view_list;

        public View_Holder(View view) {
            super(view);

            this.cinemaName = (TextView) view.findViewById(R.id.cinemaName);
            this.time_view_list = (RecyclerView) view.findViewById(R.id.time_view_list);

        }
    }
}

