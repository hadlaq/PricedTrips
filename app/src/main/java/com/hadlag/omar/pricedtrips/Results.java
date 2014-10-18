package com.hadlag.omar.pricedtrips;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class Results extends Activity {

    public double lon1;
    public double lat1;
    public double lon2;
    public double lat2;

    TextView t1;
    TextView t2;
    TextView t4;
    String x = "..";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String[] temp = {"s"};
            new retrieveData().execute(temp);
        } else {

        }

        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);
        t4 = (TextView) findViewById(R.id.textView4);

        Bundle extras = getIntent().getExtras();
        lon1 = extras.getDouble("Longitude1");
        lat1 = extras.getDouble("Latitude1");
        lon2 = extras.getDouble("Longitude2");
        lat2 = extras.getDouble("Latitude2");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class retrieveData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String text = "";
            try {
                String link = "https://api.uber.com/v1/estimates/price?" +
                        "server_token=H2ccbDYUVdy3VmEtgYUIms94itkdkJmmMIEbQp8q" +
                        "&start_latitude=" + lat1 + "&start_longitude=" + lon1 + "&end_latitude=" +
                        lat2 + "&end_longitude=" + lon2;
                URL url = new URL(link);
                String line;
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                while ((line = in.readLine()) != null) {
                    text += line;
                }
                in.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return text;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json1 = new JSONObject(result);
                JSONArray json2 = new JSONArray(json1.getString("prices"));
                JSONObject json3 = json2.getJSONObject(0);
                x = json3.getString("estimate");
                String n = json3.getString("duration");
                n = n.trim();
                int y = Integer.parseInt(n);
                double z = y * 1.0 / 60;
                z = z * 10;
                y = (int) z;
                z = y * 1.0 / 10;
                String g = z + " minutes";
                t4.setText(g);
                t2.setText(x);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
