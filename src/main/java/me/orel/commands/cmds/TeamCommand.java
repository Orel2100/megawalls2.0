package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import me.orel.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TeamCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public TeamCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can manage teams.");
            return true;
        }

        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }

        Player player = (Player) sender;
        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "create":
                handleCreate(player, args);
                break;
            case "invite":
                handleInvite(player, args);
                break;
            case "accept":
                handleAccept(player, args);
                break;
            case "deny":
                handleDeny(player, args);
                break;
            case "leave":
                handleLeave(player);
                break;
            case "disband":
                handleDisband(player);
                break;
            default:
                sendHelpMessage(sender);
                break;
        }

        return true;
    }

    private void handleCreate(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /team create <name>");
            return;
        }
        if (plugin.getTeamManager().getTeam(player) != null) {
            player.sendMessage(ChatColor.RED + "You are already in a team.");
            return;
        }
        String teamName = args[1];
        if (plugin.getTeamManager().getTeam(teamName) != null) {
            player.sendMessage(ChatColor.RED + "A team with that name already exists.");
            return;
        }
        plugin.getTeamManager().createTeam(teamName, player);
        player.sendMessage(ChatColor.GREEN + "Team '" + teamName + "' created.");
    }

    private void handleInvite(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /team invite <player>");
            return;
        }
        Team team = plugin.getTeamManager().getTeam(player);
        if (team == null || !team.isLeader(player)) {
            player.sendMessage(ChatColor.RED + "You are not the leader of a team.");
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }
        if (plugin.getTeamManager().getTeam(target) != null) {
            player.sendMessage(ChatColor.RED + "That player is already in a team.");
            return;
        }
        plugin.getTeamManager().invitePlayer(target, team.getName());
        player.sendMessage(ChatColor.GREEN + "Invitation sent to " + target.getName() + ".");
        target.sendMessage(ChatColor.GREEN + "You have been invited to join team '" + team.getName() + "'.");
        target.sendMessage(ChatColor.GREEN + "Type '/team accept' or '/team deny'.");
    }

    private void handleAccept(Player player, String[] args) {
        String teamName = plugin.getTeamManager().getInvite(player);
        if (teamName == null) {
            player.sendMessage(ChatColor.RED + "You don't have any pending invitations.");
            return;
        }
        Team team = plugin.getTeamManager().getTeam(teamName);
        if (team == null) {
            player.sendMessage(ChatColor.RED + "The team you were invited to no longer exists.");
            return;
        }
        team.addMember(player);
        plugin.getTeamManager().removeInvite(player);
        player.sendMessage(ChatColor.GREEN + "You have joined team '" + team.getName() + "'.");
        team.getMembers().stream().map(Bukkit::getPlayer).filter(Objects::nonNull)
                .forEach(member -> member.sendMessage(ChatColor.GREEN + player.getName() + " has joined the team."));
    }

    private void handleDeny(Player player, String[] args) {
        String teamName = plugin.getTeamManager().getInvite(player);
        if (teamName == null) {
            player.sendMessage(ChatColor.RED + "You don't have any pending invitations.");
            return;
        }
        plugin.getTeamManager().removeInvite(player);
        player.sendMessage(ChatColor.GREEN + "You have denied the invitation.");
    }

    private void handleLeave(Player player) {
        Team team = plugin.getTeamManager().getTeam(player);
        if (team == null) {
            player.sendMessage(ChatColor.RED + "You are not in a team.");
            return;
        }
        if (team.isLeader(player)) {
            player.sendMessage(ChatColor.RED + "You are the leader of the team. Use /team disband to disband it.");
            return;
        }
        team.removeMember(player);
        player.sendMessage(ChatColor.GREEN + "You have left the team.");
        team.getMembers().stream().map(Bukkit::getPlayer).filter(Objects::nonNull)
                .forEach(member -> member.sendMessage(ChatColor.RED + player.getName() + " has left the team."));
    }

    private void handleDisband(Player player) {
        Team team = plugin.getTeamManager().getTeam(player);
        if (team == null || !team.isLeader(player)) {
            player.sendMessage(ChatColor.RED + "You are not the leader of a team.");
            return;
        }
        team.getMembers().stream().map(Bukkit::getPlayer).filter(Objects::nonNull)
                .forEach(member -> member.sendMessage(ChatColor.RED + "The team has been disbanded."));
        plugin.getTeamManager().disbandTeam(team.getName());
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "--- Team Commands ---");
        sender.sendMessage(ChatColor.GREEN + "/team create <name>");
        sender.sendMessage(ChatColor.GREEN + "/team invite <player>");
        sender.sendMessage(ChatColor.GREEN + "/team accept");
        sender.sendMessage(ChatColor.GREEN + "/team deny");
        sender.sendMessage(ChatColor.GREEN + "/team leave");
        sender.sendMessage(ChatColor.GREEN + "/team disband");
    }
}
