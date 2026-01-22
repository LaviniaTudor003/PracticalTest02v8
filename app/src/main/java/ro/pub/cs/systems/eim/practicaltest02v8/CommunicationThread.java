package ro.pub.cs.systems.eim.practicaltest02v8;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class CommunicationThread extends Thread {

    private final Socket socket;

    public CommunicationThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter =
                    new PrintWriter(socket.getOutputStream(), true);

            String urlString = bufferedReader.readLine();
            Log.i("SERVER", "Received URL: " + urlString);

            if (urlString == null || urlString.isEmpty()) {
                socket.close();
                return;
            }

            if (urlString.contains("bad")) {
                printWriter.println("URL blocked by firewall");
                printWriter.flush();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}

                socket.close();
                return;
            }

            URL url = new URL(urlString);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader httpReader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = httpReader.readLine()) != null) {
                result.append(line).append("\n");
            }

            httpReader.close();

            printWriter.println(result.toString());

            socket.close();

        } catch (Exception e) {
            Log.e("SERVER", "Error: " + e.getMessage());
        }
    }
}
