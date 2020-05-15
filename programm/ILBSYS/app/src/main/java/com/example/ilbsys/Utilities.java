package com.example.ilbsys;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class Utilities extends Application {

    //private static Server server = new Server("Tower", "192.168.1.3");
    private static ArrayList<Server> servers = new ArrayList<Server>();

    private static int selectedServerIndex = 0;

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
}
