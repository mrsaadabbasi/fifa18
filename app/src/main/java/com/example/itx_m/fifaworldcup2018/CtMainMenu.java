package com.example.itx_m.fifaworldcup2018;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CtMainMenu extends Activity {

    private InterstitialAd mInterstitialAd;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        adView = (AdView) findViewById(R.id.adView);
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
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-9842282663786907/3574230474");
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                // Call displayInterstitial() function
//                displayInterstitial();
//            }
//
//        });
//        NotifyLogic();

        if(savedInstanceState == null){
            try {

                CtApp.jsonFile = getJSONData();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(CtWifiUtils.isNetworkAvailable(this)){
                new CtJSONUpdateAsyncTask(this).execute();
            }else{
                Toast.makeText(getApplicationContext(), "Please connect to internet for updated results" ,
                        Toast.LENGTH_LONG).show();
            }
          //  requestNewInterstitial();
        }

    }

    private void NotifyLogic() {

        String date = "03/26/2012 11:00:00";//current
        String dateafter = "03/26/2012 11:01:00";
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM/dd/yyyy hh:mm:ss");
        Date convertedDate = new Date();
        Date convertedDate2 = new Date();
        try {
            convertedDate = dateFormat.parse(date);
            convertedDate2 = dateFormat.parse(dateafter);
            if (convertedDate2.after(convertedDate)) {
                Notify("T20 WorldCup 2016","Click here to see Updated Point Table");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void Notify(String notificationTitle, String notificationMessage){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        @SuppressWarnings("deprecation")

        Notification notification = new Notification(R.drawable.notification_template_icon_bg,"New Message", System.currentTimeMillis());
        Intent notificationIntent = new Intent(this,CtPointsTable.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        //notification.setLatestEventInfo(CtMainMenu.this, notificationTitle,notificationMessage, pendingIntent);
        notificationManager.notify(9999, notification);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    public void showFixtures(View view) {
        Intent startingPoint = new Intent("android.intent.action.CTFIXTURESLIST");
        startActivity(startingPoint);
        overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
    }

    public void showPointsTable(View view) {
        Intent startingPoint = new Intent("android.intent.action.CTPOINTSTABLE");
        startActivity(startingPoint);
        overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit);
    }

    public void exitApp(View view) {
        finish();
    }

    private JSONObject getJSONData() throws JSONException {
        return new JSONObject(loadJSONFromSuitablePlace());
    }

    public String loadJSONFromSuitablePlace() {
        if (CtStorageHelper.isExternalStorageReadableAndWritable()) {
            if (!fileExistsInExternalStorage("championsTrophy2017.json")) {
                copyFiletoExternalStorage("championsTrophy2017.json");
            }
            return readJsonObjectFromPath("/data/data/" + this.getPackageName() + "/championsTrophy2017.json", true);
        } else
            return readJsonObjectFromPath("championsTrophy2017.json", false);

    }

    private String readJsonObjectFromPath(String path, boolean isExternalStoragePath) {
        String json;
        InputStream is = null;
        try {
            if (isExternalStoragePath)
                is = new FileInputStream(path);
            else
                is = getAssets().open(path);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private boolean fileExistsInExternalStorage(String fileName) {
        File myFile = new File("/data/data/" + this.getPackageName() + "/" + fileName);

        return myFile.exists();
    }

    private void copyFiletoExternalStorage(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            String newFileName = "/data/data/" + this.getPackageName() + "/" + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    private void checkIfAssetsFileIsRecent() {
    }



}
