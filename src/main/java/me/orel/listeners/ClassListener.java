package me.orel.listeners;

import me.orel.MegaWallzFFA;
import me.orel.class_system.UpgradeType;
import me.orel.player.MPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClassListener implements Listener {

    private final MegaWallzFFA plugin;
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public ClassListener(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getItem();
            if (item != null && item.getType().name().endsWith("_SWORD")) {
                MPlayer mPlayer = plugin.getPlayerManager().getMPlayer(player);
                if (mPlayer.getSelectedClass() != null) {
                    if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains(mPlayer.getSelectedClass().getName())) {
                        if (player.getLevel() >= 100) {
                        long now = System.currentTimeMillis();
                        long lastUse = cooldowns.getOrDefault(player.getUniqueId(), 0L);
                        long cooldown = mPlayer.getSelectedClass().getAbility().getCooldown();
                        if (now - lastUse > cooldown) {
                            int upgradeLevel = mPlayer.getUpgradeLevel(UpgradeType.ABILITY);
                            mPlayer.getSelectedClass().getAbility().use(player, upgradeLevel);
                            player.setLevel(0);
                            player.setExp(0);
                            cooldowns.put(player.getUniqueId(), now);
                            player.sendMessage(ChatColor.GREEN + "You used your ability!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Your ability is on cooldown for " + ((cooldown - (now - lastUse)) / 1000) + "s.");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough energy!");
                    }
                    }
                }
            }
        }
    }
}
