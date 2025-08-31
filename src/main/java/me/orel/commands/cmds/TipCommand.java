package me.orel.commands.cmds;

import me.orel.MegaWallzFFA;
import me.orel.player.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TipCommand implements CommandExecutor {

    private final MegaWallzFFA plugin;

    public TipCommand(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can tip other players.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /tip <player> <amount>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        Player player = (Player) sender;
        if (player.equals(target)) {
            sender.sendMessage(ChatColor.RED + "You can't tip yourself.");
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid amount specified.");
            return true;
        }

        if (amount <= 0) {
            sender.sendMessage(ChatColor.RED + "You must tip a positive amount.");
            return true;
        }

        MPlayer tipper = plugin.getPlayerManager().getMPlayer(player);
        if (tipper.getCoins() < amount) {
            sender.sendMessage(ChatColor.RED + "You don't have enough coins to tip that much.");
            return true;
        }

        MPlayer tipped = plugin.getPlayerManager().getMPlayer(target);
        tipper.addCoins(-amount);
        tipped.addCoins(amount);

        player.sendMessage(ChatColor.GREEN + "You have tipped " + target.getName() + " " + amount + " coins.");
        target.sendMessage(ChatColor.GREEN + "You have been tipped " + amount + " coins by " + player.getName() + ".");

        return true;
    }
}
