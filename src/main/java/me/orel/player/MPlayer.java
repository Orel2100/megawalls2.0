package me.orel.player;

import me.orel.class_system.Class;
import me.orel.class_system.UpgradeType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MPlayer {

    private final UUID uuid;
    private final Player player;
    private Class selectedClass;
    private int kills;
    private int deaths;
    private int coins;
    private int energy;
    private long lastAbilityUse;
    private final Map<String, Map<UpgradeType, Integer>> upgrades = new HashMap<>();

    public MPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.player = player;
        this.kills = 0;
        this.deaths = 0;
        this.coins = 0;
        this.energy = 0;
        this.lastAbilityUse = 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer() {
        return player;
    }

    public Class getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(Class selectedClass) {
        this.selectedClass = selectedClass;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void addKill() {
        this.kills++;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void addDeath() {
        this.deaths++;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void addEnergy(int energy) {
        this.energy = Math.min(this.energy + energy, getSelectedClass().getMaxEnergy());
    }

    public long getLastAbilityUse() {
        return lastAbilityUse;
    }

    public void setLastAbilityUse(long lastAbilityUse) {
        this.lastAbilityUse = lastAbilityUse;
    }

    public int getUpgradeLevel(String className, UpgradeType type) {
        return upgrades.getOrDefault(className, new HashMap<>()).getOrDefault(type, 1);
    }

    public void setUpgradeLevel(String className, UpgradeType type, int level) {
        upgrades.computeIfAbsent(className, k -> new HashMap<>()).put(type, level);
    }

    public Map<String, Map<UpgradeType, Integer>> getUpgrades() {
        return upgrades;
    }
}
