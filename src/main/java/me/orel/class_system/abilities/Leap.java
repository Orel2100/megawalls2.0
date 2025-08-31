package me.orel.class_system.abilities;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Leap implements Ability {
    @Override
    public void use(Player player) {
        player.setVelocity(player.getLocation().getDirection().multiply(1.5));
        player.setVelocity(new Vector(player.getVelocity().getX(), 1.0D, player.getVelocity().getZ()));
    }

    @Override
    public long getCooldown() {
        return 5000; // 5 seconds
    }
}
