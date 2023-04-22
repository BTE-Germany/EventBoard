package de.nudelsuppe.eventboard;

import de.nudelsuppe.eventboard.board.BoardManager;
import de.nudelsuppe.eventboard.commands.LeaderboardCommand;
import de.nudelsuppe.eventboard.data.DataSource;
import de.nudelsuppe.eventboard.listeners.JoinListener;
import de.nudelsuppe.eventboard.listeners.QuitListener;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {
    private static Main instance;
    public static String PREFIX = ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"Event"+ChatColor.DARK_GRAY+"] "+ChatColor.GRAY;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;
        registerCommands();
        registerListeners();
        DataSource.getData();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            @Override
            public void run() {
                BoardManager.updateBoards();
            }
        }, 0, 20L *getConfig().getInt("updateDelay"));

    }

    @Override
    public void onDisable() {
        for(Player p: Bukkit.getOnlinePlayers()) {
            BoardManager.removeBoard(p);
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public void registerCommands() {
        getCommand("leaderboard").setExecutor(new LeaderboardCommand());
        getCommand("leaderboard").setTabCompleter(new LeaderboardCommand());
    }

    public static void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new QuitListener(), instance);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), instance);

    }
    public static final Map<UUID, FastBoard> boards = new HashMap<>();

}

