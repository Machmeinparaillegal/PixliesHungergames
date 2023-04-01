package org.pixlies.pixlieshungergames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.pixlies.pixlieshungergames.Objects.Game;
import org.pixlies.pixlieshungergames.PixliesHungergames;
import org.pixlies.pixlieshungergames.utils.GameUtils;
import org.pixlies.pixlieshungergames.utils.KillUtils;
import org.pixlies.pixlieshungergames.utils.ParticipatorUtils;

import java.util.logging.Level;

import static org.pixlies.pixlieshungergames.PixliesHungergames.instance;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)){
            JavaPlugin.getPlugin(PixliesHungergames.class).getLogger().log(Level.SEVERE, "Console may not use this Command");
            return true;
        }
        Player player = (Player) commandSender;
        if(!player.hasPermission("pixlies.staff.hungergames.start")){
            player.sendMessage("§cYou do not have permission to do this!");
            return false;
        }
        if(GameUtils.getInitiated()) {
            player.sendMessage("§cThere is already a round of Hunger Games going on!");
            return false;
        }
        player.sendMessage("§aStarting Hunger Games...");
        KillUtils.clear();
        ParticipatorUtils.clear();
        instance.game = new Game();


        return true;
    }


}
