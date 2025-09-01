package me.orel.listeners;

import me.orel.MegaWallzFFA;
import me.orel.player.MPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    private final MegaWallzFFA plugin;

    public DamageListener(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player damager = (Player) event.getDamager();
        MPlayer mDamager = plugin.getPlayerManager().getMPlayer(damager);

        if (mDamager.getSelectedClass() != null) {
            me.orel.api.Utils.addLevel(damager, mDamager.getSelectedClass().getEnergyPerHit());
        }
    }
}
