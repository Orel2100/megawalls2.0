package me.orel.commands;

import me.orel.MegaWallzFFA;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler implements CommandExecutor {

    private final MegaWallzFFA plugin;
    private final Map<String, CommandExecutor> commands = new HashMap<>();

    public CommandHandler(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    public void register(String commandName, CommandExecutor commandExecutor) {
        commands.put(commandName.toLowerCase(), commandExecutor);
        plugin.getCommand(commandName).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandExecutor commandExecutor = commands.get(command.getName().toLowerCase());
        if (commandExecutor != null) {
            return commandExecutor.onCommand(sender, command, label, args);
        }
        return false;
    }
}
