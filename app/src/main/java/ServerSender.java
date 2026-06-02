import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerSender {
    public static void send(String ip, String port, String scannedText) {
        // Run in background thread — never do network on main thread
        new Thread(() -> {
            try {
                URL url = new URL("http://" + ip + ":" + port + "/scan");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                String json = "{\"text\": \"" + scannedText + "\"}";
                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                System.out.println("Server response: " + responseCode);
                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
