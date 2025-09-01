package me.orel.listeners;

import me.orel.MegaWallzFFA;
import me.orel.api.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final MegaWallzFFA plugin;

    public DeathListener(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            player.spigot().respawn();
            Location lobby = ConfigUtils.getLobby();
            if (lobby != null) {
                player.teleport(lobby);
            }
        }, 1L);
    }
}
