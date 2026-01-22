package ro.pub.cs.systems.eim.practicaltest02v8;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest02v8MainActivity extends AppCompatActivity {

    private EditText serverPortEditText;
    private Button startServerButton;
    private EditText urlEditText;
    private Button sendRequestButton;
    public static TextView resultTextView;

    private ServerThread serverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02v8_main);

        serverPortEditText = findViewById(R.id.server_port_edit_text);
        startServerButton = findViewById(R.id.start_server_button);
        urlEditText = findViewById(R.id.url_edit_text);
        sendRequestButton = findViewById(R.id.send_request_button);
        resultTextView = findViewById(R.id.result_text_view);

        startServerButton.setOnClickListener(v -> {
            int port = Integer.parseInt(serverPortEditText.getText().toString());
            serverThread = new ServerThread(port);
            serverThread.start();

            Toast.makeText(this, "Server started", Toast.LENGTH_SHORT).show();
        });

        sendRequestButton.setOnClickListener(v -> {
            String url = urlEditText.getText().toString();
            int port = Integer.parseInt(serverPortEditText.getText().toString());

            ClientThread clientThread =
                    new ClientThread(
                            "127.0.0.1",
                            port,
                            url,
                            new Handler(getMainLooper())
                    );
            clientThread.start();
        });
    }
}
