package me.orel.listeners;

import me.orel.MegaWallzFFA;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DeathListener implements Listener {

    private final MegaWallzFFA plugin;

    public DeathListener(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        event.setDeathMessage(null);

        plugin.getPlayerManager().getMPlayer(player).addDeath();

        if (killer != null) {
            double multiplier = plugin.getBoosterManager().getCurrentMultiplier();
            int coinsToGive = (int) (10 * multiplier);
            plugin.getPlayerManager().getMPlayer(killer).addKill();
            plugin.getPlayerManager().getMPlayer(killer).addCoins(coinsToGive);
            killer.sendMessage(ChatColor.GOLD + "+" + coinsToGive + " coins");
            event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.YELLOW + " was slain by " + ChatColor.RED + killer.getName() + ChatColor.YELLOW + ".");
        } else {
            event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.YELLOW + " died.");
        }

        // Respawn the player
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            player.spigot().respawn();
            if (MegaWallzFFA.getFFALobby() != null) {
                player.teleport(MegaWallzFFA.getFFALobby());
            }
            player.getInventory().clear();
            ItemStack classSelector = new ItemStack(Material.NETHER_STAR);
            ItemMeta meta = classSelector.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Class Selector");
            classSelector.setItemMeta(meta);
            player.getInventory().setItem(4, classSelector);
        }, 1L);
    }
}
