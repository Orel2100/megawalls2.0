package me.orel.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MPlayerManager {

    private final Map<UUID, MPlayer> players = new HashMap<>();

    public void addPlayer(Player player) {
        players.put(player.getUniqueId(), new MPlayer(player));
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }

    public MPlayer getMPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public MPlayer getMPlayer(Player player) {
        return getMPlayer(player.getUniqueId());
    }
}
