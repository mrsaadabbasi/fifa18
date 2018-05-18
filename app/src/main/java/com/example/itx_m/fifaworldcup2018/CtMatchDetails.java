package com.example.itx_m.fifaworldcup2018;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;


public class CtMatchDetails extends Activity {

    int matchId;

    private JSONObject matchInfo;
    private JSONObject team1Info;
    private JSONObject team2Info;
    private TextView tvTeam1Name;
    private TextView tvTeam2Name;
    private TextView tvMatchId;
    private TextView tvRound;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvStadiumName;
    private TextView tvWinningTeam;
    private ImageView imageTeam1;
    private ImageView imageTeam2;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        matchId = Integer.parseInt(getIntent().getStringExtra("matchId"));
        try {
            matchInfo = CtApp.jsonFile.getJSONArray("Matches").getJSONObject(matchId);
            team1Info = CtApp.jsonFile.getJSONObject("Teams").getJSONObject(matchInfo.getString("TeamOne"));
            team2Info = CtApp.jsonFile.getJSONObject("Teams").getJSONObject(matchInfo.getString("TeamTwo"));
        } catch (JSONException e) {
            Toast.makeText(CtMatchDetails.this, "Fail to Read", Toast.LENGTH_SHORT).show();
        }
        adView = (AdView) findViewById(R.id.adView);

        tvTeam1Name = (TextView) findViewById(R.id.tvTeam1Name);
        tvTeam2Name = (TextView) findViewById(R.id.tvTeam2Name);
        tvMatchId = (TextView) findViewById(R.id.tvMatchId);
        tvRound = (TextView) findViewById(R.id.tvRound);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvStadiumName = (TextView) findViewById(R.id.tvStadiumName);
        tvWinningTeam = (TextView) findViewById(R.id.tvWinningTeam);
        imageTeam1 = (ImageView) findViewById(R.id.imageViewTeam1);
        imageTeam2 = (ImageView) findViewById(R.id.imageViewTeam2);

//        adView.setVisibility(View.GONE);
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();
//
//        adView.loadAd(adRequest);
//        adView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                adView.setVisibility(View.VISIBLE);
//            }
//        });
        try {
            tvTeam1Name.setText(team1Info.getString("Name"));
            tvTeam2Name.setText(team2Info.getString("Name"));
            tvMatchId.setText("Match " + matchId);
            tvRound.setText(matchInfo.getString("Round"));
            tvDate.setText(matchInfo.getString("Day") + "  " + matchInfo.getString("Date"));
            tvTime.setText(matchInfo.getString("Time"));
            tvStadiumName.setText(matchInfo.getString("Venue"));
            tvWinningTeam.setText(matchInfo.getString("WinningTeam"));

            int id = getResources().getIdentifier(team1Info.getString("Short Name"), "drawable", "com.example.itx_m.fifaworldcup2018");
            Bitmap flag = BitmapFactory.decodeResource(getResources(), id);
            imageTeam1.setImageBitmap(flag);

            id = getResources().getIdentifier(team2Info.getString("Short Name"), "drawable", "com.example.itx_m.fifaworldcup2018");
            flag = BitmapFactory.decodeResource(getResources(), id);
            imageTeam2.setImageBitmap(flag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_match_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void streamMatch(View view) {

    }


}
