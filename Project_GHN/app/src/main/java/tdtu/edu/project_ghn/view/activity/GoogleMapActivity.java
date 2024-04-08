package tdtu.edu.project_ghn.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import tdtu.edu.project_ghn.R;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            address = extras.getString("address");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                Address location = addresses.get(0);
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                //Add markers
                gMap.addMarker(new MarkerOptions().position(latLng).title("The Address"));

                //Move camera and zoom in
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 100.0f));

                //Calculate distance
                /*float[] results = new float[1];
                Location.distanceBetween(location1.getLatitude(), location1.getLongitude(),
                        location2.getLatitude(), location2.getLongitude(), results);
                float distanceInMeters = results[0];
                boolean isWithin10km = distanceInMeters < 10000;
                Log.i("Distance", "Distance: " + distanceInMeters + ", Is within 10km? " + isWithin10km);*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}