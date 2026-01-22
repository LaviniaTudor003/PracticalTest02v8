package ro.pub.cs.systems.eim.practicaltest02v8;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private final String address;
    private final int port;
    private final String url;
    private final Handler handler;

    public ClientThread(String address, int port, String url, Handler handler) {
        this.address = address;
        this.port = port;
        this.url = url;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(address, port);

            PrintWriter printWriter =
                    new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            printWriter.println(url);

            final String response = bufferedReader.readLine();

            handler.post(() ->
                    PracticalTest02v8MainActivity.resultTextView.setText(response)
            );

            socket.close();

        } catch (Exception e) {
            Log.e("CLIENT", "Error: " + e.getMessage());
        }
    }
}
