package com.example.itx_m.fifaworldcup2018;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CtPointsTable extends AppCompatActivity {

    HashMap<String, List<CtTeam>> sortedTeams;
    ArrayList<String> pools;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_table);
        adView = (AdView) findViewById(R.id.adView);
        sortedTeams = new HashMap<>();
        pools = new ArrayList<>();
        separateTeamsOnPool();

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        /** Getting fragment manager */
        FragmentManager fm = getSupportFragmentManager();

        /** Instantiating FragmentPagerAdapter */
        CtCustomFragmentPagerAdapter pagerAdapter = new CtCustomFragmentPagerAdapter(fm);
        Bundle data = new Bundle();
        data.putSerializable("SortedTeams", sortedTeams);
        data.putStringArrayList("Pools", pools);
        pagerAdapter.setBundle(data);

        /** Setting the pagerAdapter to the pager object */
        pager.setAdapter(pagerAdapter);
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
    }

    private void separateTeamsOnPool() {
        String currentlyBeingSortedPool;
        try {
            JSONObject teams = CtApp.jsonFile.getJSONObject("Teams");
            ArrayList<CtTeam> ctTeamList;
            for (int x = 1; x <= teams.length() - 3; x++) {
                ctTeamList = new ArrayList<>();
                currentlyBeingSortedPool = teams.getJSONObject("t" + x).getString("Group");
                if (sortedTeams.containsKey(currentlyBeingSortedPool))
                    continue;
                pools.add(currentlyBeingSortedPool);
                ctTeamList.add(jsonObjectToTeamObject(teams.getJSONObject("t" + x)));
                for (int y = x + 1; y <= teams.length() - 3; y++) {
                    if (teams.getJSONObject("t" + y).getString("Group").equals(currentlyBeingSortedPool))
                        ctTeamList.add(jsonObjectToTeamObject(teams.getJSONObject("t" + y)));
                }

                sortedTeams.put(currentlyBeingSortedPool, ctTeamList);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CtTeam jsonObjectToTeamObject(JSONObject object) {
        CtTeam ctTeam = new CtTeam();
        try {
            ctTeam.name = object.getString("Name");
            ctTeam.shortName = object.getString("Short Name");
            ctTeam.totalMatches = object.getString("Total matches");
            ctTeam.points = object.getString("Points");
            ctTeam.won = object.getString("Won");
            ctTeam.lost = object.getString("Lost");
            ctTeam.status = object.getString("Status");
            ctTeam.pool = object.getString("Group");
        } catch (JSONException e) {
            ctTeam = null;
        }
        return ctTeam;
    }
}
