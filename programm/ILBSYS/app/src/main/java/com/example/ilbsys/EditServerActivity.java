package com.example.ilbsys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditServerActivity extends AppCompatActivity {
    Button changeButton;
    EditText nameEdit, addressEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_server);

        // Get the buttons
        changeButton = (Button) findViewById(R.id.bt_submit);

        // Get EditText
        nameEdit = (EditText) findViewById(R.id.server_name);
        addressEdit = (EditText) findViewById(R.id.server_address);

        // Fill the edit text fields with the data of the current server
        nameEdit.setText(Utilities.getCurrentServer().Name);
        addressEdit.setText(Utilities.getCurrentServer().Address);

        // Open modal to select a server
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.editServer(Utilities.getCurrentServerIndex(), new Server(nameEdit.getText().toString(), addressEdit.getText().toString()));
            }
        });


    }
}