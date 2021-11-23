package com.medical.citylap.activity;

import androidx.annotation.NonNull;
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
import android.widget.ProgressBar;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medical.citylap.Adapter.ListViewAdapter;
import com.medical.citylap.R;
import com.medical.citylap.modles.LocationModle;

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
    ProgressBar progressBar;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    private GoogleMap mMap;
    // creating array list for adding all our locations.
    private ArrayList<LatLng> locationArrayList;

    String[] location=new String[]{};
    List<String>Locationlist=new ArrayList<>();
    ListView listView;
    ListAdapter listAdapter;
    CardView cardView;
    ImageView shoelist;
    ImageView backhome;
    SupportMapFragment mapFragment;
    LocationModle locationModlel=new LocationModle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

// Permission is not granted
// Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
// Permission has already been granted



        }



        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.onCreate(savedInstanceState);
        locationArrayList = new ArrayList<>();
progressBar=findViewById(R.id.locatioprograss);
        getdata();

        listView = findViewById(R.id.list_item_location);
        cardView = findViewById(R.id.cardView4);
        shoelist = findViewById(R.id.showe_list);

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

                CameraPosition cameraPosition = CameraPosition.builder().target(locationArrayList.get(position)).zoom(15f).build();
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

    public void getdata()
    {

        ArrayList<String>locationModles=new ArrayList<>();

        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("location");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int i=0;
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {

                        locationModlel = npsnapshot.getValue(LocationModle.class);
                      //  locationModles.add(locationModlel.getBigadd());
                       // location[i]=(locationModlel.getNamelabe().toString());
                        Locationlist.add(locationModlel.getNamelabe().toString());
                        locationArrayList.add(new LatLng(Double.parseDouble( locationModlel.getLat().toString()),Double.parseDouble(locationModlel.getLog().toString())));
                        i++;
                    }
                    listAdapter = new ListViewAdapter(Mooglmap.this, Locationlist);
                    listView.setAdapter(listAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }


        });


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

//            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                @Override
//                public void onMapClick(LatLng latLng) {
//                    try {
//                        if (geo == null)
//                            geo = new Geocoder(Mooglmap.this, Locale.getDefault());
//                        List<Address> address = geo.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                        if (address.size() > 0) {
//                            mMap.addMarker(new MarkerOptions().position(latLng).title("Name:" + address.get(0).getCountryName()
//                                    + ". Address:" + address.get(0).getAddressLine(0)));
//
////                            txtMarkers.setText("Name:" + address.get(0).getCountryName()
////                                    + ". Address:" + address.get(0).getAddressLine(0));
//                        }
//                    } catch (IOException ex) {
//                        if (ex != null)
//                            Toast.makeText(Mooglmap.this, "Error:" + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//
//            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(Marker marker) {
//                    Log.d("TAG", "onMarkerClick: "+marker.getTitle().toString() + " Lat:" + marker.getPosition().latitude + " Long:" + marker.getPosition().longitude);
//                    return false;
//                }
//            });
        }

    }
    //When the connect request has successfully completed


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


}