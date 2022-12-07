package de.nudelsuppe.eventboard.board;

import de.nudelsuppe.eventboard.Main;
import de.nudelsuppe.eventboard.data.DataSource;
import de.nudelsuppe.eventboard.data.LeaderboardUser;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.*;

public class BoardManager {

    private static Map<UUID, FastBoard> boards = new HashMap<>();


    public static void addBoard(Player player) {
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.GOLD+""+ChatColor.BOLD+"Leaderboard");
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
        System.out.println("Updating boards...");
        for(FastBoard board: boards.values()) {
            updateBoard(board);
        }
    }
    private static void updateBoard(FastBoard board) {
        ArrayList<String> res = new ArrayList<>();
        for(int i=0; i < Math.min(DataSource.data.getUsers().length, 10);i++) {
            LeaderboardUser user = DataSource.data.getUsers()[i];
            Player p = Bukkit.getPlayer(user.getMinecraft_id());
            if(user.getPoints()<=0|| user.getMinecraft_id().equals("") || Objects.isNull(p))continue;
            res.add(ChatColor.YELLOW+""+(i+1)+". "+ChatColor.GRAY+p.getName()+ChatColor.DARK_GRAY+" ("+ChatColor.YELLOW+user.getPoints()+ChatColor.DARK_GRAY+")");
        }
        if(DataSource.data.getUsers().length>10) {
            res.add(ChatColor.GRAY+"+ "+(DataSource.data.getUsers().length-10)+" weitere");
        }
        res.add("");
        res.add(ChatColor.YELLOW+"Gebäude: "+ChatColor.GRAY+DataSource.data.getBuilds().length);
        res.add(ChatColor.YELLOW+"Builder: "+ChatColor.GRAY+DataSource.data.getUsers().length);
        res.add(ChatColor.YELLOW+"Insg. Punkte: "+ChatColor.GRAY+DataSource.data.getPoints());

        board.updateLines(res);
    }
}
