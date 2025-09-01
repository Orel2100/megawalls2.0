package me.orel.class_system;

import me.orel.MegaWallzFFA;
import me.orel.player.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UpgradeGUI implements Listener {

    private final MegaWallzFFA plugin;

    public UpgradeGUI(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        MPlayer mPlayer = plugin.getPlayerManager().getMPlayer(player);
        Class selectedClass = mPlayer.getSelectedClass();
        Inventory gui = Bukkit.createInventory(null, 9 * 3, "Upgrades for " + selectedClass.getName());

        for (UpgradeType type : UpgradeType.values()) {
            int currentLevel = mPlayer.getUpgradeLevel(type);
            int price = selectedClass.getUpgradePrice(type, currentLevel);

            ItemStack item = new ItemStack(getIconForUpgrade(type));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + type.name());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Current Level: " + currentLevel);
            if (currentLevel < 9) { // Max level placeholder
                lore.add(ChatColor.GRAY + "Next Level Cost: " + price + " coins");
            } else {
                lore.add(ChatColor.GOLD + "Max Level Reached");
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            gui.addItem(item);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().startsWith("Upgrades for ")) {
            return;
        }
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        MPlayer mPlayer = plugin.getPlayerManager().getMPlayer(player);
        Class selectedClass = mPlayer.getSelectedClass();
        UpgradeType type = UpgradeType.valueOf(ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()));

        int currentLevel = mPlayer.getUpgradeLevel(type);
        if (currentLevel >= 9) {
            player.sendMessage(ChatColor.RED + "This upgrade is already at the maximum level.");
            return;
        }

        int price = selectedClass.getUpgradePrice(type, currentLevel);
        if (mPlayer.getCoins() < price) {
            player.sendMessage(ChatColor.RED + "You don't have enough coins to purchase this upgrade.");
            return;
        }

        mPlayer.addCoins(-price);
        mPlayer.setUpgradeLevel(type, currentLevel + 1);
        player.sendMessage(ChatColor.GREEN + "You have upgraded " + type.name() + " to level " + (currentLevel + 1) + "!");
        open(player); // Re-open the GUI to show the new level
    }

    private Material getIconForUpgrade(UpgradeType type) {
        switch (type) {
            case ABILITY:
                return Material.ENCHANTED_BOOK;
            case KIT:
                return Material.DIAMOND_CHESTPLATE;
            default:
                return Material.BARRIER;
        }
    }
}
