package com.example.session1b;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IpAddress extends AppCompatActivity {

    EditText txtIpAddress;
    Button saveIpAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ip_address);

        txtIpAddress = findViewById(R.id.myipaddress);
        saveIpAddress = findViewById(R.id.saveipaddress);

        // Load previously saved IP and show it in the field
        String savedIp = getSharedPreferences("settings", MODE_PRIVATE)
                .getString("ipaddress", "");
        txtIpAddress.setText(savedIp);
        MyService.ipaddress = savedIp;

        saveIpAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = txtIpAddress.getText().toString().trim();
                if (ip.isEmpty()) {
                    txtIpAddress.setError(getString(R.string.error_enter_ip));
                    txtIpAddress.requestFocus();
                } else {
                    MyService.ipaddress = ip;

                    // Save to SharedPreferences so it persists
                    getSharedPreferences("settings", MODE_PRIVATE)
                            .edit()
                            .putString("ipaddress", ip)
                            .apply();

                    Toast.makeText(IpAddress.this, "IP saved: " + ip, Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}