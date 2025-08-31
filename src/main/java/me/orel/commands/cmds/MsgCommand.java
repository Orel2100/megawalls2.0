package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MsgCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public MsgCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can send messages.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /msg <player> <message>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        Player player = (Player) sender;
        if (player.equals(target)) {
            sender.sendMessage(ChatColor.RED + "You can't message yourself.");
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }

        String formattedMessage = message.toString().trim();
        player.sendMessage(ChatColor.LIGHT_PURPLE + "To " + target.getName() + ": " + ChatColor.WHITE + formattedMessage);
        target.sendMessage(ChatColor.LIGHT_PURPLE + "From " + player.getName() + ": " + ChatColor.WHITE + formattedMessage);

        plugin.getLastMessaged().put(player.getUniqueId(), target.getUniqueId());
        plugin.getLastMessaged().put(target.getUniqueId(), player.getUniqueId());

        return true;
    }
}
