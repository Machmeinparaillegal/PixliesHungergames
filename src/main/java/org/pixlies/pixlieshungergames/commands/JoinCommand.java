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
import org.pixlies.pixlieshungergames.utils.ParticipatorUtils;


import java.io.File;
import java.util.logging.Level;

public class JoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)){
            JavaPlugin.getPlugin(PixliesHungergames.class).getLogger().log(Level.SEVERE, "Console may not execute this command!");
            return false;
        }
        Player p = (Player) commandSender;
        File file = new File("plugins/PixliesHungergames", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if(config.getBoolean("started")){
            p.sendMessage(config.getString("prefix") + "§cThe game has already started! Come earlier next time!");
            return true;
        }
        if(ParticipatorUtils.getParticipants().contains(p.getName())){
            p.sendMessage(config.getString("prefix") + "§aYou are no longer participating in the Hunger Games :(");
            ParticipatorUtils.removeParticipant(p.getName());
        }else{
            p.sendMessage(config.getString("prefix") + "§aYou are now participating in the Hunger Games!");
            ParticipatorUtils.addParticipant(p.getName());
        }
        return false;
    }
}
