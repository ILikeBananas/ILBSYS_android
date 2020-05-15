package com.example.ilbsys;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class SeeInfos extends AppCompatActivity {
    public TextView ram_usage, cpu_usage, server_name;
    public Server server;
    public InfluxDB influxDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_infos);
        DecimalFormat dec = new DecimalFormat("#0.0");

        // Textviews
        ram_usage = findViewById(R.id.ram_usage);
        cpu_usage = findViewById(R.id.cpu_usage);
        server_name = findViewById(R.id.server_name);

        // Setting server name
        server_name.setText(Utilities.getCurrentServer().Name);

        // Setting up influxDB
        influxDB = new InfluxDB();
        influxDB.setCurrentServerAddress(Utilities.getCurrentServer().Address);

        // Setting ram usage
        Double ram = influxDB.getRamUsage();

        ram_usage.setText(dec.format(ram).toString() + "%");

        // Setting cpu usage
        Double cpu = influxDB.getCPUUsage();
        cpu_usage.setText(dec.format(cpu).toString() + "%");

    }
}
