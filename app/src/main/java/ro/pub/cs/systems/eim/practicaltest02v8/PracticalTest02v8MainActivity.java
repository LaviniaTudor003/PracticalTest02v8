package ro.pub.cs.systems.eim.practicaltest02v8;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest02v8MainActivity extends AppCompatActivity {

    private EditText serverPortEditText;
    private Button startServerButton;
    private ServerThread serverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02v8_main);

        serverPortEditText = findViewById(R.id.server_port_edit_text);
        startServerButton = findViewById(R.id.start_server_button);

        startServerButton.setOnClickListener(v -> {
            String portString = serverPortEditText.getText().toString();

            if (portString.isEmpty()) {
                Toast.makeText(this, "Port required", Toast.LENGTH_SHORT).show();
                return;
            }

            int port = Integer.parseInt(portString);
            serverThread = new ServerThread(port);
            serverThread.start();

            Toast.makeText(this, "Server started on port " + port, Toast.LENGTH_SHORT).show();
        });
//        b) - trimiterea paginilor catre clienti care se conecteaza pe un port indicat de utilizator in interfata grafica(fie continut pagina, fie "URL blocked by firewall"
    }
}
