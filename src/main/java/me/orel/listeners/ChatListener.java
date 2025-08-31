package me.orel.listeners;

import me.orel.MegaWallzFFA;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final MegaWallzFFA plugin;

    public ChatListener(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.getMuteManager().isMuted(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You are currently muted.");
            return;
        }

        if (plugin.isChatLocked() && !player.hasPermission("megawallsffa.lockchat.bypass")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Chat is currently locked.");
        }
    }
}
