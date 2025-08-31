package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class LeaderboardCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public LeaderboardCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        File playerDataFolder = new File(plugin.getDataFolder(), "playerdata");
        if (!playerDataFolder.exists() || playerDataFolder.listFiles() == null) {
            sender.sendMessage(ChatColor.RED + "No player data found.");
            return true;
        }

        Map<String, Integer> killsMap = new HashMap<>();
        for (File playerFile : playerDataFolder.listFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
            String uuidString = playerFile.getName().replace(".yml", "");
            UUID uuid = UUID.fromString(uuidString);
            String playerName = Bukkit.getOfflinePlayer(uuid).getName();
            if (playerName != null) {
                killsMap.put(playerName, config.getInt("stats.kills", 0));
            }
        }

        List<Map.Entry<String, Integer>> sortedKills = killsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        sender.sendMessage(ChatColor.YELLOW + "--- Top 5 Kills ---");
        for (int i = 0; i < Math.min(5, sortedKills.size()); i++) {
            Map.Entry<String, Integer> entry = sortedKills.get(i);
            sender.sendMessage(ChatColor.GREEN + "#" + (i + 1) + " " + entry.getKey() + ": " + ChatColor.WHITE + entry.getValue());
        }

        return true;
    }
}
