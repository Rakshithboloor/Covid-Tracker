package com.example.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyCustomAdapter extends ArrayAdapter<ContryModel> {

    private Context context;
    private List<ContryModel> CountryModelLIs;
    private List<ContryModel> countryModelsListFiltered;


    public MyCustomAdapter(Context context, List<ContryModel> CountryModelLIs) {
        super(context, R.layout.list_customitem, CountryModelLIs);
        this.context = context;
        this.CountryModelLIs = CountryModelLIs;
        this.countryModelsListFiltered = CountryModelLIs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_customitem, null, true);
        TextView tx = view.findViewById(R.id.countryname);
        ImageView img = view.findViewById(R.id.imageflg);
        tx.setText(countryModelsListFiltered.get(position).getCountry());
        Glide.with(context).load(countryModelsListFiltered.get(position).getFlag()).into(img);

        return view;
    }

    @Override
    public int getCount() {
        return countryModelsListFiltered.size();
    }

    @Nullable
    @Override
    public ContryModel getItem(int position) {
        return countryModelsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = CountryModelLIs.size();
                    filterResults.values = CountryModelLIs ;

                }else{
                    List<ContryModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(ContryModel itemsModel:CountryModelLIs){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModelsListFiltered = (List<ContryModel>) results.values;
                Affected_countries.cm = (List<ContryModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}