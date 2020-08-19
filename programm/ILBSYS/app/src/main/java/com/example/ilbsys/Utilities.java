package com.example.ilbsys;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utilities extends Application {

    public static final String SERVERS = "servers";
    public static final String USER = "USER";
    private static ArrayList<Server> servers = new ArrayList<Server>();

    private static int selectedServerIndex = 0;
    public static SharedPreferences sharedPreferences;// = getSharedPreferences(USER, MODE_PRIVATE);

    public void Utilities() {
        loadData();
        sharedPreferences = getApplicationContext().getSharedPreferences(USER, MODE_PRIVATE);
    }

    public static ArrayList<Server> getAllServer() {
        return servers;
    }

    public static String[] getAllServerAsString() {
        String[] serverString = new String[servers.size()];
        if(servers.size() != 0) {
            for (int i = 0; i < servers.size(); i++) {
                serverString[i] = servers.get(i).Name;
            }
        }
        return serverString;
    }

    public static void addServer(Server server) {
        servers.add(server);
        saveData();
    }

    public static int getCurrentServerIndex() {
        return selectedServerIndex;
    }

    public static void setCurrentServer(int serverIndex) {
        selectedServerIndex = serverIndex;
    }


    public static Server getCurrentServer() {
        return servers.get(selectedServerIndex);
    }

    public static void deleteServer(int serverIndex) {
        servers.remove(serverIndex);
        saveData();
    }

    public static void editServer(int serverIndex, Server server) {
        servers.get(serverIndex).Address = server.Address;
        servers.get(serverIndex).Name = server.Name;
        saveData();
    }

    // Saves the servers in the shared preferences
    public static void saveData() {
        Gson gson = new Gson();
        String json = gson.toJson(servers);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SERVERS, json);
        editor.commit();
    }

    // Loads the servers from the shared preferences
    public static void loadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SERVERS, null);
        if (json != null && !json.isEmpty()) {
            Type type = new TypeToken<ArrayList<Server>>() {

            }.getType();
            servers = gson.fromJson(json, type);
        }
    }

}
