package me.orel.listeners;

import me.orel.MegaWallzFFA;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import me.orel.player.MPlayer;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LobbyListener implements Listener {

    private final MegaWallzFFA plugin;

    public LobbyListener(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getItem();
            if (item == null || item.getType() == Material.AIR || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
                return;
            }

            String displayName = item.getItemMeta().getDisplayName();
            if (displayName.equals("§aClass Selector§7 (Right Click)")) {
                event.setCancelled(true);
                plugin.getClassSelectorGUI().open(player);
            } else if (displayName.equals("§cPLAY!§7 (Right Click)")) {
                event.setCancelled(true);
                plugin.randomSpawn(player);
            }
        }
    }
}
