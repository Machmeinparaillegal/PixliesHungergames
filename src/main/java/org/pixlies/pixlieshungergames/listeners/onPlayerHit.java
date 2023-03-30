package org.pixlies.pixlieshungergames.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.pixlies.pixlieshungergames.utils.GameUtils;

public class onPlayerHit implements Listener {
    @EventHandler
    public void playerHit(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player)){ return;}
        if(!GameUtils.isStarted() && !e.getDamager().hasPermission("pixlies.staff.hungergames.bypass")){
            e.setCancelled(true);
        }
    }
}
