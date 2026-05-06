package com.example.applocalizacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonGps = findViewById(R.id.button_gps);
        Button buttonMap = findViewById(R.id.button_map);

        buttonGps.setOnClickListener(this);
        buttonMap.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_gps) {
            Intent i = new Intent(this, LocationActivity.class);
            startActivity(i);
        }

        if (view.getId() == R.id.button_map) {
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);
        }
    }
}