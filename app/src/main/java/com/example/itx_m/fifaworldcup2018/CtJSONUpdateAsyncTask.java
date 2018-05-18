package com.example.itx_m.fifaworldcup2018;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Zohaib on 04/02/2016.
 */
public class CtJSONUpdateAsyncTask extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog;
        InputStream inputStream = null;
        String result = "";
        String resultcontent = "";
        Context context;

        public CtJSONUpdateAsyncTask(Context context){
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    CtJSONUpdateAsyncTask.this.cancel(true);
                }
            });
        }
    @Override
    protected Void doInBackground(String... params) {
        String url = "https://trendinglobe.com/wp-content/uploads/data/data.json";
        try {
            URL url2 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            inputStream = connection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        RequestQueue queue = Volley.newRequestQueue(context);
//        String url = "https://trendinglobe.com/wp-content/uploads/data/testJson.json";
//
//        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // display response
//                        Log.d("Response", response.toString());
//                        inputStream = new ByteArrayInputStream(response.toString().getBytes(Charset.forName("UTF-8")));
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Response", error.getMessage());
//                    }
//                }
//        );
//        String url_select = "http://zairexport.com/saad/silverhawk/championsTrophy2017.json";

//        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
//
//        try {
//            // Set up HTTP post
//
//            // HttpClient is more then less deprecated. Need to change to URLConnection
//            HttpClient httpClient = new DefaultHttpClient();
//
//            HttpPost httpPost = new HttpPost(url_select);
//            httpPost.setEntity(new UrlEncodedFormEntity(param));
//            HttpResponse httpResponse = httpClient.execute(httpPost);
//            HttpEntity httpEntity = httpResponse.getEntity();
//
//            // Read content & Log
//            inputStream = httpEntity.getContent();
//        } catch (UnsupportedEncodingException e1) {
//            Log.e("UnsupportedEncodngExptn", e1.toString());
//            e1.printStackTrace();
//        } catch (ClientProtocolException e2) {
//            Log.e("ClientProtocolException", e2.toString());
//            e2.printStackTrace();
//        } catch (IllegalStateException e3) {
//            Log.e("IllegalStateException", e3.toString());
//            e3.printStackTrace();
//        } catch (IOException e4) {
//            Log.e("IOException", e4.toString());
//            e4.printStackTrace();
//        }
        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();

        } catch (Exception e) {
            Log.e("StringBldng & BffrdRdr", "Error converting result " + e.toString());
        }
        return null;
    }
    protected void onPostExecute(Void v) {
        //parse JSON data
        try {
            String newFileName = "/data/data/" + context.getPackageName() + "/championsTrophy2017.json";
            FileOutputStream outputStream  = new FileOutputStream(new File(newFileName));
            outputStream.write(result.getBytes());
            outputStream.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        try {
            CtApp.jsonFile = new JSONObject(result);
            this.progressDialog.dismiss();
        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        } // catch (JSONException e)
    }
}
