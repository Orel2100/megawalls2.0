package me.orel.class_system.classes;

import me.orel.class_system.Class;
import me.orel.class_system.abilities.Ability;
import me.orel.class_system.abilities.Leap;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Golem extends Class {
    @Override
    public String getName() {
        return "Golem";
    }

    @Override
    public String getDescription() {
        return "A tanky class with high defense.";
    }

    @Override
    public Material getIcon() {
        return Material.IRON_BLOCK;
    }

    @Override
    public List<ItemStack> getItems() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.STONE_SWORD));
        return items;
    }

    @Override
    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Material.IRON_BOOTS),
                new ItemStack(Material.IRON_LEGGINGS),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemStack(Material.IRON_HELMET)
        };
    }

    @Override
    public Ability getAbility() {
        return new Leap();
    }

    @Override
    public int getEnergyPerHit() {
        return 10;
    }

    @Override
    public int getMaxEnergy() {
        return 100;
    }

    @Override
    public int getUpgradePrice(me.orel.class_system.UpgradeType type, int level) {
        return 100 * level; // Simple placeholder pricing
    }
}
