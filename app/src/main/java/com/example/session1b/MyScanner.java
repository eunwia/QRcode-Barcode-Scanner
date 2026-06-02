package com.example.session1b;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.zxing.BarcodeFormat;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MyScanner extends AppCompatActivity {

    private DecoratedBarcodeView barcodeScannerView;
    private TextView statusText;
    private static final int CAMERA_PERMISSION_REQUEST = 101;

    private String lastText;
    private boolean isProcessing = false;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() == null || result.getText().equals(lastText) || isProcessing) {
                return;
            }

            isProcessing = true;
            lastText = result.getText();
            
            // STRICT CHECK: ONLY send and show link if configured
            if (MyService.ipaddress == null || MyService.ipaddress.isEmpty() || MyService.port == null || MyService.port.isEmpty()) {
                runOnUiThread(() -> {
                    statusText.setText("Set up your port number and ip address first before scanning.");
                    Toast.makeText(MyScanner.this, "Set up your port number and ip address first before scanning.", Toast.LENGTH_LONG).show();
                    
                    // Reset processing flag after a delay
                    new android.os.Handler().postDelayed(() -> {
                        isProcessing = false;
                        lastText = null;
                    }, 2000);
                });
                return;
            }

            // ONLY NOW REVEAL THE LINK
            MyService.qrcode = result.getText();
            runOnUiThread(() -> {
                statusText.setText("http://" + MyService.ipaddress + ":" + MyService.port);
            });

            // Send scanned result to Python server
            new Thread(() -> {
                try {
                    String urlStr = "http://" + MyService.ipaddress + ":" + MyService.port + "/scan";
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    String json = "{\"text\": \"" + MyService.qrcode + "\"}";
                    conn.getOutputStream().write(json.getBytes());
                    conn.getOutputStream().flush();
                    conn.getOutputStream().close();

                    int responseCode = conn.getResponseCode();
                    conn.disconnect();

                    runOnUiThread(() -> {
                        if (responseCode == 200) {
                            Toast.makeText(MyScanner.this, "Sent: " + MyService.qrcode, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MyScanner.this, "Server error: " + responseCode, Toast.LENGTH_SHORT).show();
                        }
                        
                        // Reset processing flag after a delay
                        new android.os.Handler().postDelayed(() -> {
                            isProcessing = false;
                            lastText = null;
                        }, 2000);
                    });

                } catch (Exception e) {
                    runOnUiThread(() -> {
                        Toast.makeText(MyScanner.this, "Connection failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        // Reset processing flag after error
                        new android.os.Handler().postDelayed(() -> {
                            isProcessing = false;
                            lastText = null;
                        }, 2000);
                    });
                }
            }).start();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_scanner);

        barcodeScannerView = findViewById(R.id.barcodeScannerView);
        statusText = findViewById(R.id.statusText);

        // Remove the default text inside the scanner
        barcodeScannerView.setStatusText("");

        // Initialize from intent is often required for internal setup
        barcodeScannerView.initializeFromIntent(getIntent());
        
        // Start decoding
        barcodeScannerView.decodeContinuous(callback);

        // Check for camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeScannerView.resume();

        // Reload configuration
        String savedIp = getSharedPreferences("settings", MODE_PRIVATE).getString("ipaddress", "");
        String savedPort = getSharedPreferences("settings", MODE_PRIVATE).getString("port", "");

        MyService.ipaddress = savedIp;
        MyService.port = savedPort;

        // INITIALLY: NO LINK. ONLY INSTRUCTIONS OR ERROR.
        if (savedIp.isEmpty() || savedPort.isEmpty()) {
            statusText.setText("Set up your port number and ip address first before scanning.");
        } else {
            statusText.setText("Place QR code inside the scanner to scan it.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeScannerView.pause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                barcodeScannerView.resume();
            } else {
                Toast.makeText(this, "Camera permission is required to scan", Toast.LENGTH_LONG).show();
            }
        }
    }
}