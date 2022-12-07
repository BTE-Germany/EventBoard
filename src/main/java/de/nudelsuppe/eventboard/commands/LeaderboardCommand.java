package de.nudelsuppe.eventboard.commands;

import com.google.gson.Gson;
import de.nudelsuppe.eventboard.Main;
import de.nudelsuppe.eventboard.board.BoardManager;
import de.nudelsuppe.eventboard.data.DataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LeaderboardCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("event.leaderboard.view")) return true;
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length==0) {
                BoardManager.toggleBoard(player);
                player.sendMessage(Main.PREFIX+"Leaderboard visibility toggled.");
                return true;
            }

            if(args[0].equalsIgnoreCase("toggle")) {
                BoardManager.toggleBoard(player);
                player.sendMessage(Main.PREFIX+"Leaderboard visibility toggled.");
                return true;
            }

            // Admin Commands
            if(!sender.hasPermission("event.leaderboard.admin")) return true;
            if(args[0].equalsIgnoreCase("reload")) {
                DataSource.getData();
                BoardManager.updateBoards();
                player.sendMessage(Main.PREFIX+"Data and Boards updated.");
                return true;
            }


            if(args[0].equalsIgnoreCase("data")) {
                Gson g = new Gson();
                player.sendMessage(Main.PREFIX+g.toJson(DataSource.data));
                return true;
            }
            if(args[0].equalsIgnoreCase("boards")) {
                player.sendMessage(Main.PREFIX+"List of players currently seeing the board:");
                for(UUID u:BoardManager.getBoards().keySet()) {
                    Player p = Bukkit.getPlayer(u);
                    player.sendMessage(ChatColor.GRAY+"- "+p.getName());
                }
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED+"Only Players can execute this command.");
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        if(args.length>1) return completions;

        StringUtil.copyPartialMatches(args[0], Arrays.asList("toggle", "reload", "data", "boards"), completions);
        Collections.sort(completions);
        return completions;
    }
}
