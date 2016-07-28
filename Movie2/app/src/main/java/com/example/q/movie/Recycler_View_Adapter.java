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
 * Created by q on 2016-07-25.
 */
public class Recycler_View_Adapter extends RecyclerView.Adapter<Recycler_View_Adapter.View_Holder> {
    private ArrayList<Title_Data> titleData;
    private Context context;

    public Recycler_View_Adapter(Context context, ArrayList<Title_Data> titleData) {
        this.titleData = titleData;
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cinema, null);
        View_Holder holder = new View_Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(View_Holder holder, int i) {
        final String sectionName = titleData.get(i).getTitle();

        ArrayList singleSectionItems = titleData.get(i).getmovieSection();

        holder.movieTitle.setText(sectionName);

        Cinema_View_Adapter itemListDataAdapter = new Cinema_View_Adapter(context, singleSectionItems);

        holder.cinema_view_list.setHasFixedSize(true);
        holder.cinema_view_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.cinema_view_list.setAdapter(itemListDataAdapter);

    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return (null != titleData ? titleData.size() : 0);
    }

    public class View_Holder extends RecyclerView.ViewHolder{
        protected TextView movieTitle;

        protected RecyclerView cinema_view_list;

        public View_Holder(View view) {
            super(view);

            this.movieTitle = (TextView) view.findViewById(R.id.movieTitle);
            this.cinema_view_list = (RecyclerView) view.findViewById(R.id.cinema_view_list);

        }
    }
}

