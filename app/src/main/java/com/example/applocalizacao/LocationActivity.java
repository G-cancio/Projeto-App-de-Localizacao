package com.example.applocalizacao;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class LocationActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_UPDATES = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Button buttonStart = findViewById(R.id.buttonStart);
        Button buttonStop = findViewById(R.id.buttonStop);


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationsUpdate();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationsUpdate();
            }
        });
    }

    public void startLocationsUpdate() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            long timeInterval = 5 * 1000;
            locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, timeInterval).build();

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    Location location = locationResult.getLastLocation();
                    atualizaLocationTextView(location);
                }
            };
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_UPDATES);
    }
    }

    public void stopLocationsUpdate() {
        if (fusedLocationProviderClient != null && locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
        atualizaLocationTextView(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationsUpdate();
        } else {
            Toast.makeText(this, "Sem permissão para mostrar atualizações da sua localização", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void atualizaLocationTextView (Location location) {
        TextView locationTextView = (TextView)
                findViewById(R.id.locationTextView);
        String s = "Dados da Última Localização:\n";

        if (location!=null) {
            s += "Latitude: (G/M/S) " + Location.convert(location.getLatitude(),Location.FORMAT_SECONDS) + "\n";
            s += "Longitude: (G/M/S) " + Location.convert(location.getLongitude(),Location.FORMAT_SECONDS) + "\n";
            s += "Altitude: " + location.getAltitude() + "\n";
            s += "Rumo: (radianos) " + location.getBearing() + "\n";
            s += "Velocidade (m/s): " + location.getSpeed() + "\n";
            s += "Precisão: (m) " + location.getAccuracy() + "\n";
        }
        locationTextView.setText(s);
    }
}