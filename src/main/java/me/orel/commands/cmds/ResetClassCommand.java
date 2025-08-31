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

        ItemStack classSelector = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = classSelector.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Class Selector");
        classSelector.setItemMeta(meta);
        player.getInventory().setItem(4, classSelector);

        player.sendMessage(ChatColor.GREEN + "Your class has been reset.");
        return true;
    }
}
