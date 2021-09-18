package com.medical.citylap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.medical.citylap.Adapter.ListViewAdapter;
import com.medical.citylap.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// FragmentActivity implements OnMapReadyCallback,
//         GoogleApiClient.ConnectionCallbacks,
//         GoogleApiClient.OnConnectionFailedListener,
//         LocationListener
public class Mooglmap extends AppCompatActivity implements OnMapReadyCallback {
    // below are the latitude and longitude
    // of 4 different locations.
    Geocoder geo;

    LatLng sydney = new LatLng(29.613473238612904, 30.654309019446373);
    LatLng TamWorth = new LatLng(29.891483056260487 , 31.261986941099167);
    LatLng NewCastle = new LatLng(29.898009941244563, 31.26653596758842);
    LatLng Brisbane = new LatLng(-2.470125, 15.021072);
    private GoogleMap mMap;
    // creating array list for adding all our locations.
    private ArrayList<LatLng> locationArrayList;

    String[] location;
    ListView listView;
    ListAdapter listAdapter;
    CardView cardView;
    ImageView shoelist;
    ImageView backhome;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        location = new String[]{"فرع الجيزه", "فرع المنيب", "فرع شارع الصحافه", "فرع شارع الحمهوريه"};
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.onCreate(savedInstanceState);

        locationArrayList = new ArrayList<>();

        locationArrayList.add(sydney);
        locationArrayList.add(TamWorth);
        locationArrayList.add(NewCastle);
        locationArrayList.add(Brisbane);

        listView = findViewById(R.id.list_item_location);
        cardView = findViewById(R.id.cardView4);
        shoelist = findViewById(R.id.showe_list);
        listAdapter = new ListViewAdapter(this, location);
        listView.setAdapter(listAdapter);
        shoelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                cardView.setVisibility(View.GONE);
                shoelist.setVisibility(View.VISIBLE);

                mMap.clear();
                // below line is use to add marker to each location of our array list.
                mMap.addMarker(new MarkerOptions().position(locationArrayList.get(position)).title("Marker"));

                // below lin is use to zoom our camera on map.

                CameraPosition cameraPosition = CameraPosition.builder().target(locationArrayList.get(position)).zoom(20f).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

//                // below line is use to move our camera to the specific location.
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(position)));


            }
        });
        backhome = findViewById(R.id.backtohome);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mooglmap.this, Home.class));
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    protected void onStart() {
        super.onStart();
        mapFragment.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapFragment.onStop();
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            geo = new Geocoder(Mooglmap.this, Locale.getDefault());

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    try {
                        if (geo == null)
                            geo = new Geocoder(Mooglmap.this, Locale.getDefault());
                        List<Address> address = geo.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        if (address.size() > 0) {
                            mMap.addMarker(new MarkerOptions().position(latLng).title("Name:" + address.get(0).getCountryName()
                                    + ". Address:" + address.get(0).getAddressLine(0)));

//                            txtMarkers.setText("Name:" + address.get(0).getCountryName()
//                                    + ". Address:" + address.get(0).getAddressLine(0));
                        }
                    } catch (IOException ex) {
                        if (ex != null)
                            Toast.makeText(Mooglmap.this, "Error:" + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Log.d("TAG", "onMarkerClick: "+marker.getTitle().toString() + " Lat:" + marker.getPosition().latitude + " Long:" + marker.getPosition().longitude);
                    return false;
                }
            });
        }

    }

}