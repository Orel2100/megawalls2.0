package me.orel.player;

import org.bukkit.entity.Player;

import me.orel.class_system.UpgradeType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MPlayer {

    private final UUID uuid;
    private final Player player;
    private int coins;
    private me.orel.class_system.Class selectedClass;
    private final Map<UpgradeType, Integer> upgrades = new HashMap<>();

    public MPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.player = player;
        this.coins = 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer() {
        return player;
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

    public me.orel.class_system.Class getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(me.orel.class_system.Class selectedClass) {
        this.selectedClass = selectedClass;
    }

    public int getUpgradeLevel(UpgradeType type) {
        return upgrades.getOrDefault(type, 1);
    }

    public void setUpgradeLevel(UpgradeType type, int level) {
        upgrades.put(type, level);
    }

    public Map<UpgradeType, Integer> getUpgrades() {
        return upgrades;
    }
}
