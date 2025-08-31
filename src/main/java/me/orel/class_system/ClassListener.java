package me.orel.class_system;

import me.orel.MegaWallzFFA;
import me.orel.player.MPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClassListener implements Listener {

    private final MegaWallzFFA plugin;

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
                    if (mPlayer.getEnergy() >= mPlayer.getSelectedClass().getMaxEnergy()) {
                        long now = System.currentTimeMillis();
                        long lastUse = mPlayer.getLastAbilityUse();
                        long cooldown = mPlayer.getSelectedClass().getAbility().getCooldown();
                        if (now - lastUse > cooldown) {
                            mPlayer.getSelectedClass().getAbility().use(player);
                            mPlayer.setEnergy(0);
                            mPlayer.setLastAbilityUse(now);
                            player.sendMessage(ChatColor.GREEN + "You used your ability!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Your ability is on cooldown for " + ((cooldown - (now - lastUse)) / 1000) + "s.");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough energy!");
                    }
                }
            } else if (item != null && item.getType() == Material.COMMAND_BLOCK && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasDisplayName() && meta.getDisplayName().equals("§aClass Selector§7 (Right Click)")) {
                    plugin.getClassSelectorGUI().open(player);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Select a Class")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }
            Player player = (Player) event.getWhoClicked();
            String className = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
            Class selectedClass = plugin.getClassManager().getClass(className);
            if (selectedClass != null) {
                plugin.getPlayerManager().getMPlayer(player).setSelectedClass(selectedClass);
                player.getInventory().clear();
                for (ItemStack item : selectedClass.getItems()) {
                    player.getInventory().addItem(item);
                }
                player.getInventory().setArmorContents(selectedClass.getArmor());
                player.sendMessage(ChatColor.GREEN + "You have selected the " + selectedClass.getName() + " class!");
                player.closeInventory();
            }
        }
    }
}
