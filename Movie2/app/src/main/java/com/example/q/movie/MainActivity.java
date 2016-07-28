package com.example.q.movie;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    boolean[] mSelect1;
    boolean[] mSelect2;

    ArrayList<String> movielist = new ArrayList<String>();
    ArrayList<String> cinemalist = new ArrayList<String>();
    CharSequence[] cinemaSeq;
    CharSequence[] movieSeq;
    int year, month, day;
    String mov[];
    String cin[];
    String m, d, y;
    String url = "http://52.78.67.177:8080/plays";
    static String str = null;

    JSONArray jarray = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new mTask().execute();
    }

    private class mTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            String jstr = null;
            try {
                jstr = getJsonFromServer(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            str = jstr;
            return jstr;
        }
        protected void onPostExecute(String jstr) {
            try {
                jarray = new JSONArray(jstr);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);
                    movielist.add(jObject.getString("title"));
                    cinemalist.add(jObject.getString("theater"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            HashSet ml = new HashSet(movielist);
            HashSet cl = new HashSet(cinemalist);
            movielist = new ArrayList(ml);
            cinemalist = new ArrayList(cl);
            cinemaSeq = cinemalist.toArray(new CharSequence[cinemalist.size()]);
            movieSeq = movielist.toArray(new CharSequence[movielist.size()]);
            mSelect1 = new boolean[cinemalist.size()];
            mSelect2 = new boolean[movielist.size()];
            for (int i = 0; i < cinemalist.size(); i++) {
                mSelect1[i] = false;
            }
            for (int i = 0; i < movielist.size(); i++) {
                mSelect2[i] = false;
            }

            mov = new String[movielist.size()];
            for (int i = 0; i < movielist.size(); i++) {
                    mov[i] = movielist.get(i);
            }
            TextView text1 = (TextView) findViewById(R.id.text3);
            text1.setText("< 선택된 영화 >\n\n모든 영화");

            cin = new String[cinemalist.size()];
            for (int i = 0; i < cinemalist.size(); i++) {
                cin[i] = cinemalist.get(i);
            }


            TextView text2 = (TextView) findViewById(R.id.text1);
            text2.setText("< 선택된 극장 >\n\n모든 극장");


            GregorianCalendar calendar = new GregorianCalendar();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            y = ""+year;
            m = ""+(month+1);
            d = ""+day;
            if(day<10){
                d="0"+day;
            }
            if(month+1<10){
                m="0"+(month+1);
            }
            Button btncinema = (Button) findViewById(R.id.cinema);
            btncinema.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("영화관 선택")
                            .setMultiChoiceItems(cinemaSeq, mSelect1, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    mSelect1[which] = isChecked;
                                }
                            })
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    TextView text = (TextView) findViewById(R.id.text1);
                                    String result = "< 선택된 극장 >\n\n";
                                    int a = 0, c = 0;
                                    for (int i = 0; i < mSelect1.length; i++) {
                                        if (mSelect1[i]) {
                                            a++;
                                        }
                                    }
                                    if(a==0) {
                                        result += "없음";
                                    }
                                    else {
                                        for (int i = 0; i < mSelect1.length; i++) {
                                            if (mSelect1[i]) {
                                                if (c == a - 1) {
                                                    result += cinemaSeq[i];
                                                } else {
                                                    result += cinemaSeq[i] + " / ";
                                                }
                                                c++;
                                            }
                                        }
                                    }
                                    cin = new String[a];
                                    int b = a;
                                    for (int i = 0; i < mSelect1.length; i++) {
                                        if (mSelect1[i]) {
                                            cin[b - a] = cinemaSeq[i].toString();
                                            a--;
                                        }
                                    }
                                    text.setText(result);

                                }
                            })
                            .setNegativeButton("취소", null)
                            .show();
                }
            });


            Button btndate = (Button) findViewById(R.id.date);
            btndate.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    new DatePickerDialog(MainActivity.this, dateSetListener, year, month, day).show();
                }
            });


            Button btnmovie = (Button) findViewById(R.id.movie);
            btnmovie.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("영화 선택")
                            .setMultiChoiceItems(movieSeq, mSelect2, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    mSelect2[which] = isChecked;
                                }
                            })
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    TextView text = (TextView) findViewById(R.id.text3);
                                    String result = "< 선택된 영화 >\n\n";
                                    int a = 0, c = 0;
                                    for (int i = 0; i < mSelect2.length; i++) {
                                        if (mSelect2[i]) {
                                            a++;
                                        }
                                    }
                                    if(a==0){
                                        result += "없음";
                                    }
                                    else {
                                        for (int i = 0; i < mSelect2.length; i++) {
                                            if (mSelect2[i]) {
                                                if (c == a - 1) {
                                                    result += movieSeq[i];
                                                } else {
                                                    result += movieSeq[i] + " / ";
                                                }
                                                c++;
                                            }
                                        }
                                    }
                                    mov = new String[a];
                                    int b = a;
                                    for (int i = 0; i < mSelect2.length; i++) {
                                        if (mSelect2[i]) {
                                            mov[b - a] = movieSeq[i].toString();
                                            a--;
                                        }
                                    }
                                    text.setText(result);
                                }
                            })
                            .setNegativeButton("취소", null)
                            .show();
                }

            });


            Button btnreserve = (Button) findViewById(R.id.reserve);
            btnreserve.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ReserveActivity.class);

                    intent.putExtra("mov", mov);
                    intent.putExtra("cin", cin);
                    //intent.putExtra("str", str);
                    intent.putExtra("m", m);
                    intent.putExtra("d", d);
                    intent.putExtra("y", y);
                    startActivity(intent);
                }
            });
        }
    }


    public static String getJsonFromServer(String url) throws IOException {

        BufferedReader inputStream = null;

        URL jsonUrl = new URL(url);
        URLConnection dc = jsonUrl.openConnection();

        inputStream = new BufferedReader(new InputStreamReader(
                dc.getInputStream()));

        // read the JSON results into a string
        String jsonResult = inputStream.readLine();
        return jsonResult;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            m = String.valueOf(monthOfYear + 1);
            if (monthOfYear + 1 < 10) {
                m = "0" + String.valueOf(monthOfYear + 1);
            }
            d = String.valueOf(dayOfMonth);
            if (dayOfMonth < 10) {
                d = "0" + String.valueOf(dayOfMonth);
            }
            y = String.valueOf(year);
            String msg = String.format("%d년 %d월 %d일", year, monthOfYear + 1, dayOfMonth);
            TextView text = (TextView) findViewById(R.id.text2);
            text.setText(msg);
        }
    };
}
