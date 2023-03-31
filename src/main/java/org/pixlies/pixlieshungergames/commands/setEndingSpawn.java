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
import java.util.logging.Level;

public class setEndingSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player)){
            JavaPlugin.getPlugin(PixliesHungergames.class).getLogger().log(Level.SEVERE, "Console may not use this command!");
            return true;
        }
        Player p = (Player) commandSender;
        if(!p.hasPermission("pixlies.staff.hungergames.setspawnpoints")){
            p.sendMessage("§cYou do not have permission to do this!");
            return true;
        }
        File file = new File("plugins/PixliesHungergames", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        String world = p.getWorld().getName();
        long x = Math.round(p.getLocation().getX() * 100) / 100;
        long y = Math.round(p.getLocation().getY() * 100) / 100;
        long z = Math.round(p.getLocation().getZ() * 100) / 100;
        float pitch = p.getLocation().getPitch();
        float yaw = p.getLocation().getYaw();
        String serialized = world + ";" + x + ";" + y + ";" + z + ";" + pitch + ";" + yaw;

        if(args.length == 0){
            config.set("endingmain", serialized);
            try{
                config.save(file);
            }catch(Exception e){
                e.printStackTrace();
            }
            p.sendMessage("§aSuccesfully set the ending point for everyone to " + x + ", " + y + ", " + z +"!");
        }else if(args[0].equals("1")){
            config.set("endingfirst", serialized);
            try{
                config.save(file);
            }catch(Exception e){
                e.printStackTrace();
            }
            p.sendMessage("§aSuccesfully set the ending point for the 1st place to " + x + ", " + y + ", " + z +"!");
        }else if (args[0].equals("2")){
            config.set("endingsecond", serialized);
            try{
                config.save(file);
            }catch(Exception e){
                e.printStackTrace();
            }
            p.sendMessage("§aSuccesfully set the ending point for the 2nd place to " + x + ", " + y + " " + z +"!");
        } else if (args[0].equals("3")) {
            config.set("endingthird", serialized);
            try{
                config.save(file);
            }catch(Exception e){
                e.printStackTrace();
            }
            p.sendMessage("§aSuccesfully set the ending point for the 3rd place to " + x + ", " + y + ", " + z +"!");
        }
        return false;
    }
}
