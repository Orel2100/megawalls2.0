package me.orel.team;

import org.bukkit.entity.Player;

import java.util.*;

public class TeamManager {

    private final Map<String, Team> teams = new HashMap<>();
    private final Map<UUID, String> invites = new HashMap<>(); // Player UUID -> Team Name

    public void createTeam(String name, Player leader) {
        teams.put(name.toLowerCase(), new Team(name, leader));
    }

    public void disbandTeam(String name) {
        teams.remove(name.toLowerCase());
    }

    public Team getTeam(String name) {
        return teams.get(name.toLowerCase());
    }

    public Team getTeam(Player player) {
        for (Team team : teams.values()) {
            if (team.isMember(player)) {
                return team;
            }
        }
        return null;
    }

    public void invitePlayer(Player player, String teamName) {
        invites.put(player.getUniqueId(), teamName.toLowerCase());
    }

    public String getInvite(Player player) {
        return invites.get(player.getUniqueId());
    }

    public void removeInvite(Player player) {
        invites.remove(player.getUniqueId());
    }
}
