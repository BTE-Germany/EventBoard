package de.nudelsuppe.eventboard.listeners;

import de.nudelsuppe.eventboard.board.BoardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        System.out.println(event.getPlayer().getUniqueId());
        BoardManager.addBoard(event.getPlayer());
    }
}
