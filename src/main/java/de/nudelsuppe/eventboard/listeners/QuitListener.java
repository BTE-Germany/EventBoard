package de.nudelsuppe.eventboard.listeners;

import de.nudelsuppe.eventboard.Main;
import de.nudelsuppe.eventboard.board.BoardManager;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        BoardManager.removeBoard(e.getPlayer());
    }
}
