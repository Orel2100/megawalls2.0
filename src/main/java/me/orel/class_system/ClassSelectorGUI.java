package me.orel.class_system;

import me.orel.MegaWallzFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ClassSelectorGUI {

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
}
