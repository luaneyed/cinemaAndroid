package com.example.q.movie;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ReserveActivity extends AppCompatActivity {
    String mov[];
    String cin[];
    String m, d, y, str;
    JSONArray newjarray;

    ArrayList<Title_Data> allSampleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mov = extras.getStringArray("mov");
            cin = extras.getStringArray("cin");
            m = extras.getString("m");
            d = extras.getString("d");
            y = extras.getString("y");
            str = MainActivity.str;
            //str = extras.getString("str");
        }
        TextView t = (TextView) findViewById(R.id.today);
        t.setText(y+" / "+m+" / "+d);

        allSampleData = new ArrayList<Title_Data>();

        Button yesterday = (Button)findViewById(R.id.yesterday);
        yesterday.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                if(d.equals("01")) {
                    if(m.equals("01")){
                        y=String.valueOf(Integer.parseInt(y)-1);
                        m=String.valueOf(12);
                        d=String.valueOf(31);
                    }
                    else if(m.equals("03")){
                        m=String.valueOf(02);
                        d=String.valueOf(28);
                    }
                    else if(m.equals("02")||m.equals("04")||m.equals("06")||m.equals("08")||m.equals("09")||m.equals("11")){
                        if(Integer.parseInt(m)-1<10) {
                            m = "0"+String.valueOf(Integer.parseInt(m) - 1);
                        }
                        else{
                            m = String.valueOf(Integer.parseInt(m) - 1);
                        }
                        d=String.valueOf(31);
                    }
                    else{
                        if(Integer.parseInt(m)-1<10) {
                            m = "0"+String.valueOf(Integer.parseInt(m) - 1);
                        }
                        else{
                            m = String.valueOf(Integer.parseInt(m) - 1);
                        }
                        d=String.valueOf(30);
                    }
                }
                else{
                    if(Integer.parseInt(d)-1<10) {
                        d = "0"+String.valueOf(Integer.parseInt(d) - 1);
                    }
                    else{
                        d = String.valueOf(Integer.parseInt(d) - 1);
                    }
                }
                TextView t = (TextView) findViewById(R.id.today);
                t.setText(y+" / "+m+" / "+d);

                allSampleData = new ArrayList<Title_Data>();

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

                recyclerView.setHasFixedSize(true);

                Recycler_View_Adapter cinemaadapter = new Recycler_View_Adapter(ReserveActivity.this, allSampleData);

                recyclerView.setLayoutManager(new LinearLayoutManager(ReserveActivity.this, LinearLayoutManager.VERTICAL, false));

                recyclerView.setAdapter(cinemaadapter);
                createDummyData();
            }
        });

        Button tomorrow = (Button)findViewById(R.id.tomorrow);
        tomorrow.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                if(m.equals("02")&&d.equals("28")) {
                    m = "03";
                    d ="01";

                }
                else if((m.equals("01")||m.equals("03")||m.equals("05")||m.equals("07")||m.equals("08")||m.equals("10")||m.equals("12"))&&d.equals("31")){
                    d="01";
                    if(Integer.parseInt(m)+1<10) {
                        m = "0"+String.valueOf(Integer.parseInt(m) + 1);
                    }
                    else{
                        m = String.valueOf(Integer.parseInt(m) + 1);
                    }
                }
                else if((m.equals("04")||m.equals("06")||m.equals("09")||m.equals("11"))&&d.equals("30")){
                    d="01";
                    if(Integer.parseInt(m)+1<10) {
                        m = "0"+String.valueOf(Integer.parseInt(m) + 1);
                    }
                    else{
                        m = String.valueOf(Integer.parseInt(m) + 1);
                    }
                }
                else {
                    if (Integer.parseInt(d) + 1 < 10) {
                        d = "0" + String.valueOf(Integer.parseInt(d) + 1);
                    } else {
                        d = String.valueOf(Integer.parseInt(d) + 1);
                    }
                }
                TextView t = (TextView) findViewById(R.id.today);
                t.setText(y+" / "+m+" / "+d);

                allSampleData = new ArrayList<Title_Data>();

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

                recyclerView.setHasFixedSize(true);

                Recycler_View_Adapter cinemaadapter = new Recycler_View_Adapter(ReserveActivity.this, allSampleData);

                recyclerView.setLayoutManager(new LinearLayoutManager(ReserveActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(cinemaadapter);

                createDummyData();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);

        Recycler_View_Adapter cinemaadapter = new Recycler_View_Adapter(this, allSampleData);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(cinemaadapter);

        createDummyData();
    }


    public void createDummyData() {
        TextView noresult = (TextView) findViewById(R.id.noresult);
        noresult.setVisibility(View.GONE);
        try {
            JSONArray jarray = new JSONArray(str);
            newjarray = new JSONArray();
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);
                for (int j = 0; j < mov.length; j++) {
                    if (jObject.getString("title").equals(mov[j])) {
                        for (int k = 0; k < cin.length; k++) {
                            if (jObject.getString("theater").equals(cin[k]) && jObject.getString("day").equals(d) && jObject.getString("month").equals(m)) {
                                newjarray.put(jObject);
                            }
                        }

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<String> first = new ArrayList<String>();
        ArrayList<ArrayList<String>> second = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<ArrayList<String>>> third = new ArrayList<ArrayList<ArrayList<String>>>();

        try {
            for (int i = 0; i < newjarray.length(); i++) {
                JSONObject jObject = newjarray.getJSONObject(i);
                first.add(jObject.getString("title") + " : " + jObject.getString("option") + " - " + jObject.getString("age"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashSet f = new HashSet(first);
        first = new ArrayList(f);

        try {
            for (int i = 0; i < first.size(); i++) {
                ArrayList<String> ChildList = new ArrayList<String>();
                for (int j = 0; j < newjarray.length(); j++) {
                    JSONObject jObject = newjarray.getJSONObject(j);
                    if ((jObject.getString("title") + " : " + jObject.getString("option") + " - " + jObject.getString("age")).equals(first.get(i))) {
                        ChildList.add(jObject.getString("theater") + " _ " + jObject.getString("screen"));
                    }
                }
                HashSet s = new HashSet(ChildList);
                ChildList = new ArrayList(s);
                second.add(ChildList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < first.size(); i++) {
                ArrayList<ArrayList<String>> ChildList1 = new ArrayList<ArrayList<String>>();
                for (int j = 0; j < second.get(i).size(); j++) {
                    ArrayList<String> ChildList2 = new ArrayList<String>();
                    for (int k = 0; k < newjarray.length(); k++) {
                        JSONObject jObject = newjarray.getJSONObject(k);
                        if ((jObject.getString("title") + " : " + jObject.getString("option") + " - " + jObject.getString("age")).equals(first.get(i))) {
                            if ((jObject.getString("theater") + " _ " + jObject.getString("screen")).equals(second.get(i).get(j))) {
                                ChildList2.add(jObject.getString("startTime") + " ~ " + jObject.getString("endTime") + " ( 잔여석" + jObject.getString("leftSeat") + " )");
                            }
                        }
                    }
                    HashSet t = new HashSet(ChildList2);
                    ChildList2 = new ArrayList(t);
                    ChildList1.add(ChildList2);
                }
                HashSet t = new HashSet(ChildList1);
                ChildList1 = new ArrayList(t);
                third.add(ChildList1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (newjarray.length() == 0) {
            noresult.setVisibility(View.VISIBLE);
            Title_Data td = new Title_Data();
            td.setTitle("결과가 없습니다.");
            allSampleData.add(td);
        }
        else {
            for (int i = 0; i < first.size(); i++) {
                Title_Data td = new Title_Data();
                td.setTitle(first.get(i));
                ArrayList<Cinema_Data> cinemaData = new ArrayList<Cinema_Data>();
                for (int j = 0; j < second.get(i).size(); j++) {
                    Cinema_Data cd = new Cinema_Data();
                    cd.setCinema(second.get(i).get(j));
                    ArrayList<Time_Data> timeData = new ArrayList<Time_Data>();
                    for (int k = 0; k < third.get(i).get(j).size(); k++) {
                        timeData.add(new Time_Data(third.get(i).get(j).get(k)));
                    }

                    cd.setcinemaSection(timeData);
                    cinemaData.add(cd);
                }

                td.setmovieSection(cinemaData);
                allSampleData.add(td);
            }
        }
    }
}


