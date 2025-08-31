package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReplyCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public ReplyCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can reply to messages.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /reply <message>");
            return true;
        }

        Player player = (Player) sender;
        UUID lastMessagedUUID = plugin.getLastMessaged().get(player.getUniqueId());
        if (lastMessagedUUID == null) {
            sender.sendMessage(ChatColor.RED + "You have no one to reply to.");
            return true;
        }

        Player target = Bukkit.getPlayer(lastMessagedUUID);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "The player you were talking to is no longer online.");
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }

        String formattedMessage = message.toString().trim();
        player.sendMessage(ChatColor.LIGHT_PURPLE + "To " + target.getName() + ": " + ChatColor.WHITE + formattedMessage);
        target.sendMessage(ChatColor.LIGHT_PURPLE + "From " + player.getName() + ": " + ChatColor.WHITE + formattedMessage);

        plugin.getLastMessaged().put(player.getUniqueId(), target.getUniqueId());
        plugin.getLastMessaged().put(target.getUniqueId(), player.getUniqueId());

        return true;
    }
}
