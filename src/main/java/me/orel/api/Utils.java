package me.orel.api;

import me.orel.MegaWallzFFA;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class Utils {

    public static void addLevel(Player p, int level) {
        MegaWallzFFA plugin = MegaWallzFFA.getInstance();
        if (!plugin.getPlaying().contains(p.getName())) {
            return;
        }
        if (p.getLevel() >= 100) {
            return;
        }
        p.setLevel(Math.min(p.getLevel() + level, 100));
        p.setExp((float) p.getLevel() / 100);

        if (p.getLevel() >= 100) {
            new BukkitRunnable() {
                boolean flash = false;
                @Override
                public void run() {
                    if (p.getLevel() < 100) {
                        p.setExp((float) p.getLevel() / 100);
                        cancel();
                        return;
                    }
                    flash = !flash;
                    p.setExp(flash ? 1.0f : 0.0f);
                }
            }.runTaskTimer(plugin, 0, 10L);
        }
    }

    public static void giveLobbyItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        for(org.bukkit.potion.PotionEffect potion : player.getActivePotionEffects()) player.removePotionEffect(potion.getType());

        ItemStack skull = new ItemStack(org.bukkit.Material.PLAYER_HEAD, 1);
        SkullMeta sm = (SkullMeta) skull.getItemMeta();
        sm.setOwningPlayer(player);
        sm.setDisplayName(org.bukkit.ChatColor.GREEN + "My Profile " + org.bukkit.ChatColor.GRAY + "(Right Click)");
        sm.setLore(Arrays.asList(org.bukkit.ChatColor.GRAY + "Click to see your own stats"));
        skull.setItemMeta(sm);

        player.getInventory().setItem(8, ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.COMMAND_BLOCK), "§aClass Selector§7 (Right Click)"));
        player.getInventory().setItem(1, skull);
        player.getInventory().setItem(4, ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.CAKE), "§cPLAY!§7 (Right Click)"));

        player.updateInventory();
        player.setLevel(0);
        player.setExp(0);
        player.setMaxHealth(20.0);
        player.setFoodLevel(20);
        player.setHealth(20.0);
        player.setGameMode(GameMode.ADVENTURE);
    }

    public static ItemStack getSteaks(int amount, String name) {
        return ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.COOKED_BEEF, amount), ChatColor.AQUA + name + " Steak");
    }

    public static ItemStack getPotionRegeneration(int amount, String c) {
        ItemStack potion = new ItemStack(org.bukkit.Material.POTION, amount);
        PotionMeta pm = (PotionMeta) potion.getItemMeta();
        pm.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 2), true);
        pm.setDisplayName(ChatColor.AQUA + "Potion of Regeneration III");
        potion.setItemMeta(pm);
        return potion;
    }

    public static ItemStack getPotionHeal(int heal, int amount) {
        ItemStack h = new ItemStack(org.bukkit.Material.POTION, amount);
        PotionMeta hm = (PotionMeta) h.getItemMeta();
        hm.setDisplayName(ChatColor.AQUA + "Potion of Heal (" + heal + ChatColor.RED + "❤" + ChatColor.AQUA + ")");
        h.setItemMeta(hm);
        return h;
    }
}
