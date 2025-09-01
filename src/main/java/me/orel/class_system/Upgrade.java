package me.orel.class_system;

import me.orel.MegaWallzFFA;
import me.orel.api.ConfigUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Upgrade {

    private final OfflinePlayer player;
    private final UpgradeType type;
    private final Class mwClass;
    private final File playerFile;
    private final FileConfiguration config;

    public Upgrade(OfflinePlayer player, Class mwClass, UpgradeType type) {
        this.player = player;
        this.mwClass = mwClass;
        this.type = type;
        this.playerFile = new File(MegaWallzFFA.getInstance().getDataFolder(), "playerdata/" + player.getUniqueId() + ".yml");
        this.config = YamlConfiguration.loadConfiguration(playerFile);
    }

    public int getCurrentLevel() {
        return config.getInt("upgrades." + mwClass.getName() + "." + type.name(), 1);
    }

    public void upgrade() {
        int currentLevel = getCurrentLevel();
        if (currentLevel >= 9) {
            return;
        }
        config.set("upgrades." + mwClass.getName() + "." + type.name(), currentLevel + 1);
        save();
    }

    private void save() {
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
