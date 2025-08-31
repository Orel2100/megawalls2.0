package me.orel.player;

import me.orel.MegaWallzFFA;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MPlayerManager {

    private final MegaWallzFFA plugin;
    private final Map<UUID, MPlayer> players = new HashMap<>();
    private final File playerDataFolder;

    public MPlayerManager(MegaWallzFFA plugin) {
        this.plugin = plugin;
        this.playerDataFolder = new File(plugin.getDataFolder(), "playerdata");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
    }

    public void addPlayer(Player player) {
        loadPlayerData(player);
    }

    public void removePlayer(Player player) {
        savePlayerData(player);
        players.remove(player.getUniqueId());
    }

    public MPlayer getMPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public MPlayer getMPlayer(Player player) {
        return getMPlayer(player.getUniqueId());
    }

    public void loadPlayerData(Player player) {
        File playerFile = new File(playerDataFolder, player.getUniqueId() + ".yml");
        MPlayer mPlayer = new MPlayer(player);
        if (playerFile.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
            mPlayer.setKills(config.getInt("stats.kills", 0));
            mPlayer.setDeaths(config.getInt("stats.deaths", 0));
            mPlayer.setCoins(config.getInt("stats.coins", 0));
            if (config.isConfigurationSection("upgrades")) {
                for (String className : config.getConfigurationSection("upgrades").getKeys(false)) {
                    for (String typeName : config.getConfigurationSection("upgrades." + className).getKeys(false)) {
                        me.orel.class_system.UpgradeType type = me.orel.class_system.UpgradeType.valueOf(typeName);
                        int level = config.getInt("upgrades." + className + "." + typeName);
                        mPlayer.setUpgradeLevel(className, type, level);
                    }
                }
            }
        }
        players.put(player.getUniqueId(), mPlayer);
    }

    public void savePlayerData(Player player) {
        MPlayer mPlayer = getMPlayer(player);
        if (mPlayer == null) {
            return;
        }
        File playerFile = new File(playerDataFolder, player.getUniqueId() + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        config.set("stats.kills", mPlayer.getKills());
        config.set("stats.deaths", mPlayer.getDeaths());
        config.set("stats.coins", mPlayer.getCoins());
        for (Map.Entry<String, Map<me.orel.class_system.UpgradeType, Integer>> entry : mPlayer.getUpgrades().entrySet()) {
            for (Map.Entry<me.orel.class_system.UpgradeType, Integer> upgradeEntry : entry.getValue().entrySet()) {
                config.set("upgrades." + entry.getKey() + "." + upgradeEntry.getKey().name(), upgradeEntry.getValue());
            }
        }
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
