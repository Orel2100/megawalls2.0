package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.concurrent.TimeUnit;

public class MuteCheckCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public MuteCheckCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("megawallsffa.mcheck")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /mcheck <player>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        if (plugin.getMuteManager().isMuted(target.getUniqueId())) {
            long endTime = plugin.getMuteManager().getMuteEndTime(target.getUniqueId());
            if (endTime == -1) {
                sender.sendMessage(ChatColor.RED + target.getName() + " is permanently muted.");
            } else {
                long remainingMillis = endTime - System.currentTimeMillis();
                String remainingTime = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(remainingMillis),
                        TimeUnit.MILLISECONDS.toMinutes(remainingMillis) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(remainingMillis) % 60);
                sender.sendMessage(ChatColor.RED + target.getName() + " is muted for another " + remainingTime + ".");
            }
        } else {
            sender.sendMessage(ChatColor.GREEN + target.getName() + " is not muted.");
        }

        return true;
    }
}
