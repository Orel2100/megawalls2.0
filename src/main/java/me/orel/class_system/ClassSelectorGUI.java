package me.orel.class_system;

import me.orel.MegaWallzFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import me.orel.player.MPlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collections;

public class ClassSelectorGUI implements Listener {

    private final MegaWallzFFA plugin;

    public ClassSelectorGUI(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9 * 3, "Select a Class");

        for (Class mwClass : plugin.getClassManager().getClasses()) {
            ItemStack item = new ItemStack(mwClass.getIcon());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + mwClass.getName());
            meta.setLore(Collections.singletonList(ChatColor.GRAY + mwClass.getDescription()));
            item.setItemMeta(meta);
            gui.addItem(item);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Select a Class")) {
            return;
        }
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        String className = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
        Class selectedClass = plugin.getClassManager().getClass(className);
        if (selectedClass != null) {
            MPlayer mPlayer = plugin.getPlayerManager().getMPlayer(player);
            mPlayer.setSelectedClass(selectedClass);
            player.sendMessage(ChatColor.GREEN + "You have selected the " + selectedClass.getName() + " class.");
            player.closeInventory();
        }
    }
}
