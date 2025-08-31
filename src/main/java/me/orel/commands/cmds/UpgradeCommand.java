package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import me.orel.class_system.UpgradeGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UpgradeCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public UpgradeCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        if (plugin.getPlayerManager().getMPlayer(player).getSelectedClass() == null) {
            player.sendMessage(ChatColor.RED + "You must have a class selected to upgrade it.");
            return true;
        }

        new UpgradeGUI(plugin).open(player);
        return true;
    }
}
