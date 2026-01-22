package ro.pub.cs.systems.eim.practicaltest02v8;

import android.util.Log;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private final int port;
    private ServerSocket serverSocket;

    public ServerThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            Log.i("SERVER", "Server started on port " + port);

            while (true) {
                Log.i("SERVER", "Waiting for client...");
                Socket socket = serverSocket.accept();
                Log.i("SERVER", "Client connected");

                CommunicationThread communicationThread =
                        new CommunicationThread(socket);
                communicationThread.start();
            }

        } catch (Exception e) {
            Log.e("SERVER", "Could not start server");
        }
    }
}
