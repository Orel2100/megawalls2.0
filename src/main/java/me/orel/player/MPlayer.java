package me.orel.player;

import org.bukkit.entity.Player;

import java.util.UUID;

public class MPlayer {

    private final UUID uuid;
    private final Player player;
    private int coins;
    private me.orel.class_system.Class selectedClass;

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
}
