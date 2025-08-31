package me.orel.class_system;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class Class {

    public abstract String getName();
    public abstract String getDescription();
    public abstract Material getIcon();
    public abstract List<ItemStack> getItems();
    public abstract ItemStack[] getArmor();

}
