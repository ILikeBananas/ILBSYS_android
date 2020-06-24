package com.example.ilbsys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.app.DialogFragment;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button selectServerButton = findViewById(R.id.bt_server_selection);
        selectServerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                selectServerClick(view);
            }
        });

        final Button addServerButton = findViewById(R.id.bt_add_server);
        addServerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addServerClick(view);
            }
        });

        final Button seeStatisticsButton = findViewById(R.id.bt_statistics);
        seeStatisticsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                statisticsClick(view);
            }
        });

        final Button deleteServerButton = findViewById(R.id.bt_delete_server);
        deleteServerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteServerClick(view);
            }
        });

        final Button editServerButton = findViewById(R.id.bt_edit_server);
        editServerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                editServerClick(view);
            }
        });
    }

    public void selectServerClick(android.view.View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Setting the title for the modal
        builder.setTitle(R.string.server_selection);
        builder.setItems(Utilities.getAllServerAsString(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utilities.setCurrentServer(which);
            }
        });
        builder.show();
    }

    public void addServerClick(android.view.View view) {
        Intent addServerIntent = new Intent(this, AddServerActivity.class);
        startActivity(addServerIntent);
    }

    public void statisticsClick(android.view.View view) {
        Intent statisticsIntent = new Intent(this, SeeInfos.class);
        startActivity(statisticsIntent);
    }

    public void deleteServerClick(android.view.View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Setting the title for the modal
        builder.setTitle(R.string.delete_server);
        builder.setItems(Utilities.getAllServerAsString(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utilities.deleteServer(which);

            }
        });
        builder.show();
    }

    public void editServerClick(android.view.View view) {
       Intent editServerIntent = new Intent(this, EditServerActivity.class);
       startActivity(editServerIntent);
    }



}