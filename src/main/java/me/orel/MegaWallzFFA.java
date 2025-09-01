package me.orel;

import me.orel.commands.CommandHandler;
import me.orel.player.MPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MegaWallzFFA extends JavaPlugin implements Listener {

    private static MegaWallzFFA instance;
    private MPlayerManager playerManager;
    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        instance = this;
        playerManager = new MPlayerManager();
        commandHandler = new CommandHandler(this);

        saveDefaultConfig();

        commandHandler.register("arena", new me.orel.commands.ArenaCommand(this));

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new me.orel.listeners.DeathListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        playerManager.addPlayer(event.getPlayer());
        org.bukkit.Location lobby = me.orel.api.ConfigUtils.getLobby();
        if (lobby != null) {
            event.getPlayer().teleport(lobby);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerManager.removePlayer(event.getPlayer());
    }

    public static MegaWallzFFA getInstance() {
        return instance;
    }

    public MPlayerManager getPlayerManager() {
        return playerManager;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
