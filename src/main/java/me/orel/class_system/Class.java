package me.orel.class_system;

import org.bukkit.Material;
import me.orel.class_system.abilities.Ability;
import org.bukkit.Material;
import me.orel.MegaWallzFFA;
import me.orel.player.MPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public abstract class Class {

    public void apply(Player player) {
        MegaWallzFFA plugin = MegaWallzFFA.getInstance();
        MPlayer mPlayer = plugin.getPlayerManager().getMPlayer(player);
        int upgradeLevel = 1; // Placeholder

        player.getInventory().clear();
        for (java.util.Map.Entry<Integer, ItemStack> entry : getStartingItems(upgradeLevel).entrySet()) {
            player.getInventory().setItem(entry.getKey(), entry.getValue());
        }
        player.getInventory().setArmorContents(getArmor());
    }

    public abstract String getName();

    public abstract String getDescription();

    public abstract Material getIcon();

    public abstract ClassType getType();

    public abstract HashMap<Integer, ItemStack> getStartingItems(int upgradeLevel);

    public abstract ItemStack[] getArmor();

    public abstract Ability getAbility();

    public abstract int getEnergyPerHit();

    public abstract int getMaxEnergy();

    public abstract int getUpgradePrice(UpgradeType type, int level);
}
