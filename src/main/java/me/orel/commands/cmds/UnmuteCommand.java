package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnmuteCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public UnmuteCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("megawallsffa.unmute")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /unmute <player>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        if (!plugin.getMuteManager().isMuted(target.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "That player is not muted.");
            return true;
        }

        plugin.getMuteManager().unmutePlayer(target.getUniqueId());
        sender.sendMessage(ChatColor.GREEN + target.getName() + " has been unmuted.");

        if (target.isOnline()) {
            target.getPlayer().sendMessage(ChatColor.GREEN + "You have been unmuted.");
        }

        return true;
    }
}
