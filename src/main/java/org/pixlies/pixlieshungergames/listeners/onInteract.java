package org.pixlies.pixlieshungergames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.pixlies.pixlieshungergames.PixliesHungergames;
import org.pixlies.pixlieshungergames.utils.GameUtils;
import org.pixlies.pixlieshungergames.utils.ParticipatorUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class onInteract implements Listener {
    @EventHandler
    public void interactEvent(PlayerInteractEvent e){
        if(!GameUtils.isStarted() && !e.getPlayer().hasPermission("pixlies.staff.hungergames.bypass")){
            e.setCancelled(true);
        }
        File file = new File("plugins/PixliesHungergames", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        Player p = e.getPlayer();
        if(!ParticipatorUtils.getParticipants().contains(p.getName())) return;
        if(p.getActiveItem().equals(GameUtils.createTracker()) && !GameUtils.getTrackerCooldown(p.getName())){
            GameUtils.setTrackerCooldown(p.getName());
            ArrayList<Player> allPlayers = new ArrayList<>();
            for(Player t : Bukkit.getOnlinePlayers()){
                if(t.equals(p)) continue;
                if(ParticipatorUtils.getParticipants().contains(t.getName())) allPlayers.add(t);
            }
            if(allPlayers.isEmpty()) return;
            //No +1 cus size already shows one more than the technical count
            Player target = allPlayers.get(ThreadLocalRandom.current().nextInt(0, allPlayers.size()));
            p.setCompassTarget(target.getLocation());
            p.sendMessage(config.getString("prefix") + "§aTracking §6" + target.getName() + "§a...");
            Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(PixliesHungergames.class), () -> {
                GameUtils.setTrackerCooldown(p.getName());
            }, 20*60 );
        }
        if(GameUtils.getTrackerCooldown(p.getName())){
            p.sendMessage(config.getString("prefix") + "§cYou can only refresh your tracker once a minute!");
        }
    }
}
