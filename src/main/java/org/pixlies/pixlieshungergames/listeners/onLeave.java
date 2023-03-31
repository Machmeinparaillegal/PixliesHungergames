package org.pixlies.pixlieshungergames.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.pixlies.pixlieshungergames.utils.GameUtils;
import org.pixlies.pixlieshungergames.utils.ParticipatorUtils;
import org.pixlies.pixlieshungergames.utils.PlayerUtils;

public class onLeave implements Listener {
    @EventHandler
    public void leaveEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if((GameUtils.isStarted() || GameUtils.isPreStart() || GameUtils.getInitiated()) && ParticipatorUtils.getParticipants().contains(p.getName())) {
            ParticipatorUtils.removeParticipant(p.getName());
            p.setGameMode(GameMode.SPECTATOR);
            PlayerUtils.clearEntireInventory(p);
        }

    }
}
