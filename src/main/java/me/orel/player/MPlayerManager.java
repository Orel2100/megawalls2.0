package me.orel.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import me.orel.MegaWallzFFA;
import me.orel.class_system.UpgradeType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import me.orel.MegaWallzFFA;
import me.orel.class_system.UpgradeType;
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
            mPlayer.setCoins(config.getInt("coins", 0));
            String className = config.getString("selected-class");
            if (className != null) {
                mPlayer.setSelectedClass(plugin.getClassManager().getClass(className));
            }
            if (config.isConfigurationSection("upgrades")) {
                for (String typeName : config.getConfigurationSection("upgrades").getKeys(false)) {
                    UpgradeType type = UpgradeType.valueOf(typeName);
                    int level = config.getInt("upgrades." + typeName);
                    mPlayer.setUpgradeLevel(type, level);
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
        config.set("coins", mPlayer.getCoins());
        if (mPlayer.getSelectedClass() != null) {
            config.set("selected-class", mPlayer.getSelectedClass().getName());
        }
        for (Map.Entry<UpgradeType, Integer> entry : mPlayer.getUpgrades().entrySet()) {
            config.set("upgrades." + entry.getKey().name(), entry.getValue());
        }
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
