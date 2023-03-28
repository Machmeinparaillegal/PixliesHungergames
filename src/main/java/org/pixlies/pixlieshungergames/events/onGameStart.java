package org.pixlies.pixlieshungergames.events;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.pixlies.pixlieshungergames.PixliesHungergames;
import org.pixlies.pixlieshungergames.utils.PlayerUtils;
import org.pixlies.pixlieshungergames.utils.ParticipatorUtils;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class onGameStart implements Listener {
    File file = new File("plugins/PixliesHungergames", "config.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    @EventHandler
    public void startEvent(GameStartedEvent e){
        JavaPlugin.getPlugin(PixliesHungergames.class).getLogger().info("Starting Hunger Games");
        if(ParticipatorUtils.getParticipants().isEmpty()){
            PlayerUtils.messageToAll(config.getString("prefix") + "§cNo one joined :(((");
            return;
        }
        int vacantspawns = config.getList("spawnpoints").size() - ParticipatorUtils.getParticipants().size();
        if(vacantspawns < 0){
            e.setCancelled(true);
                PlayerUtils.messageToAll(config.getString("prefix") + "§cThere is not enough spawnpoints for the game to start! Please set §b" + Math.abs(vacantspawns) + " §cadditional spawns!");
            return;
        }

        int place = 0;
        for(String participant : ParticipatorUtils.getParticipants()){
            Player partplayer = Bukkit.getPlayer(participant);
            if(partplayer == null){
                ParticipatorUtils.removeParticipant(participant);
                continue;
            }
            partplayer.teleport(getSpawn(place));
            place++;
        }
        AtomicInteger timer = new AtomicInteger(10);
        Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(PixliesHungergames.class), task -> {
            if(timer.get() == 10){
                PlayerUtils.messageToAll(config.getString("prefix") + "§aFighting starting in §610 §aseconds!");
                PlayerUtils.soundToAll(Sound.BLOCK_NOTE_BLOCK_GUITAR);
            } else if (timer.get() <= 5 && timer.get() != 0) {
                PlayerUtils.messageToAll(config.getString("prefix") + "§aFighting starting in §6" + timer.get() + " §aseconds!");
                PlayerUtils.soundToAll(Sound.BLOCK_NOTE_BLOCK_GUITAR);
            }else if(timer.get() == 0){
             //Code when fighting starts
                PlayerUtils.messageToAll(config.getString("prefix") + "§aFighting has begun! Good luck!");
                PlayerUtils.soundToAll(Sound.ENTITY_GENERIC_EXPLODE);
                config.set("started", true);
                try {
                    config.save(file);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                getSpawnWorld().setGameRule(GameRule.MOB_GRIEFING, true);
                task.cancel();
            }
            timer.set(timer.get() - 1);
        }, 0, 20);
    }
    public Location getSpawn(int i){
         String spawn = (String) config.getList("spawnpoints").get(i);
         String[] spawndeserialized = spawn.split(";");
        return new Location(Bukkit.getWorld(spawndeserialized[0]), Integer.parseInt(spawndeserialized[1]), Integer.parseInt(spawndeserialized[2]), Integer.parseInt(spawndeserialized[3]));
    }
    public World getSpawnWorld(){
        String spawns = (String) config.getList("spawnpoints").get(0);
        String[] spawnsdeserialized = spawns.split(";");
        return Bukkit.getWorld(spawnsdeserialized[0]);
    }
}
