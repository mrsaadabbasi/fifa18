package com.example.itx_m.fifaworldcup2018;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Zohaib on 31/01/2015.
 */
public class CtPointsTableListViewItem extends BaseAdapter implements Filterable{

    private final Activity context;
    private ArrayList<CtTeam> ctTeam1List;

    private final ArrayList<CtTeam> filteredCtTeam1List;

    private ValueFilter valueFilter;
    private LayoutInflater mInflater;

    public CtPointsTableListViewItem(Activity context, ArrayList<CtTeam> ctTeam1List) {
        this.context = context;
        Collections.sort(ctTeam1List, new Comparator<CtTeam>() {
            public int compare(CtTeam ctTeam1, CtTeam ctTeam2) {
                return Integer.parseInt(ctTeam2.points) - Integer.parseInt(ctTeam1.points);
            }
        });
        this.ctTeam1List = ctTeam1List;

        filteredCtTeam1List = ctTeam1List;

        mInflater = LayoutInflater.from(context);

        getFilter();
    }

    @Override
    public int getCount() {
        return ctTeam1List.size();
    }

    @Override
    public Object getItem(int position) {
        return ctTeam1List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        Holder viewHolder;

        if (view == null) {

            viewHolder = new Holder();

            view = mInflater.inflate(R.layout.points_table_list_item, null);

            viewHolder.name = (TextView) view.findViewById(R.id.textViewName);
            viewHolder.totalMatches = (TextView) view.findViewById(R.id.textViewTotalMatches);
            viewHolder.wonMatches = (TextView) view.findViewById(R.id.textViewMatchesWon);
            viewHolder.lostMatches = (TextView) view.findViewById(R.id.textViewMatchesLost);
            viewHolder.totalPoints = (TextView) view.findViewById(R.id.textViewTotalPoints);
            viewHolder.view = view;

            view.setTag(viewHolder);

        }

        else {

            viewHolder = (Holder) view.getTag();
        }

        viewHolder.name.setText(ctTeam1List.get(position).name);
        viewHolder.totalMatches.setText(ctTeam1List.get(position).totalMatches);
        viewHolder.wonMatches.setText(ctTeam1List.get(position).won);
        viewHolder.lostMatches.setText(ctTeam1List.get(position).lost);
        viewHolder.totalPoints.setText(ctTeam1List.get(position).points);
        if (ctTeam1List.get(position).status.equals("X"))
            viewHolder.view.setBackgroundColor(Color.parseColor("#90B40404"));
        else
            viewHolder.view.setBackgroundColor(Color.TRANSPARENT);
        return view;
    }

    //Returns a filter that can be used to constrain data with a filtering pattern.
    @Override
    public Filter getFilter() {

        if (valueFilter == null) {

            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }


    private class ValueFilter extends Filter {


        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            CustomListItem customListItem = new CustomListItem();

            if (constraint != null && constraint.length() > 0) {

                for (int i = 0; i < filteredCtTeam1List.size(); i++) {

                    if (filteredCtTeam1List.get(i).name.toLowerCase().contains(constraint)) {

                        customListItem.filteredListCtTeam1.add(filteredCtTeam1List.get(i));

                    }
                }


                results.count = filteredCtTeam1List.size();

                results.values = customListItem;

            } else {
                customListItem.filteredListCtTeam1.addAll(filteredCtTeam1List);

                results.count = ctTeam1List.size();

                results.values = customListItem;

            }

            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            CustomListItem resultList = (CustomListItem) results.values;
            ctTeam1List = resultList.filteredListCtTeam1;

            notifyDataSetChanged();


        }

    }

    private class CustomListItem{

        ArrayList<CtTeam> filteredListCtTeam1;

        public CustomListItem(){
            filteredListCtTeam1 = new ArrayList<>();
        }

    }

    private class Holder {
        TextView name;
        TextView totalMatches;
        TextView wonMatches;
        TextView lostMatches;
        TextView totalPoints;
        public View view;
    }

}
