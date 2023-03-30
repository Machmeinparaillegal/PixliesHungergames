package org.pixlies.pixlieshungergames;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.pixlies.pixlieshungergames.Objects.Game;
import org.pixlies.pixlieshungergames.commands.JoinCommand;
import org.pixlies.pixlieshungergames.commands.SetspawnCommand;
import org.pixlies.pixlieshungergames.commands.StartCommand;
import org.pixlies.pixlieshungergames.commands.StopCommand;
import org.pixlies.pixlieshungergames.listeners.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class PixliesHungergames extends JavaPlugin {

    public static PixliesHungergames instance = null;
    public static String prefix = "§bHungergames | ";
    public Game game;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;
        loadConfig();
        registerCommands();
        registerEvents();
        JavaPlugin.getPlugin(PixliesHungergames.class).getLogger().log(Level.FINE, "Plugin enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        JavaPlugin.getPlugin(PixliesHungergames.class).getLogger().log(Level.SEVERE, "Plugin disabled!");
    }

    public void loadConfig() {
        File file = new File("plugins/PixliesHungergames", "config.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            cfg.set("prefix", prefix);
            try {
                cfg.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }



    public void registerCommands(){
        getCommand("setspawnpoints").setExecutor(new SetspawnCommand());
        getCommand("start").setExecutor(new StartCommand());
        getCommand("join").setExecutor(new JoinCommand());
        getCommand("gamestop").setExecutor(new StopCommand());
    }
    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new onDeath(), this);
        getServer().getPluginManager().registerEvents(new onGameStart(), this);
        getServer().getPluginManager().registerEvents(new onMove(), this);
        getServer().getPluginManager().registerEvents(new onBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new onPlayerHit(), this);
        getServer().getPluginManager().registerEvents(new onInteract(), this);
    }
}

