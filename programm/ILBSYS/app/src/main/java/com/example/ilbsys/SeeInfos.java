package com.example.ilbsys;

import android.os.Bundle;
import android.os.Debug;
import android.provider.ContactsContract;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class SeeInfos extends AppCompatActivity {
    public TextView ram_usage, cpu_usage, server_name, uptime;
    public Server server;
    public InfluxDB influxDB;
    public CustomGauge cpu_gauge, ram_gauge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_infos);
        DecimalFormat dec = new DecimalFormat("#0.0");

        // Textviews
        ram_usage = findViewById(R.id.ram_usage);
        cpu_usage = findViewById(R.id.cpu_usage);
        server_name = findViewById(R.id.server_name);
        uptime = findViewById(R.id.uptime);

        // Gauges
        cpu_gauge = findViewById(R.id.cpu_gauge);
        ram_gauge = findViewById(R.id.ram_gauge);

        // Setting server name
        server_name.setText(Utilities.getCurrentServer().Name);

        // Setting up influxDB
        influxDB = new InfluxDB();
        influxDB.setCurrentServerAddress(Utilities.getCurrentServer().Address);

        // Setting ram usage
        Double ram = influxDB.getRamUsage();

        ram_usage.setText(dec.format(ram).toString() + "%");
        ram_gauge.setValue(ram.intValue());

        // Setting cpu usage
        Double cpu = influxDB.getCPUUsage();
        cpu_usage.setText(dec.format(cpu).toString() + "%");
        cpu_gauge.setValue(cpu.intValue());

        // Setting uptime
        int uptimeValue = influxDB.getUptime();
        int days = uptimeValue / 60 / 60 / 24;
        int hours = (uptimeValue - (days * 24 * 60 * 60)) /60 / 60;
        String s = String.valueOf(days) + " " + getString(R.string.days) + " " + String.valueOf(hours) + " " + getString(R.string.hours);
        uptime.setText(String.valueOf(days) + " " + getString(R.string.days) + " " + String.valueOf(hours) + " " + getString(R.string.hours));
        int i = 0;

        // Setting CPU graph
        InfluxDB.UsageWithTime[] CPUpoints = influxDB.getCPUOverTime();
        GraphView cpuGraph = (GraphView) findViewById(R.id.cpu_graph);
        LineGraphSeries<DataPoint> cpuSeries = new LineGraphSeries<>();
        for(int j = 0; j < CPUpoints.length; j++) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            try {
                Date date = formatter.parse(CPUpoints[j].TimeStamp);
                cpuSeries.appendData(new DataPoint(date, CPUpoints[j].Usage), false, CPUpoints.length);
            } catch (Exception e) {
                int a = 0;
            }
        }

        cpuGraph.addSeries(cpuSeries);

        // Set date label formatter
        cpuGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        cpuGraph.getGridLabelRenderer().setHumanRounding(false);
        cpuGraph.getGridLabelRenderer().setNumHorizontalLabels(3);

        // Setting RAM graph
        InfluxDB.UsageWithTime[] ramPoints = influxDB.getRAMOverTime();
        GraphView ramGraph = (GraphView) findViewById(R.id.ram_graph);
        LineGraphSeries<DataPoint> ramSeries = new LineGraphSeries<>();
        for(int j = 0; j < ramPoints.length; j++) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            try {
                Date date = formatter.parse(ramPoints[j].TimeStamp);
                ramSeries.appendData(new DataPoint(date, ramPoints[j].Usage), false, ramPoints.length);
            } catch (Exception e) {
                int a = 0;
            }
        }

        ramGraph.addSeries(ramSeries);

        // Set date label formatter
        ramGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        ramGraph.getGridLabelRenderer().setHumanRounding(false);
        ramGraph.getGridLabelRenderer().setNumHorizontalLabels(3);


    }
}
