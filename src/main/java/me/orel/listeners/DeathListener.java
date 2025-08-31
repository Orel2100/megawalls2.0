package me.orel.listeners;

import me.orel.MegaWallzFFA;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DeathListener implements Listener {

    private final MegaWallzFFA plugin;

    public DeathListener(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        event.setDeathMessage(null);

        plugin.getPlayerManager().getMPlayer(player).addDeath();

        if (killer != null) {
            double multiplier = plugin.getBoosterManager().getCurrentMultiplier();
            int coinsToGive = (int) (10 * multiplier);
            plugin.getPlayerManager().getMPlayer(killer).addKill();
            plugin.getPlayerManager().getMPlayer(killer).addCoins(coinsToGive);
            killer.sendMessage(ChatColor.GOLD + "+" + coinsToGive + " coins");
            event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.YELLOW + " was slain by " + ChatColor.RED + killer.getName() + ChatColor.YELLOW + ".");
        } else {
            event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.YELLOW + " died.");
        }

        // Respawn the player
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            player.spigot().respawn();
            if (MegaWallzFFA.getFFALobby() != null) {
                player.teleport(MegaWallzFFA.getFFALobby());
            }
            // Give lobby items
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            for(org.bukkit.potion.PotionEffect potion : player.getActivePotionEffects()) player.removePotionEffect(potion.getType());

            ItemStack skull = new ItemStack(org.bukkit.Material.PLAYER_HEAD,1,(byte)3);
            org.bukkit.inventory.meta.SkullMeta sm = (org.bukkit.inventory.meta.SkullMeta) skull.getItemMeta();
            sm.setOwner(player.getName());
            sm.setDisplayName(org.bukkit.ChatColor.GREEN + "My Profile " + org.bukkit.ChatColor.GRAY + "(Right Click)");
            sm.setLore(java.util.Arrays.asList(org.bukkit.ChatColor.GRAY + "Click to see your own stats"));
            skull.setItemMeta(sm);

            player.getInventory().setItem(8, me.orel.api.ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.COMMAND_BLOCK), "§aClass Selector§7 (Right Click)"));
            player.getInventory().setItem(1, skull);
            player.getInventory().setItem(7, me.orel.api.ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.EMERALD), "§aShop§7 (Right Click)"));
            player.getInventory().setItem(4, me.orel.api.ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.CAKE), "§cPLAY!§7 (Right Click)"));
            if(player.hasPermission("megawalls.spectate")) {
                player.getInventory().setItem(5, me.orel.api.ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.GOLD_NUGGET), "§aStaff Spectate Item§7 (Right Click)"));
            }
            player.getInventory().setItem(0, me.orel.api.ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.COMPASS), "§aGame Menu§7 (Right Click)"));
        }, 1L);
    }
}
