package ro.pub.cs.systems.eim.practicaltest02v8;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class CommunicationThread extends Thread {

    private final Socket socket;

    public CommunicationThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                Log.i("SERVER", "Received from client: " + line);
            }

            socket.close();
        } catch (Exception e) {
            Log.e("SERVER", "Error in CommunicationThread");
        }
    }
}
