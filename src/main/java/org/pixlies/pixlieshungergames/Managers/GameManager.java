package org.pixlies.pixlieshungergames.Managers;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.pixlies.pixlieshungergames.events.GameStartedEvent;
import org.pixlies.pixlieshungergames.utils.PlayerUtils;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.pixlies.pixlieshungergames.PixliesHungergames.instance;

public class GameManager {
    private static int timePassed;
    File file = new File("plugins/PixliesHungergames", "config.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    public void startCooldown() {
        timePassed = 12;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (timePassed >= 10 ) {
                    if(timePassed % 10 == 0) {
                        sendTimerMessageToAllPlayers(timePassed);
                    }
                    timePassed = timePassed - 1;
                } else if (timePassed >= 3) {
                        sendTimerMessageToAllPlayers(timePassed);
                    timePassed = timePassed - 1;
                } else if (timePassed > 0){
                  sendTimerMessageToAllPlayers(timePassed);
                    timePassed = timePassed - 1;



                }else if(timePassed == 0){
                    // Code to be executed when timer reaches 0
                    PlayerUtils.soundToAll(Sound.BLOCK_NOTE_BLOCK_PLING);
                    PlayerUtils.messageToAll(config.getString("prefix") + "§aThe Hunger Games have started! Good luck!");
                    Bukkit.getPluginManager().callEvent(new GameStartedEvent());
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

    private void sendTimerMessageToAllPlayers(int time) {
        for(Player player1: instance.getServer().getOnlinePlayers()) {
            if(time >3) {
                player1.sendMessage(config.getString("prefix") + "§aA Round of Hunger Games was started, do §6/join§a to join!\n" + time + " Seconds remaining.");
            }else{
                player1.sendMessage("§6" + time );
            }
            player1.playSound(player1.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
        }
    }
}
