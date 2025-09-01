package me.orel.class_system.classes;

import me.orel.MegaWallzFFA;
import me.orel.class_system.Class;
import me.orel.class_system.ClassType;
import me.orel.class_system.abilities.Ability;
import me.orel.class_system.UpgradeType;
import me.orel.class_system.abilities.IronPunch;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Herobrine extends Class {

    private final MegaWallzFFA plugin;

    public Herobrine(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

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
    public ClassType getType() {
        return ClassType.HERO;
    }

    @Override
    public HashMap<Integer, ItemStack> getStartingItems(int upgradeLevel) {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(0, new ItemStack(Material.DIAMOND_SWORD));
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
        return new IronPunch(plugin);
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
    public int getUpgradePrice(UpgradeType type, int level) {
        return 150 * level;
    }
}
