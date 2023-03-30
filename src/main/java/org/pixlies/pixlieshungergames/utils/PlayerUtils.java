package org.pixlies.pixlieshungergames.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class PlayerUtils {
    public static void messageToAll(String message){
        Bukkit.broadcast(Component.text(message));
    }
    public static void soundToAll(Sound sound){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.playSound(p.getLocation(), sound, 1.0f, 1.0f);
        }
    }
    public static void clearEntireInventory(Player p){
        PlayerInventory inv = p.getInventory();
        inv.clear();
        inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);
        inv.setItemInOffHand(null);
    }
}
