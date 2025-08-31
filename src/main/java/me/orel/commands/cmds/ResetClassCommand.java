package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import me.orel.player.MPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ResetClassCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public ResetClassCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        MPlayer mPlayer = plugin.getPlayerManager().getMPlayer(player);

        if (mPlayer.getSelectedClass() == null) {
            player.sendMessage(ChatColor.RED + "You don't have a class selected.");
            return true;
        }

        mPlayer.setSelectedClass(null);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        // Give lobby items
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

        player.sendMessage(ChatColor.GREEN + "Your class has been reset.");
        return true;
    }
}
