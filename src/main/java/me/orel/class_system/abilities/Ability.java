package me.orel.class_system.abilities;

import org.bukkit.entity.Player;

public interface Ability {

    void use(Player player, int upgradeLevel);

    long getCooldown();
}
