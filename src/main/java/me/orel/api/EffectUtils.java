package me.orel.api;

import me.orel.MegaWallzFFA;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

public class EffectUtils {

    public static void createCircle(Location location, float radius, int ticks) {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                int particles = 50;
                for (int j = 0; j < particles; j++) {
                    double angle, x, z;
                    angle = 2 * Math.PI * j / particles;
                    x = Math.cos(angle) * radius;
                    z = Math.sin(angle) * radius;
                    location.getWorld().spawnParticle(Particle.FLAME, location.clone().add(x, 0, z), 1, 0, 0, 0, 0);
                }
                i++;
                if (i >= ticks) {
                    cancel();
                }
            }
        }.runTaskTimer(MegaWallzFFA.getInstance(), 0, 1L);
    }

    public static void createHelix(Location location, int radius, int ticks) {
        new BukkitRunnable() {
            int i = 0;
            final int strands = 5;
            final int particles = 25;
            final float curve = 2;
            final double rotation = Math.PI / 4;

            @Override
            public void run() {
                for (int strand = 1; strand <= strands; strand++) {
                    for (int j = 1; j <= particles; j++) {
                        float ratio = (float) j / particles;
                        double angle = curve * ratio * 2 * Math.PI / strands + (2 * Math.PI * strand / strands) + rotation;
                        double x = Math.cos(angle) * ratio * radius;
                        double z = Math.sin(angle) * ratio * radius;
                        location.getWorld().spawnParticle(Particle.FLAME, location.clone().add(x, 0, z), 1, 0, 0, 0, 0);
                    }
                }
                i++;
                if (i >= ticks) {
                    cancel();
                }
            }
        }.runTaskTimer(MegaWallzFFA.getInstance(), 0, 1L);
    }
}
