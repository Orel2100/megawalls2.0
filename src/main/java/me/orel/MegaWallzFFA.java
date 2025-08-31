package me.orel;

import me.orel.class_system.ClassManager;
import me.orel.class_system.ClassSelectorGUI;
import me.orel.commands.CommandHandler;
import me.orel.config.Configuration;
import me.orel.player.MPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import me.orel.booster.BoosterManager;
import me.orel.mute.MuteManager;
import me.orel.api.ItemStackCreator;
import me.orel.team.TeamManager;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class MegaWallzFFA extends JavaPlugin implements Listener {

    private static MegaWallzFFA instance;
    private final Map<UUID, UUID> lastMessaged = new HashMap<>();
    private boolean chatLocked = false;
    private MPlayerManager playerManager;
    private Configuration configuration;
    private CommandHandler commandHandler;
    private ClassManager classManager;
    private ClassSelectorGUI classSelectorGUI;
    private TeamManager teamManager;
    private MuteManager muteManager;
    private BoosterManager boosterManager;

    @Override
    public void onEnable() {
        instance = this;
        playerManager = new MPlayerManager(this);
        configuration = new Configuration(this);
        commandHandler = new CommandHandler(this);
        classManager = new ClassManager();
        classSelectorGUI = new ClassSelectorGUI(this);
        teamManager = new TeamManager();
        muteManager = new MuteManager(this);
        boosterManager = new BoosterManager();

        configuration.load();

        commandHandler.register("megawallsffa", new me.orel.commands.cmds.MegaWallsFFACommand());
        commandHandler.register("resetclass", new me.orel.commands.cmds.ResetClassCommand(this));
        commandHandler.register("arena", new me.orel.commands.cmds.ArenaCommand(this));
        commandHandler.register("stats", new me.orel.commands.cmds.StatsCommand(this));
        commandHandler.register("leaderboard", new me.orel.commands.cmds.LeaderboardCommand(this));
        commandHandler.register("msg", new me.orel.commands.cmds.MsgCommand(this));
        commandHandler.register("reply", new me.orel.commands.cmds.ReplyCommand(this));
        commandHandler.register("team", new me.orel.commands.cmds.TeamCommand(this));
        commandHandler.register("mute", new me.orel.commands.cmds.MuteCommand(this));
        commandHandler.register("unmute", new me.orel.commands.cmds.UnmuteCommand(this));
        commandHandler.register("mcheck", new me.orel.commands.cmds.MuteCheckCommand(this));
        commandHandler.register("staffchat", new me.orel.commands.cmds.StaffChatCommand());
        commandHandler.register("lockchat", new me.orel.commands.cmds.LockChatCommand(this));
        commandHandler.register("clearchat", new me.orel.commands.cmds.ClearChatCommand());
        commandHandler.register("booster", new me.orel.commands.cmds.BoosterCommand(this));
        commandHandler.register("tip", new me.orel.commands.cmds.TipCommand(this));
        commandHandler.register("upgrade", new me.orel.commands.cmds.UpgradeCommand(this));

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new me.orel.class_system.ClassListener(this), this);
        getServer().getPluginManager().registerEvents(new me.orel.listeners.DeathListener(this), this);
        getServer().getPluginManager().registerEvents(new me.orel.listeners.DamageListener(this), this);
        getServer().getPluginManager().registerEvents(new me.orel.listeners.ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new me.orel.class_system.UpgradeGUI(this), this);
    }

    @Override
    public void onDisable() {
        muteManager.saveMutes();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        playerManager.addPlayer(event.getPlayer());
        org.bukkit.entity.Player player = event.getPlayer();

        if (getFFALobby() != null) {
            player.teleport(getFFALobby());
        }

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        for(org.bukkit.potion.PotionEffect potion : player.getActivePotionEffects()) player.removePotionEffect(potion.getType());

        ItemStack skull = new ItemStack(org.bukkit.Material.PLAYER_HEAD,1,(byte)3);
        SkullMeta sm = (SkullMeta) skull.getItemMeta();
        sm.setOwner(player.getName());
        sm.setDisplayName(org.bukkit.ChatColor.GREEN + "My Profile " + org.bukkit.ChatColor.GRAY + "(Right Click)");
        sm.setLore(Arrays.asList(org.bukkit.ChatColor.GRAY + "Click to see your own stats"));
        skull.setItemMeta(sm);

        player.getInventory().setItem(8, ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.COMMAND_BLOCK), "§aClass Selector§7 (Right Click)"));
        player.getInventory().setItem(1, skull);
        player.getInventory().setItem(7, ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.EMERALD), "§aShop§7 (Right Click)"));
        player.getInventory().setItem(4, ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.CAKE), "§cPLAY!§7 (Right Click)"));
        if(player.hasPermission("megawalls.spectate")) {
            player.getInventory().setItem(5, ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.GOLD_NUGGET), "§aStaff Spectate Item§7 (Right Click)"));
        }
        player.getInventory().setItem(0, ItemStackCreator.createItem(new ItemStack(org.bukkit.Material.COMPASS), "§aGame Menu§7 (Right Click)"));

        player.updateInventory();
        player.setLevel(0);
        player.setExp(0);
        player.setMaxHealth(20.0);
        player.setFoodLevel(20);
        player.setHealth(20.0);
        player.setGameMode(org.bukkit.GameMode.ADVENTURE);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerManager.removePlayer(event.getPlayer());
    }

    public MPlayerManager getPlayerManager() {
        return playerManager;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public ClassManager getClassManager() {
        return classManager;
    }

    public ClassSelectorGUI getClassSelectorGUI() {
        return classSelectorGUI;
    }

    public static MegaWallzFFA getInstance() {
        return instance;
    }

    public static Location getFFALobby() {
        if (getInstance().getConfig().getString("ffa-lobby") == null) {
            return null;
        }
        return me.orel.config.Configuration.convertToLocation(getInstance().getConfig().getString("ffa-lobby"));
    }

    public static void setFFALobby(Location loc) {
        getInstance().getConfig().set("ffa-lobby", me.orel.config.Configuration.convertLocation(loc));
        getInstance().saveConfig();
    }

    public Map<UUID, UUID> getLastMessaged() {
        return lastMessaged;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public MuteManager getMuteManager() {
        return muteManager;
    }

    public boolean isChatLocked() {
        return chatLocked;
    }

    public void toggleChatLock() {
        this.chatLocked = !this.chatLocked;
    }

    public BoosterManager getBoosterManager() {
        return boosterManager;
    }
}
