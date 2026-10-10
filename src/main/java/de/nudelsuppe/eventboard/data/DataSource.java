package de.nudelsuppe.eventboard.data;

import com.google.gson.Gson;
import de.nudelsuppe.eventboard.Main;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class DataSource {

    public static volatile LeaderboardData data = LeaderboardData.empty();

    public static void getData() {
        try {
            //Bukkit.getLogger().log(Level.INFO,"[Event] Loading Data...");
            URL url = java.net.URI.create(Main.getInstance().getConfig().getString("apiUrl")+"/leaderboard").toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestProperty("Accept", "application/json");
            try {
                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IllegalStateException("HTTP response " + responseCode);
                }
                try (InputStream in = conn.getInputStream()) {
                    LeaderboardData freshData = new Gson().fromJson(
                            new String(in.readAllBytes(), StandardCharsets.UTF_8), LeaderboardData.class);
                    if (freshData == null) throw new IllegalStateException("API returned an empty response");
                    if (freshData.getUsers() == null) freshData.setUsers(new de.nudelsuppe.eventboard.data.LeaderboardUser[0]);
                    if (freshData.getBuilds() == null) freshData.setBuilds(new LeaderboardBuilds[0]);
                    data = freshData;
                }
            } finally {
                conn.disconnect();
            }
        } catch (Exception e) {
            Main plugin = Main.getInstance();
            if (plugin != null) {
                plugin.getLogger().log(Level.WARNING, "Could not fetch leaderboard data; continuing with the last available data: " + e.getMessage());
            }
        }
    }
}
