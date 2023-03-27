package org.pixlies.pixlieshungergames.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.pixlies.pixlieshungergames.utils.KillUtils;

import java.io.File;
import java.time.Duration;


public class onDeath implements Listener {
    @EventHandler
    public void deathEvent(PlayerDeathEvent e){
        File file = new File("plugins/PixliesHungergames", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        //disabled for testing purposes
        //  if(!config.getBoolean("started")) return;

        e.setCancelled(true);
        Player victim = e.getPlayer();
        Player killer = e.getPlayer().getKiller();
        victim.setGameMode(GameMode.SPECTATOR);


        String deathmessage = e.getDeathMessage().replace(victim.getName(), "You") + "!";
        if(deathmessage.contains("was")){
            deathmessage = deathmessage.replace("was", "were");
        }

        Title title = Title.title(Component.text("§4YOU DIED!"), Component.text(deathmessage), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(2)));
        victim.showTitle(title);

        //TODO: Test
        if(killer != null) {
            String ending;
            int kills = KillUtils.getKills(killer.getName()) + 1;
            switch (kills%10) {
                case 1: ending = "st";
                        break;
                case 2: ending = "nd";
                        break;
                case 3: ending = "rd";
                        break;
                default: ending = "th";
                        break;
            }
            killer.sendMessage(config.getString("prefix") + "§aYou killed §6" + victim.getName() +"§a! That was your §c" + kills + ending + "§a victim!");
            killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            KillUtils.addKill(killer.getName());
        }
    }
}
