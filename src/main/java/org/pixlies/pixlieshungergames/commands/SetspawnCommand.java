package org.pixlies.pixlieshungergames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.pixlies.pixlieshungergames.PixliesHungergames;


import java.io.File;
import java.util.List;
import java.util.logging.Level;


public class SetspawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)){
            JavaPlugin.getPlugin(PixliesHungergames.class).getLogger().log(Level.SEVERE, "Console may not use this Command");
            return true;
        }

        Player p = (Player)commandSender;

        if(!p.hasPermission("pixlies.staff.hungergames.spawnpoint")){
            p.sendMessage("§cYou do not have permission to do this!");
            return true;
        }
        String world = p.getWorld().getName();
        long x = Math.round(p.getLocation().getX() * 100) / 100;
        long y = Math.round(p.getLocation().getY() * 100) / 100;
        long z = Math.round(p.getLocation().getZ() * 100) / 100;

        File file = new File("plugins/PixliesHungergames", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        String serialized = world + ";" + x + ";" + y + ";" + z;
        List<String> cfgList = config.getStringList("spawnpoints");

        int number = cfgList.size() + 1;

        if(cfgList.contains(serialized)){
            cfgList.remove(serialized);
            config.set("spawnpoints", cfgList);
            try{
                config.save(file);
            }catch(Exception e){
                e.printStackTrace();
                p.sendMessage(config.getString("prefix") + "§7Something went wrong!");
                return true;
            }
            p.sendMessage(config.getString("prefix") + "§bSuccesfully removed the spawnpoint from " + x + ", " + y + ", " + z + "!");

        }else {
            cfgList.add(serialized);
            config.set("spawnpoints", cfgList);
            try{
                config.save(file);
            }catch(Exception e){
                e.printStackTrace();
                p.sendMessage(config.getString("prefix") + "§7Something went wrong!");
                return true;
            }
            p.sendMessage(config.getString("prefix") + "§bSuccesfully added spawnpoint #" + number + " to " + x + ", " + y + ", " + z + "!");
        }

        return false;
    }
}
