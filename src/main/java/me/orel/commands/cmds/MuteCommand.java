package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.concurrent.TimeUnit;

public class MuteCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public MuteCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("megawallsffa.mute")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /mute <player> <duration> [reason]");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        long durationMillis = parseDuration(args[1]);
        if (durationMillis == 0) {
            sender.sendMessage(ChatColor.RED + "Invalid duration format. Use 'perm' or a format like 10s, 5m, 2h, 7d.");
            return true;
        }

        plugin.getMuteManager().mutePlayer(target.getUniqueId(), durationMillis);
        String durationString = args[1].equalsIgnoreCase("perm") ? "permanently" : "for " + args[1];
        sender.sendMessage(ChatColor.GREEN + target.getName() + " has been " + durationString + " muted.");

        if (target.isOnline()) {
            target.getPlayer().sendMessage(ChatColor.RED + "You have been " + durationString + " muted.");
        }

        return true;
    }

    private long parseDuration(String durationStr) {
        if (durationStr.equalsIgnoreCase("perm")) {
            return -1;
        }
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
