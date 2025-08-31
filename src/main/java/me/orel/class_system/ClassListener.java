package me.orel.class_system;

import me.orel.MegaWallzFFA;
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
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getItem();
            if (item != null && item.getType() == Material.NETHER_STAR && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.GREEN + "Class Selector")) {
                    plugin.getClassSelectorGUI().open(event.getPlayer());
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
