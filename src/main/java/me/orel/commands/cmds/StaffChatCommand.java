package me.orel.commands.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("megawallsffa.staffchat")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /staffchat <message>");
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }

        String formattedMessage = ChatColor.AQUA + "[Staff] " + sender.getName() + ": " + ChatColor.WHITE + message.toString().trim();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("megawallsffa.staffchat")) {
                player.sendMessage(formattedMessage);
            }
        }

        return true;
    }
}
