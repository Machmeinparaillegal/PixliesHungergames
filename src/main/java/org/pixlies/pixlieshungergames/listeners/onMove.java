package org.pixlies.pixlieshungergames.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.pixlies.pixlieshungergames.utils.GameUtils;
import org.pixlies.pixlieshungergames.utils.ParticipatorUtils;
import org.pixlies.pixlieshungergames.utils.PlayerUtils;

public class onMove implements Listener {
    @EventHandler
    public void moveEvent(PlayerMoveEvent e){
        if(GameUtils.isPreStart() && ParticipatorUtils.getParticipants().contains(e.getPlayer().getName()) && e.hasChangedBlock()){
            e.setCancelled(true);
        }
        if(PlayerUtils.getStopped().contains(e.getPlayer())){
            e.setCancelled(true);
        }
    }
}
