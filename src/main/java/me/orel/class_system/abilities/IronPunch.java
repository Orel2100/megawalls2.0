package me.orel.class_system.abilities;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class IronPunch implements Ability {
    @Override
    public void use(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 100, 0)); // Strength 1 for 5 seconds
    }

    @Override
    public long getCooldown() {
        return 10000; // 10 seconds
    }
}
