package me.orel.class_system;

import me.orel.class_system.abilities.Ability;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class Class {

    public abstract String getName();
    public abstract String getDescription();
    public abstract Material getIcon();
    public abstract List<ItemStack> getItems();
    public abstract ItemStack[] getArmor();
    public abstract Ability getAbility();
    public abstract int getEnergyPerHit();
    public abstract int getMaxEnergy();
    public abstract int getUpgradePrice(UpgradeType type, int level);

}
