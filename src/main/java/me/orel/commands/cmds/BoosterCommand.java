package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class BoosterCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public BoosterCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("megawallsffa.booster")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /booster <multiplier> <duration>");
            return true;
        }

        double multiplier;
        try {
            multiplier = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid multiplier specified.");
            return true;
        }

        long durationMillis = parseDuration(args[1]);
        if (durationMillis == 0) {
            sender.sendMessage(ChatColor.RED + "Invalid duration format. Use a format like 10s, 5m, 2h, 7d.");
            return true;
        }

        plugin.getBoosterManager().activateBooster(((Player) sender).getUniqueId(), multiplier, durationMillis);
        Bukkit.broadcastMessage(ChatColor.GOLD + "A " + multiplier + "x coin booster has been activated for " + args[1] + " by " + sender.getName() + "!");

        return true;
    }

    private long parseDuration(String durationStr) {
        try {
            long value = Long.parseLong(durationStr.substring(0, durationStr.length() - 1));
            char unit = durationStr.charAt(durationStr.length() - 1);
            switch (unit) {
                case 's':
                    return TimeUnit.SECONDS.toMillis(value);
                case 'm':
                    return TimeUnit.MINUTES.toMillis(value);
                case 'h':
                    return TimeUnit.HOURS.toMillis(value);
                case 'd':
                    return TimeUnit.DAYS.toMillis(value);
                default:
                    return 0;
            }
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
