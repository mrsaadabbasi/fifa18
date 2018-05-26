package com.example.itx_m.fifaworldcup2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CtFixturesActivity extends AppCompatActivity {

    private EditText searchBar;
    private TextWatcher filterTextWatcher;
    private AdView adView;
    CtMatchesListViewItem ctMatchesListViewItem;
    private ListView matchesListView;
    private JSONObject obj;
    private ArrayList<String> team1List;
    private ArrayList<String> team2List;
    private ArrayList<String> dateList;
    private ArrayList<Integer> matchesId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);
        adView = (AdView)findViewById(R.id.adView);
        initializeArrrayData();
        adView.setVisibility(View.GONE);
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
            }
        });
        ctMatchesListViewItem = new CtMatchesListViewItem(CtFixturesActivity.this, team1List, team2List, matchesId, dateList);

        matchesListView = (ListView) findViewById(R.id.listViewMatches);
        searchBar = (EditText) findViewById(R.id.editTextSearchBar);

        matchesListView.setAdapter(ctMatchesListViewItem);

        matchesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("android.intent.action.CTMATCHDETAILS");
                intent.putExtra("matchId", view.getContentDescription().toString());
                startActivity(intent);
            }
        });

        filterTextWatcher = new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                ctMatchesListViewItem.getFilter().filter(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

        };

        searchBar.addTextChangedListener(filterTextWatcher);

    }

    private void initializeArrrayData() {
        JSONArray matches = new JSONArray();

        try {
            obj = CtApp.jsonFile;
            matches = obj.getJSONArray("Matches");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        team1List = new ArrayList<>();
        team2List = new ArrayList<>();
        matchesId = new ArrayList<>();
        dateList = new ArrayList<>();

        for (int count = 1; count <= matches.length(); count++) {

            try {
                matchesId.add(count);
                String t1 = matches.getJSONObject(count).getString("TeamOne");
                String t2 = matches.getJSONObject(count).getString("TeamTwo");
                team1List.add(obj.getJSONObject("Teams").getJSONObject(t1).get("Name").toString());
                team2List.add(obj.getJSONObject("Teams").getJSONObject(t2).get("Name").toString());
                dateList.add(matches.getJSONObject(count).getString("Date"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
