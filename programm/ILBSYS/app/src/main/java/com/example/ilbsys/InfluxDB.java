package com.example.ilbsys;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.provider.ContactsContract;
import android.renderscript.ScriptGroup;
import android.util.JsonReader;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class InfluxDB {


    public String currentServerAddress;

    InfluxDB() {
        ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void setCurrentServerAddress(String address) {
        currentServerAddress = address;
    }

    public Double getRamUsage() {
        Double ramUsage = 0.0;
        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("db", "telegraf");
            parameters.put("q", "SELECT last(\"used_percent\") FROM \"mem\" WHERE (\"host\" = 'Tower')");
            String changedURL = /*"http://" +*/ currentServerAddress;
            URL url = new URL("http", changedURL, 8086, "query?" + ParameterStringBuilder.getParamsString(parameters));

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            try {
                JSONObject reader = new JSONObject(content.toString());
                ramUsage = reader.getJSONArray("results").getJSONObject(0).getJSONArray("series").getJSONObject(0).getJSONArray("values").getJSONArray(0).getDouble(1);
            } catch(Exception e) {
                return 0.0;
            }

        } catch (Exception e) {
        }
        return ramUsage;
    }

    public Double getCPUUsage() {
        Double CPUUsage = 0.0;
        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("db", "telegraf");
            parameters.put("q", "SELECT last(\"usage_idle\") *-1+100 FROM \"cpu\" WHERE (\"cpu\" = 'cpu-total')");
            String changedURL = /*"http://" +*/ currentServerAddress;
            URL url = new URL("http", changedURL, 8086, "query?" + ParameterStringBuilder.getParamsString(parameters));

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            try {
                JSONObject reader = new JSONObject(content.toString());
                CPUUsage = reader.getJSONArray("results").getJSONObject(0).getJSONArray("series").getJSONObject(0).getJSONArray("values").getJSONArray(0).getDouble(1);
            } catch(Exception e) {
                return 0.0;
            }

        } catch (Exception e) {
            int i =  0;
        }
        return CPUUsage;


    }

}

