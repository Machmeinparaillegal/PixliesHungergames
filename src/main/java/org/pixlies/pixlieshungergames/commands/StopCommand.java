package org.pixlies.pixlieshungergames.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.pixlies.pixlieshungergames.PixliesHungergames;
import org.pixlies.pixlieshungergames.utils.GameUtils;

import java.util.logging.Level;

public class StopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)){
            JavaPlugin.getPlugin(PixliesHungergames.class).getLogger().log(Level.SEVERE, "Console may not execute this command!");
            return true;
        }
       Player p = (Player) commandSender;
        if(!p.hasPermission("pixlies.staff.hungergames.start")){
            p.sendMessage("You do not have permission to do this!");
            return false;
        }
        if(!GameUtils.getInitiated() && !GameUtils.isPreStart() && !GameUtils.isStarted()){
            p.sendMessage("§cThere is no game in progress!");
            return false;
        }

        Bukkit.broadcast(Component.text("§aStopped the Hunger Games!"));
        GameUtils.stopGame();
        return false;
    }
}
