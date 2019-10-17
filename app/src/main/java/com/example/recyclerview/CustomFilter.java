package com.example.recyclerview;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {
    ArrayList<Model> filterList;
    MyAdapter adapter;

    public CustomFilter(ArrayList<Model> filterList, MyAdapter adapter) {
        this.filterList = filterList;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length()>0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<Model> filterModels = new ArrayList<>();
            for (int i = 0; i<filterList.size(); i++){
                if (filterList.get(i).getTitle().toUpperCase().contains(constraint)){
                    filterModels.add (filterList.get(i));
                }
            }
            results.count =filterModels.size();
            results.values = filterModels;
        }
        else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.models = (ArrayList<Model>) results.values;
        adapter.notifyDataSetChanged();

    }
}
