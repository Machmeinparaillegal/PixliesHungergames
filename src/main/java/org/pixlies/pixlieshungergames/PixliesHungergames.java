package org.pixlies.pixlieshungergames;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.pixlies.pixlieshungergames.commands.SetspawnCommand;

import java.io.File;
import java.io.IOException;

public final class PixliesHungergames extends JavaPlugin {

    public static PixliesHungergames instance = null;
    public static String prefix = "§bHungergames | ";

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;
        loadConfig();
        registerCommands();
        System.out.println(prefix + "Plugin loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println(prefix + "Plugin disabled!");
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
            cfg.set("started", false);
            cfg.set("prefix", prefix);
            try {
                cfg.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }



    public void registerCommands(){
        getCommand("setspawn").setExecutor(new SetspawnCommand());
    }

    }

