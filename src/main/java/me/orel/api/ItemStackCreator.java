package me.orel.api;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemStackCreator {

    public static ItemStack createItem(ItemStack item, String name, int amount, String... lore) {
        item.setAmount(amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(ItemStack item, String name, String... lore) {
        return createItem(item, name, 1, lore);
    }
}
