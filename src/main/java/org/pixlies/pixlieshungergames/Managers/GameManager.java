package org.pixlies.pixlieshungergames.Managers;

import org.bukkit.entity.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.pixlies.pixlieshungergames.PixliesHungergames.instance;

public class GameManager {
    private static int timePassed;


    public void startCooldown() {
        timePassed = 60;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (timePassed >= 10 ) {
                    if(timePassed % 5 == 0) {
                        sendTimerMessageToAllPlayers(timePassed);
                    }
                    timePassed = timePassed - 1;
                } else if (timePassed >= 3) {
                    for (Player player : instance.getServer().getOnlinePlayers()) {
                        player.sendMessage("§4" + timePassed);
                    }
                    timePassed = timePassed - 1;
                } else {
                    for (Player player : instance.getServer().getOnlinePlayers()) {
                        player.sendMessage("§4" + timePassed);
                    }
                    timePassed = timePassed - 1;
                    // Code to be executed when timer reaches 0
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        executor.schedule(new Runnable() {
            @Override
            public void run() {
                // Cancel the first task after 57 seconds
                future.cancel(true);
            }
        }, 59, TimeUnit.SECONDS);
    }

    private static void sendTimerMessageToAllPlayers(int time) {
        for(Player player1: instance.getServer().getOnlinePlayers()) {
            player1.sendMessage("§eA Round of Hunger Games was started, do §4/join§e to join!\n" + time + " Seconds remaining.");
        }
    }
}
