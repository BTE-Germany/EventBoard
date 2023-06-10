package de.nudelsuppe.eventboard.board;

import de.nudelsuppe.eventboard.Main;
import de.nudelsuppe.eventboard.data.DataSource;
import de.nudelsuppe.eventboard.data.LeaderboardUser;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Level;

public class BoardManager {

    private static Map<UUID, FastBoard> boards = new HashMap<>();



    public static void initializeBoard(Player player) {
        if(!Main.getInstance().getConfig().getBoolean("enabled"))
            return;
        addBoard(player);
    }

    public static void addBoard(Player player) {
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.GOLD+""+ChatColor.BOLD+"Event");
        boards.put(player.getUniqueId(), board);
        updateBoard(board);
    }

    public static void removeBoard(Player player) {
        FastBoard board = boards.remove(player.getUniqueId());
        if(board != null) board.delete();
    }
    public static void toggleBoard(Player player) {
        if(boards.containsKey(player.getUniqueId())) {
            removeBoard(player);
        }else {
            addBoard(player);
        }
    }

    public static void setBoards(Map<UUID, FastBoard> bs) {
        boards = bs;
    }

    public static Map<UUID, FastBoard> getBoards() {
        return boards;
    }

    public static void updateBoards() {
        Bukkit.getLogger().log(Level.INFO,"Updating Boards...");
        DataSource.getData();
        for(FastBoard board: boards.values()) {
            updateBoard(board);
        }
    }
    private static void updateBoard(FastBoard board) {
        ArrayList<String> res = new ArrayList<>();
        for(int i=0; i < Math.min(DataSource.data.getUsers().length, 4);i++) {
            LeaderboardUser user = DataSource.data.getUsers()[i];
            OfflinePlayer p = Bukkit.getOfflinePlayer(user.getMinecraft_id());
            if(user.getPoints()<=0|| user.getMinecraft_id().equals("")) continue;
            res.add(ChatColor.YELLOW+""+(i+1)+". "+ChatColor.GRAY+p.getName()+ChatColor.DARK_GRAY+" ("+ChatColor.YELLOW+Math.round(user.getPoints())+ChatColor.DARK_GRAY+")");
        }
        if(Arrays.stream(DataSource.data.getUsers()).filter(user -> (user.getPoints()>0&& !user.getMinecraft_id().equals(""))).toArray().length>10) {
            res.add(ChatColor.GRAY+"+ "+(DataSource.data.getUsers().length-10)+" weitere");
        }
        LeaderboardUser user = DataSource.data.getUser(board.getPlayer().getUniqueId().toString());
        if(user!=null) {
            res.add("");
            res.add(ChatColor.YELLOW+"Deine Punkte: "+ChatColor.GRAY+Math.round(user.getPoints()));
            res.add(ChatColor.YELLOW+"Deine Platzierung: "+ChatColor.GRAY+(Arrays.asList(DataSource.data.getUsers()).indexOf(user)+1));
        }
        res.add("");
        res.add(ChatColor.YELLOW+"Insg. Gebäude: "+ChatColor.GRAY+DataSource.data.getBuilds().length);
        res.add(ChatColor.YELLOW+"Insg. Punkte: "+ChatColor.GRAY+Math.round(DataSource.data.getPoints()));

        board.updateLines(res);
    }
}
