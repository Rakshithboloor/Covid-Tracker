package com.example.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class Country_details extends AppCompatActivity {
    private  int positionCountry;
    TextView tvCountry,tvCases,tvRecovered,tvCritical,tvActive,tvTodayCases,tvTotalDeaths,tvTodayDeaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Details of "+Affected_countries.cm.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvCountry =(TextView) findViewById(R.id.country);
        tvCases =(TextView) findViewById(R.id.cases);
        tvRecovered =(TextView) findViewById(R.id.recovered);
        tvCritical =(TextView) findViewById(R.id.critical);
        tvActive =(TextView) findViewById(R.id.active);
        tvTodayCases =(TextView) findViewById(R.id.todayCases);
        tvTotalDeaths =(TextView) findViewById(R.id.deaths);
        tvTodayDeaths =(TextView) findViewById(R.id.todayDeaths);

        tvCountry.setText(Affected_countries.cm.get(positionCountry).getCountry());
        tvCases.setText(Affected_countries.cm.get(positionCountry).getCases());
        tvRecovered.setText(Affected_countries.cm.get(positionCountry).getRecovered());
        tvCritical.setText(Affected_countries.cm.get(positionCountry).getCritical());
        tvActive.setText(Affected_countries.cm.get(positionCountry).getActive());
        tvTodayCases.setText(Affected_countries.cm.get(positionCountry).getTodayCases());
        tvTotalDeaths.setText(Affected_countries.cm.get(positionCountry).getDeaths());
        tvTodayDeaths.setText(Affected_countries.cm.get(positionCountry).getTodayDeaths());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
