package de.nudelsuppe.eventboard.board;

import de.nudelsuppe.eventboard.Main;
import de.nudelsuppe.eventboard.LegacyFormat;
import de.nudelsuppe.eventboard.data.DataSource;
import de.nudelsuppe.eventboard.data.LeaderboardUser;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
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
        board.updateTitle(LegacyFormat.GOLD+LegacyFormat.BOLD+"Event");
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
        //Bukkit.getLogger().log(Level.INFO,"[Event] Updating Boards...");
        DataSource.getData();
        for(FastBoard board: boards.values()) {
            updateBoard(board);
        }
    }
    private static void updateBoard(FastBoard board) {
        ArrayList<String> res = new ArrayList<>();
        Player player = board.getPlayer();
        LeaderboardUser playerUser = DataSource.data.getUser(player.getName());
        boolean isPlayerInTop = false;
        for(int i=0; i < Math.min(DataSource.data.getUsers().length, 10);i++) {
            LeaderboardUser user = DataSource.data.getUsers()[i];
            OfflinePlayer p = Bukkit.getOfflinePlayer(user.getMinecraft_id());
            if(user.getPoints()<=0|| user.getMinecraft_id().equals("")) continue;

            if(user.getMinecraft_id().equalsIgnoreCase(player.getName())) {
                isPlayerInTop = true;
                res.add(getPlacePrefix(i+1) + LegacyFormat.AQUA + p.getName() + LegacyFormat.DARK_GRAY + " (" + LegacyFormat.AQUA + user.getPoints() + LegacyFormat.DARK_GRAY + ")");
            } else {
                res.add(getPlacePrefix(i+1) + LegacyFormat.WHITE + p.getName() + LegacyFormat.DARK_GRAY + " (" + LegacyFormat.AQUA + user.getPoints() + LegacyFormat.DARK_GRAY + ")");
            }
        }
        if(!isPlayerInTop && playerUser!=null) {
            res.add("");
            res.add(getPlacePrefix(Arrays.asList(DataSource.data.getUsers()).indexOf(playerUser)+1)+LegacyFormat.AQUA+player.getName()+LegacyFormat.DARK_GRAY+" ("+LegacyFormat.AQUA+playerUser.getPoints()+LegacyFormat.DARK_GRAY+")");
        }

        if(Arrays.stream(DataSource.data.getUsers()).filter(user -> (user.getPoints()>0&& !user.getMinecraft_id().equals(""))).toArray().length>10) {
            res.add(LegacyFormat.GRAY+"+ "+(DataSource.data.getUsers().length-(isPlayerInTop?10:11))+" weitere");
        }
        res.add("");
        res.add(LegacyFormat.YELLOW+"Insg. Gebäude: "+LegacyFormat.GRAY+DataSource.data.getBuilds().length);
        res.add(LegacyFormat.YELLOW+"Insg. Punkte: "+LegacyFormat.GRAY+Math.round(DataSource.data.getPoints()));

        board.updateLines(res);
    }

    private static String getPlacePrefix(int i) {
        switch(i) {
            case 1: return LegacyFormat.YELLOW+"ր "+LegacyFormat.RESET;
            case 2: return LegacyFormat.GRAY +"փ "+LegacyFormat.RESET;
            case 3: return LegacyFormat.GOLD +"ք "+LegacyFormat.RESET;
            default: return LegacyFormat.GRAY+""+(i) + ". "+LegacyFormat.RESET;
        }
    }
}
