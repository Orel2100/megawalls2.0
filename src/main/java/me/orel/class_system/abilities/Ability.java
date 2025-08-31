package me.orel.class_system.abilities;

import org.bukkit.entity.Player;

public interface Ability {

    void use(Player player);

    long getCooldown();
}
