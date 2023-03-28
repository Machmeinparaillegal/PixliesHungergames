package org.pixlies.pixlieshungergames.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerUtils {
    public static void messageToAll(String message){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(message);
        }
    }
    public static void soundToAll(Sound sound){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.playSound(p.getLocation(), sound, 1.0f, 1.0f);
        }
    }
}
