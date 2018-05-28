package com.example.itx_m.fifaworldcup2018;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.itx_m.fifaworldcup2018.R;

/**
 * Created by Zohaib on 31/01/2015.
 */
public class CtMatchesListViewItem extends BaseAdapter implements Filterable{

    private final Activity context;
    private ArrayList<String> team1List;
    private ArrayList<String> team2List;
    private ArrayList<String> dateList;
    private ArrayList<String> matchList;
    private ArrayList<Integer> matchesId;

    private final ArrayList<String> filteredTeam1List, filteredTeam2List, filteredDateList;
    private final ArrayList<Integer> filteredMatchesId;

    private ValueFilter valueFilter;
    private LayoutInflater mInflater;

    public CtMatchesListViewItem(Activity context, ArrayList<String> team1List, ArrayList<String> team2List, ArrayList<Integer> matchesId, ArrayList<String> dateList) {
        this.context = context;
        this.team1List = team1List;
        this.team2List = team2List;
        this.matchesId = matchesId;
        this.dateList = dateList;

        filteredTeam1List = team1List;
        filteredTeam2List = team2List;
        filteredMatchesId = matchesId;
        filteredDateList = dateList;

        mInflater = LayoutInflater.from(context);

        getFilter();
    }

    @Override
    public int getCount() {
        return team2List.size();
    }

    @Override
    public Object getItem(int position) {
        return team1List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        Holder viewHolder;

        if (view == null) {

            viewHolder = new Holder();

            view = mInflater.inflate(R.layout.matches_list_item, null);

            viewHolder.team1Name = (TextView) view.findViewById(R.id.team1_name);
            viewHolder.team2Name = (TextView) view.findViewById(R.id.team2_name);
            viewHolder.matchDate = (TextView) view.findViewById(R.id.date);
            view.setTag(viewHolder);

        }

        else {

            viewHolder = (Holder) view.getTag();
        }

        viewHolder.team1Name.setText(team1List.get(position));
        viewHolder.team2Name.setText(team2List.get(position));
        viewHolder.matchDate.setText(dateList.get(position));
        view.setContentDescription(matchesId.get(position)+"");

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
            constraint = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            CustomListItem customListItem = new CustomListItem();

            if (constraint != null && constraint.length() > 0) {

                for (int i = 0; i < filteredTeam1List.size(); i++) {

                    if (filteredTeam1List.get(i).toLowerCase().contains(constraint)||filteredTeam2List.get(i).toLowerCase().contains(constraint)) {

                        customListItem.filteredListTeam1.add(filteredTeam1List.get(i));
                        customListItem.filteredListTeam2.add(filteredTeam2List.get(i));
                        customListItem.filteredDateList.add(filteredDateList.get(i));
                        customListItem.filteredListMatchesId.add(filteredMatchesId.get(i));

                    }
                }


                results.count = filteredTeam1List.size();

                results.values = customListItem;

            } else {
                customListItem.filteredListTeam1.addAll(filteredTeam1List);
                customListItem.filteredListTeam2 = filteredTeam2List;
                customListItem.filteredListMatchesId = filteredMatchesId;
                customListItem.filteredDateList = filteredDateList;

                results.count = team2List.size();

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
            team1List = resultList.filteredListTeam1;
            team2List = resultList.filteredListTeam2;
            matchesId = resultList.filteredListMatchesId;
            dateList = resultList.filteredDateList;

            notifyDataSetChanged();


        }

    }

    private class CustomListItem{

        ArrayList<String> filteredListTeam1;
        ArrayList<String> filteredListTeam2;
        ArrayList<Integer> filteredListMatchesId;
        ArrayList<String> filteredDateList;

        public CustomListItem(){
            filteredListTeam1 = new ArrayList<String>();
            filteredListTeam2 = new ArrayList<String>();
            filteredDateList = new ArrayList<String>();
            filteredListMatchesId = new ArrayList<>();
        }

    }

    private class Holder {
        TextView team1Name;
        TextView team2Name;
        TextView matchDate;
    }

}
