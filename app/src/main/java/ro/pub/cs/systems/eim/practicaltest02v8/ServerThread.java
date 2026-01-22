package ro.pub.cs.systems.eim.practicaltest02v8;

import android.util.Log;

import java.io.IOException;
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

            while (!isInterrupted()) {
                Log.i("SERVER", "Waiting for client...");
                Socket socket = serverSocket.accept();

                CommunicationThread communicationThread =
                        new CommunicationThread(socket);
                communicationThread.start();
            }

        } catch (IOException e) {
            Log.e("SERVER", "Could not start server");
        }
    }

    public void stopServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException ignored) {}
    }
}
