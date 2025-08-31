package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LockChatCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public LockChatCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("megawallsffa.lockchat")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        plugin.toggleChatLock();
        if (plugin.isChatLocked()) {
            Bukkit.broadcastMessage(ChatColor.RED + "Chat has been locked.");
        } else {
            Bukkit.broadcastMessage(ChatColor.GREEN + "Chat has been unlocked.");
        }

        return true;
    }
}
