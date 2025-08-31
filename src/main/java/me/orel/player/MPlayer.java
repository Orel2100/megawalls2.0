package me.orel.player;

import me.orel.class_system.Class;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MPlayer {

    private final UUID uuid;
    private final Player player;
    private Class selectedClass;
    private int kills;
    private int deaths;
    private int coins;

    public MPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.player = player;
        this.kills = 0;
        this.deaths = 0;
        this.coins = 0;
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
}
