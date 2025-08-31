package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public ArenaCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (!sender.hasPermission("megawallsffa.arena")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("setspawn")) {
            Player player = (Player) sender;
            MegaWallzFFA.setFFALobby(player.getLocation());
            player.sendMessage(ChatColor.GREEN + "Arena spawn location set!");
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /arena setspawn");
        }

        return true;
    }
}
