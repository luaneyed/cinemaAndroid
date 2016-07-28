package com.example.q.movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by q on 2016-07-26.
 */
public class Time_View_Adapter extends RecyclerView.Adapter<Time_View_Adapter.View_Holder>{


        private ArrayList<Time_Data> timeData;
        private Context context;

        public Time_View_Adapter(Context context, ArrayList<Time_Data> itemsList) {
            this.timeData = itemsList;
            this.context = context;
        }

        @Override
        public View_Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_single, null);
            View_Holder holder = new View_Holder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(View_Holder holder, int i) {

            Time_Data singleItem = timeData.get(i);

            holder.timeTable.setText(singleItem.getTime());

        }

        @Override
        public int getItemCount() {
            return (null != timeData ? timeData.size() : 0);
        }

        public class View_Holder extends RecyclerView.ViewHolder {

            protected TextView timeTable;

            public View_Holder(View view) {
                super(view);

                this.timeTable = (TextView) view.findViewById(R.id.timeTable);

            }

        }

    }


