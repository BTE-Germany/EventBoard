package de.nudelsuppe.eventboard.data;

import com.google.gson.Gson;
import de.nudelsuppe.eventboard.Main;
import jdk.internal.net.http.hpack.HPACK;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class DataSource {

    public static LeaderboardData data;

    public static void getData() {
        try {
            Bukkit.getLogger().log(Level.INFO,"Loading Data...");
            URL url = new URL(Main.getInstance().getConfig().getString("apiUrl")+"/leaderboard");//your url i.e fetch data from .
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {
                Gson g = new Gson();
                LeaderboardData s = g.fromJson(output, LeaderboardData.class);
                Bukkit.getLogger().log(Level.INFO,"Data loaded");
                data = s;
                return;
            }
            conn.disconnect();

        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE,"Exception: "+e);
            Main.getInstance().getPluginLoader().disablePlugin(Main.getInstance());
        }
    }
}
