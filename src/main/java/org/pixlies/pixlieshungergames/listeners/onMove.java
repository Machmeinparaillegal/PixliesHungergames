package org.pixlies.pixlieshungergames.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.pixlies.pixlieshungergames.utils.GameUtils;
import org.pixlies.pixlieshungergames.utils.ParticipatorUtils;

public class onMove implements Listener {
    @EventHandler
    public void moveEvent(PlayerMoveEvent e){
        if(GameUtils.isPreStart() && ParticipatorUtils.getParticipants().contains(e.getPlayer().getName()) && e.hasChangedPosition()){
            e.setCancelled(true);
        }
    }
}
