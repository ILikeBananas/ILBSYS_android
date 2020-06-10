package com.example.ilbsys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddServerActivity extends AppCompatActivity {
    Button addButton;
    EditText nameEdit, addressEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_server);

        // Get the buttons
        addButton = (Button) findViewById(R.id.bt_submit);

        // Open modal to select a server
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEdit = (EditText) findViewById(R.id.server_name);
                addressEdit = (EditText) findViewById(R.id.server_address);
                Utilities.addServer(new Server(nameEdit.getText().toString(), addressEdit.getText().toString()));

                nameEdit.setText("");
                addressEdit.setText("");

                InfluxDB test = new InfluxDB();
                test.currentServerAddress = addressEdit.getText().toString();
                test.getRamUsage();
            }
        });


    }
}