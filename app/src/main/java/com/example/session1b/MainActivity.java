package com.example.session1b;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageButton btnScanner,btnPort,btnIpAddress,btnUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnScanner=findViewById(R.id.scanner);
        btnPort=findViewById(R.id.port);
        btnIpAddress=findViewById(R.id.ipaddress);
        btnUserProfile=findViewById(R.id.userprofile);

        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scannerIntent = new Intent(MainActivity.this,MyScanner.class);
                startActivity(scannerIntent);
            }
        });

        btnPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent portIntent = new Intent(MainActivity.this, MyPort.class);
                startActivity(portIntent);
            }
        });

        btnIpAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ipaddressIntent = new Intent(MainActivity.this,IpAddress.class);
                startActivity(ipaddressIntent);
            }
        });

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userProfileIntent = new Intent(MainActivity.this,UserProfile.class);
                startActivity(userProfileIntent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}