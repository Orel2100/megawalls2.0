package me.orel.class_system.abilities;

import me.orel.MegaWallzFFA;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class IronPunch implements Ability {

    private final MegaWallzFFA plugin;

    public IronPunch(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public void use(Player player, int upgradeLevel) {
        double damage = 0.5 + (0.5 * upgradeLevel);

        for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
            if (entity instanceof LivingEntity && !entity.equals(player)) {
                // Not checking for teams yet, as the team system is not implemented in this clean version.
                ((LivingEntity) entity).damage(damage, player);
                Vector knockback = entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                entity.setVelocity(knockback);
            }
        }
    }

    @Override
    public long getCooldown() {
        return 10000; // 10 seconds
    }
}
