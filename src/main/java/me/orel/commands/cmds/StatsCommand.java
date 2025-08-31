package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import me.orel.player.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public StatsCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Please specify a player to view their stats.");
                return true;
            }
            Player player = (Player) sender;
            MPlayer mPlayer = plugin.getPlayerManager().getMPlayer(player);
            sendStatsMessage(sender, player.getName(), mPlayer);
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        if (target.isOnline()) {
            MPlayer mPlayer = plugin.getPlayerManager().getMPlayer(target.getPlayer());
            sendStatsMessage(sender, target.getName(), mPlayer);
        } else {
            // Load stats for offline player
            // This is a bit more complex, will implement if needed.
            // For now, only online players are supported.
            sender.sendMessage(ChatColor.RED + "Stats for offline players are not supported yet.");
        }

        return true;
    }

    private void sendStatsMessage(CommandSender sender, String name, MPlayer mPlayer) {
        sender.sendMessage(ChatColor.YELLOW + "--- Stats for " + name + " ---");
        sender.sendMessage(ChatColor.GREEN + "Kills: " + ChatColor.WHITE + mPlayer.getKills());
        sender.sendMessage(ChatColor.GREEN + "Deaths: " + ChatColor.WHITE + mPlayer.getDeaths());
        sender.sendMessage(ChatColor.GREEN + "Coins: " + ChatColor.WHITE + mPlayer.getCoins());
    }
}
