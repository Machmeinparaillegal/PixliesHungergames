package org.pixlies.pixlieshungergames.listeners;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.pixlies.pixlieshungergames.utils.GameUtils;
import org.pixlies.pixlieshungergames.utils.ParticipatorUtils;
import org.pixlies.pixlieshungergames.utils.PlayerUtils;

import java.io.File;

public class onLeave implements Listener {
    @EventHandler
    public void leaveEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if((GameUtils.isStarted() || GameUtils.isPreStart()) && ParticipatorUtils.getParticipants().contains(p.getName())) {
            ParticipatorUtils.removeParticipant(p.getName());
            p.setGameMode(GameMode.SPECTATOR);
            PlayerUtils.clearEntireInventory(p);
            File file = new File("plugins/PixliesHungergames", "config.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            if(ParticipatorUtils.getParticipants().size() == 1){
                GameUtils.gameFinished();
            }else {
                PlayerUtils.messageToAll(config.getString("prefix") + " §c" + ParticipatorUtils.getParticipants().size() + " §fcontestants remain!");
            }
        }

    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if((GameUtils.isStarted() || GameUtils.isPreStart()) && !player.hasPermission("pixlies.staff.hungergames.bypass")){
            if(ParticipatorUtils.getParticipants().contains(player.getName())) ParticipatorUtils.removeParticipant(player.getName());
            player.setGameMode(GameMode.SPECTATOR);
        }
    }
}
