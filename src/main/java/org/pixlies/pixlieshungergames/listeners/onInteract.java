package org.pixlies.pixlieshungergames.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.pixlies.pixlieshungergames.utils.GameUtils;

public class onInteract implements Listener {
    @EventHandler
    public void interactEvent(PlayerInteractEvent e){
        if(!GameUtils.isStarted() && !e.getPlayer().hasPermission("pixlies.staff.hungergames.bypass")){
            e.setCancelled(true);
        }
    }
}
