package org.pixlies.pixlieshungergames.listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.pixlies.pixlieshungergames.utils.GameUtils;

public class onBlockBreak implements Listener {
    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e){
        if(!GameUtils.isStarted() && !(e.getPlayer().hasPermission("pixlies.staff.hungergames.bypass"))){
            e.setCancelled(true);
        }
    }
}
