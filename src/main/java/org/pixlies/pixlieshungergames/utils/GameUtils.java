package org.pixlies.pixlieshungergames.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class GameUtils {

    private static boolean initiated = false;
    private static boolean preStart = false;
    private static boolean started = false;
    public static void setPreStart(boolean ps) {
        preStart = ps;
    }
    public static boolean isPreStart(){
        return preStart;
    }

    public static void setStarted(boolean s){
        started = s;
    }

    public static boolean isStarted(){
        return started;
    }

    public static void setInitiated(boolean i){
        initiated = i;
    }

    public static boolean getInitiated(){
        return initiated;
    }
    public static World getSpawnWorld(){
        File file = new File("plugins/PixliesHungergames", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        String spawns = (String) config.getList("spawnpoints").get(0);
        String[] spawnsdeserialized = spawns.split(";");
        return Bukkit.getWorld(spawnsdeserialized[0]);
    }
    public static void stopGame(){
        GameUtils.setInitiated(false);
        GameUtils.setStarted(false);
        GameUtils.setPreStart(false);

        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.hasPermission("pixlies.staff.hungergames.bypass") && !ParticipatorUtils.getParticipants().contains(p.getName())) continue;
            p.setGameMode(GameMode.SURVIVAL);
            PlayerUtils.clearEntireInventory(p);
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setFireTicks(0);
            p.getActivePotionEffects().clear();
        }
        getSpawnWorld().setGameRule(GameRule.MOB_GRIEFING, false);
    }
}
