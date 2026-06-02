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

public class MyPort extends AppCompatActivity {

    EditText txtPort;
    Button savePort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_port);

        txtPort=findViewById(R.id.myport);
        savePort = findViewById(R.id.saveport);

        // Load previously saved Port and show it in the field
        String savedPort = getSharedPreferences("settings", MODE_PRIVATE)
                .getString("port", "");
        txtPort.setText(savedPort);
        MyService.port = savedPort;

        savePort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String port = txtPort.getText().toString().trim();
                if (port.isEmpty()) {
                    txtPort.setError(getString(R.string.error_enter_port));
                    txtPort.requestFocus();
                } else if (!port.equals("2025")) {
                    txtPort.setError(getString(R.string.error_incorrect_port));
                    txtPort.requestFocus();
                } else {
                    MyService.port = port;
                    // Save to SharedPreferences so it persists
                    getSharedPreferences("settings", MODE_PRIVATE)
                            .edit()
                            .putString("port", port)
                            .apply();
                    Toast.makeText(MyPort.this, "Port saved: " + port, Toast.LENGTH_SHORT).show();
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