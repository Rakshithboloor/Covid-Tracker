package com.example.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Affected_countries extends AppCompatActivity {
    EditText search;
    ListView lv;
    SimpleArcLoader smloader;
    public static List<ContryModel> cm = new ArrayList<>();
    ContryModel contryModel;
    MyCustomAdapter myCustomAdapterl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);
        search = (EditText) findViewById(R.id.search);
        lv = (ListView) findViewById(R.id.list);
        smloader = findViewById(R.id.loader);
        getSupportActionBar().setTitle("Affected Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        fetchdata();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(getApplicationContext(),Country_details.class).putExtra("position",position));
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                myCustomAdapterl.getFilter().filter(charSequence);
                myCustomAdapterl.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchdata() {
        String url = "https://disease.sh/v2/Countries";
        smloader.start();
        StringRequest strreq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            String country_name=jsonObject.getString("country");
                        String countryName = jsonObject.getString("country");
                        String cases = jsonObject.getString("cases");
                        String todayCases = jsonObject.getString("todayCases");
                        String deaths = jsonObject.getString("deaths");
                        String todayDeaths = jsonObject.getString("todayDeaths");
                        String recovered = jsonObject.getString("recovered");
                        String active = jsonObject.getString("active");
                        String critical = jsonObject.getString("critical");
                        JSONObject flag1=jsonObject.getJSONObject("countryInfo");
                        String flag=flag1.getString("flag");
                        Log.i("active",": "+active);
                         contryModel=new ContryModel(flag,countryName,cases,todayCases,deaths,todayDeaths,recovered,active,critical);

                        cm.add(contryModel);

                    }
                    myCustomAdapterl=new MyCustomAdapter(Affected_countries.this,cm);
                    lv.setAdapter(myCustomAdapterl);
                    smloader.stop();
                    smloader.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    smloader.stop();
                    smloader.setVisibility(View.GONE);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                smloader.stop();
                smloader.setVisibility(View.GONE);
                Toast.makeText(Affected_countries.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(strreq);
    }

}