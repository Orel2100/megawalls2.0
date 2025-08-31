package me.orel.class_system.classes;

import me.orel.class_system.Class;
import me.orel.class_system.abilities.Ability;
import me.orel.class_system.abilities.IronPunch;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Herobrine extends Class {
    @Override
    public String getName() {
        return "Herobrine";
    }

    @Override
    public String getDescription() {
        return "A powerful class with a sharp sword.";
    }

    @Override
    public Material getIcon() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public List<ItemStack> getItems() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.DIAMOND_SWORD));
        return items;
    }

    @Override
    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Material.CHAINMAIL_BOOTS),
                new ItemStack(Material.CHAINMAIL_LEGGINGS),
                new ItemStack(Material.CHAINMAIL_CHESTPLATE),
                new ItemStack(Material.CHAINMAIL_HELMET)
        };
    }

    @Override
    public Ability getAbility() {
        return new IronPunch();
    }

    @Override
    public int getEnergyPerHit() {
        return 5;
    }

    @Override
    public int getMaxEnergy() {
        return 50;
    }

    @Override
    public int getUpgradePrice(me.orel.class_system.UpgradeType type, int level) {
        return 150 * level; // Slightly more expensive
    }
}
