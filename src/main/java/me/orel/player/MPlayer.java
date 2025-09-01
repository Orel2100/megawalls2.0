package me.orel.player;

import org.bukkit.entity.Player;

import java.util.UUID;

public class MPlayer {

    private final UUID uuid;
    private final Player player;

    public MPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.player = player;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer() {
        return player;
    }
}
