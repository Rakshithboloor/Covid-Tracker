package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView recovered, tvCases, Critical, Active, TotalCases, Totaldeath;
    SimpleArcLoader simplearcloader;
    ScrollView scrollview;
    PieChart piechar;


    public void goTrackContries(View v) {
        startActivity(new Intent(getApplicationContext(),Affected_countries.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simplearcloader = (SimpleArcLoader) findViewById(com.leo.simplearcloader.R.id.loader);
        scrollview=(ScrollView) findViewById(R.id.scrollStats) ;
        scrollview.setVisibility(View.GONE);
        simplearcloader.setVisibility(View.VISIBLE);
        //text
        recovered = (TextView) findViewById(R.id.recovered);
        tvCases = (TextView) findViewById(R.id.tvCases);
        Critical = (TextView) findViewById(R.id.Critical);
        Active = (TextView) findViewById(R.id.Active);
        TotalCases = (TextView) findViewById(R.id.TotalCases);
        Totaldeath = (TextView) findViewById(R.id.Totaldeath);
        piechar = (PieChart) findViewById(R.id.piechart);
        //arcloader


        scrollview = (ScrollView) findViewById(R.id.scrollStats);
        fetchdata();

    }

    private void fetchdata() {
        String url = "https://disease.sh/v2/all";
        simplearcloader.start();
        StringRequest strreq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject resp=new JSONObject(response.toString());
                    tvCases.setText(resp.getString("todayCases"));
                    recovered.setText(resp.getString("todayRecovered"));
                    Critical.setText(resp.getString("critical"));
                    Active.setText(resp.getString("active"));
                    TotalCases.setText(resp.getString("cases"));
                    Totaldeath.setText(resp.getString("todayDeaths"));
                    piechar.addPieSlice(new PieModel("cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                    piechar.addPieSlice(new PieModel("recovered",Integer.parseInt(recovered.getText().toString()), Color.parseColor("#66BB6A")));
                    piechar.addPieSlice(new PieModel("death",Integer.parseInt(Totaldeath.getText().toString()), Color.parseColor("#29B6F6")));
                 simplearcloader.stop();
                    simplearcloader.setVisibility(View.GONE);

                    scrollview.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(strreq);
    }
}