package me.orel.mute;

import me.orel.MegaWallzFFA;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MuteManager {

    private final MegaWallzFFA plugin;
    private final File mutesFile;
    private final FileConfiguration mutesConfig;
    private final Map<UUID, Long> mutedPlayers = new HashMap<>(); // UUID -> End time in millis

    public MuteManager(MegaWallzFFA plugin) {
        this.plugin = plugin;
        this.mutesFile = new File(plugin.getDataFolder(), "mutes.yml");
        if (!mutesFile.exists()) {
            try {
                mutesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.mutesConfig = YamlConfiguration.loadConfiguration(mutesFile);
        loadMutes();
    }

    public void loadMutes() {
        if (mutesConfig.getConfigurationSection("mutes") == null) {
            return;
        }
        for (String uuidString : mutesConfig.getConfigurationSection("mutes").getKeys(false)) {
            UUID uuid = UUID.fromString(uuidString);
            long endTime = mutesConfig.getLong("mutes." + uuidString);
            mutedPlayers.put(uuid, endTime);
        }
    }

    public void saveMutes() {
        for (Map.Entry<UUID, Long> entry : mutedPlayers.entrySet()) {
            mutesConfig.set("mutes." + entry.getKey().toString(), entry.getValue());
        }
        try {
            mutesConfig.save(mutesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mutePlayer(UUID uuid, long durationMillis) {
        long endTime = (durationMillis == -1) ? -1 : System.currentTimeMillis() + durationMillis;
        mutedPlayers.put(uuid, endTime);
    }

    public void unmutePlayer(UUID uuid) {
        mutedPlayers.remove(uuid);
        mutesConfig.set("mutes." + uuid.toString(), null);
    }

    public boolean isMuted(UUID uuid) {
        if (!mutedPlayers.containsKey(uuid)) {
            return false;
        }
        long endTime = mutedPlayers.get(uuid);
        if (endTime == -1) {
            return true; // Permanent mute
        }
        if (System.currentTimeMillis() < endTime) {
            return true; // Temporary mute still active
        } else {
            unmutePlayer(uuid); // Mute expired
            return false;
        }
    }

    public long getMuteEndTime(UUID uuid) {
        return mutedPlayers.getOrDefault(uuid, 0L);
    }
}
