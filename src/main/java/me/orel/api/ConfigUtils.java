package me.orel.api;

import me.orel.MegaWallzFFA;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    private static final MegaWallzFFA plugin = MegaWallzFFA.getInstance();

    public static String locationToString(Location loc) {
        return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
    }

    public static Location stringToLocation(String locString) {
        String[] loc = locString.split(",");
        return new Location(
                Bukkit.getWorld(loc[0]),
                Double.parseDouble(loc[1]),
                Double.parseDouble(loc[2]),
                Double.parseDouble(loc[3]),
                Float.parseFloat(loc[4]),
                Float.parseFloat(loc[5])
        );
    }

    public static void setLobby(Location loc) {
        plugin.getConfig().set("lobby-location", locationToString(loc));
        plugin.saveConfig();
    }

    public static Location getLobby() {
        String locString = plugin.getConfig().getString("lobby-location");
        if (locString == null) {
            return null;
        }
        return stringToLocation(locString);
    }

    public static void addGameSpawn(Location loc) {
        List<String> spawns = plugin.getConfig().getStringList("game-spawns");
        spawns.add(locationToString(loc));
        plugin.getConfig().set("game-spawns", spawns);
        plugin.saveConfig();
    }

    public static List<Location> getGameSpawns() {
        List<String> spawnStrings = plugin.getConfig().getStringList("game-spawns");
        List<Location> spawns = new ArrayList<>();
        for (String s : spawnStrings) {
            spawns.add(stringToLocation(s));
        }
        return spawns;
    }
}
