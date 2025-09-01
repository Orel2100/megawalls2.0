package me.orel;

import me.orel.class_system.ClassManager;
import me.orel.commands.CommandHandler;
import me.orel.player.MPlayerManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public final class MegaWallzFFA extends JavaPlugin implements Listener {

    private static MegaWallzFFA instance;
    private final List<String> playing = new ArrayList<>();
    private MPlayerManager playerManager;
    private CommandHandler commandHandler;
    private ClassManager classManager;
    private me.orel.class_system.ClassSelectorGUI classSelectorGUI;

    @Override
    public void onEnable() {
        instance = this;
        playerManager = new MPlayerManager(this);
        commandHandler = new CommandHandler(this);
        classManager = new ClassManager();
        classSelectorGUI = new me.orel.class_system.ClassSelectorGUI(this);
        classManager.registerClass(new me.orel.class_system.classes.Golem(this));
        classManager.registerClass(new me.orel.class_system.classes.Herobrine(this));

        saveDefaultConfig();

        commandHandler.register("arena", new me.orel.commands.ArenaCommand(this));
        commandHandler.register("upgrade", new me.orel.commands.UpgradeCommand(this));

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new me.orel.listeners.DeathListener(this), this);
        getServer().getPluginManager().registerEvents(new me.orel.listeners.LobbyListener(this), this);
        getServer().getPluginManager().registerEvents(new me.orel.listeners.DamageListener(this), this);
        getServer().getPluginManager().registerEvents(new me.orel.listeners.ClassListener(this), this);
        getServer().getPluginManager().registerEvents(classSelectorGUI, this);
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
        me.orel.api.Utils.giveLobbyItems(event.getPlayer());
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

    public ClassManager getClassManager() {
        return classManager;
    }

    public me.orel.class_system.ClassSelectorGUI getClassSelectorGUI() {
        return classSelectorGUI;
    }

    public List<String> getPlaying() {
        return playing;
    }

    public void randomSpawn(Player p) {
        java.util.List<Location> gameSpawns = me.orel.api.ConfigUtils.getGameSpawns();
        if (gameSpawns == null || gameSpawns.isEmpty()) {
            p.sendMessage(org.bukkit.ChatColor.RED + "There are no game spawns set!");
            return;
        }
        if (getPlayerManager().getMPlayer(p).getSelectedClass() == null) {
            p.sendMessage(org.bukkit.ChatColor.RED + "You haven't selected a class!");
            return;
        }

        int random = new java.util.Random().nextInt(gameSpawns.size());
        p.teleport(gameSpawns.get(random));
        getPlayerManager().getMPlayer(p).getSelectedClass().apply(p);

        if (!playing.contains(p.getName())) {
            playing.add(p.getName());
        }
    }
}
