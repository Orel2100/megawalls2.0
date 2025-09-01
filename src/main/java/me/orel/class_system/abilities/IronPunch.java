package me.orel.class_system.abilities;

import me.orel.MegaWallzFFA;
import me.orel.api.EffectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class IronPunch implements Ability {

    private final MegaWallzFFA plugin;

    public IronPunch(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public void use(Player player, int upgradeLevel) {
        EffectUtils.createCircle(player.getLocation(), 5, 2);
        EffectUtils.createHelix(player.getLocation(), 5, 2);

        for (int x = -2; x < 4; x = x + 2) {
            for (int z = -2; z < 4; z = z + 2) {
                if (x == 0 && z == 0) continue;
                final FallingBlock fb = player.getWorld().spawnFallingBlock(player.getLocation().add(x, 3, z), Material.IRON_BLOCK.createBlockData());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (fb.isDead() || fb.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
                            fb.remove();
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0, 1L);
            }
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            double damage = 0.5 + (0.5 * upgradeLevel);
            for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    ((LivingEntity) entity).damage(damage, player);
                    Vector knockback = entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                    entity.setVelocity(knockback);
                }
            }
        }, 10L);
    }

    @Override
    public long getCooldown() {
        return 10000; // 10 seconds
    }
}
