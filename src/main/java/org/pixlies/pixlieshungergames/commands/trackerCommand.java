package org.pixlies.pixlieshungergames.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pixlies.pixlieshungergames.utils.GameUtils;
import org.pixlies.pixlieshungergames.utils.ParticipatorUtils;

public class trackerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!GameUtils.isStarted()){
           commandSender.sendMessage("§cThere is no game going on...");
            return true;
        }
        if(commandSender instanceof Player){
            Player p = (Player)commandSender;
            if(!p.hasPermission("pixlies.staff.hungergames.start")){
                p.sendMessage("§cYou do not have permission to do this!");
                return true;
            }else{
                p.sendMessage("§aGiving everyone a tracker compass...");
            }
        }
        GameUtils.giveTracker();
        return false;
    }
}
