package org.pixlies.pixlieshungergames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pixlies.pixlieshungergames.Objects.Game;
import org.pixlies.pixlieshungergames.PixliesHungergames;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.pixlies.pixlieshungergames.PixliesHungergames.instance;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)){
            System.out.println("Console may not use this Command");
            return true;
        }
        Player player = ((Player) commandSender).getPlayer();
        if(instance.game != null) {
            player.sendMessage("§cThere is already a round of Hunger Games going on.§!");
            return false;
        }
        instance.game = new Game();


        return true;
    }


}
