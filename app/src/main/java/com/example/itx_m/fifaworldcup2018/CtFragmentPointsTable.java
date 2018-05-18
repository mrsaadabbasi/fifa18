package com.example.itx_m.fifaworldcup2018;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Zohaib on 31/01/2016.
 */
public class CtFragmentPointsTable extends Fragment {
    private CtPointsTableListViewItem listViewAdapter;
    private ArrayList<CtTeam> ctTeamList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_points_table, container, false);
        ctTeamList = (ArrayList<CtTeam>) getArguments().getSerializable("TeamsList");

        TextView poolName = (TextView) v.findViewById(R.id.textViewPoolName);
        ListView listView = (ListView) v.findViewById(R.id.listView);

        poolName.setText(ctTeamList.get(1).pool);
        listViewAdapter = new CtPointsTableListViewItem(getActivity(), ctTeamList);

        listView.setAdapter(listViewAdapter);

        return v;
    }

//    public static FirstFragment newInstance(String text) {
//
//        FirstFragment f = new FirstFragment();
//        Bundle b = new Bundle();
//        b.putString("msg", text);
//
//        f.setArguments(b);
//
//        return f;
//    }
    public void addTeam(CtTeam ctTeam){
        ctTeamList.add(ctTeam);
        listViewAdapter.notifyDataSetChanged();
    }
}
