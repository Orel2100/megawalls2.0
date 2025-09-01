package me.orel.commands;

import me.orel.MegaWallzFFA;
import me.orel.api.ConfigUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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

        if (args.length > 0) {
            Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("setlobby")) {
                ConfigUtils.setLobby(player.getLocation());
                player.sendMessage(ChatColor.GREEN + "Lobby location set!");
                return true;
            } else if (args[0].equalsIgnoreCase("setgamespawn")) {
                ConfigUtils.addGameSpawn(player.getLocation());
                player.sendMessage(ChatColor.GREEN + "Game spawn location added!");
                return true;
            }
        }

        sender.sendMessage(ChatColor.RED + "Usage: /arena <setlobby|setgamespawn>");
        return true;
    }
}
