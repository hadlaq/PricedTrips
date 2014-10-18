package com.hadlag.omar.pricedtrips;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends Activity {

    public int Price;
    public double lon;
    public double lat;
    public GoogleMap map;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location me = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (me != null) {
            lon = me.getLongitude();
            lat = me.getLatitude();
            LatLng ME = new LatLng(lat, lon);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(ME, 12));
        }

    }

    public void calculate(View view) {
        Intent intent = new Intent(this, Results.class);

        EditText txtlat = (EditText) findViewById(R.id.edt_e_lat);
        EditText txtlon = (EditText) findViewById(R.id.edt_e_lon);
        /*if (txt.getText().toString().isEmpty()) {
            Price = 0;
        } else {
            Price = Integer.parseInt(txt.getText().toString());
        }*/
        Bundle extras = new Bundle();
        extras.putDouble("Longitude1", lon);
        extras.putDouble("Latitude1", lat);
        extras.putDouble("Longitude2", Double.parseDouble(txtlon.getText().toString()));
        extras.putDouble("Latitude2", Double.parseDouble(txtlat.getText().toString()));
        intent.putExtras(extras);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location me) {
            lon = me.getLongitude();
            lat = me.getLatitude();
            LatLng ME = new LatLng(lat, lon);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(ME, 12));
        }
    };
}
