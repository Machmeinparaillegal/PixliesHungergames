package org.pixlies.pixlieshungergames.listeners;

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
import org.bukkit.inventory.ItemStack;
import org.pixlies.pixlieshungergames.utils.GameUtils;
import org.pixlies.pixlieshungergames.utils.KillUtils;
import org.pixlies.pixlieshungergames.utils.ParticipatorUtils;
import org.pixlies.pixlieshungergames.utils.PlayerUtils;

import java.io.File;
import java.time.Duration;


public class onDeath implements Listener {
    @EventHandler
    public void deathEvent(PlayerDeathEvent e){
        File file = new File("plugins/PixliesHungergames", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        //TODO: disabled for testing purposes
        //if(!GameUtils.isStarted() || !ParticipatorUtils.getParticipants().contains(e.getPlayer().getName())) return;

        e.setCancelled(true);
        Player victim = e.getPlayer();
        Player killer = e.getPlayer().getKiller();


        String deathmessage = e.getDeathMessage().replace(victim.getName(), "You") + "!";
        if(deathmessage.contains("was")){
            deathmessage = deathmessage.replace("was", "were");
        }

        Title title = Title.title(Component.text("§4YOU DIED!"), Component.text(deathmessage), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(3), Duration.ofSeconds(2)));
        victim.setGameMode(GameMode.SPECTATOR);
        for(ItemStack item : victim.getInventory().getContents()) {
            if(item != null) {
                victim.getWorld().dropItemNaturally(victim.getLocation(), item);
                victim.getInventory().remove(item);
            }
        }
        PlayerUtils.clearEntireInventory(victim);
        victim.showTitle(title);
        victim.playSound(victim.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 1.0f, 1.0f);
        int placement = ParticipatorUtils.getParticipants().size();
        victim.sendMessage("§aYou placed §c" + placement + getEnding(placement) + " §awith §c" + KillUtils.getKills(victim.getName()) + " §akills! \nBetter luck next time!");
        ParticipatorUtils.removeParticipant(victim.getName());

        //TODO: Test
        if(killer != null) {

            int kills = KillUtils.getKills(killer.getName()) + 1;
            String ending = getEnding(kills);
            killer.sendMessage(config.getString("prefix") + "§aYou killed §6" + victim.getName() +"§a! That was your §c" + kills + ending + "§a victim!");
            killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            KillUtils.addKill(killer.getName());
        }
    }

    public String getEnding(int i){
        String ending;
        switch (i%10) {
            case 1: ending = "st";
                break;
            case 2: ending = "nd";
                break;
            case 3: ending = "rd";
                break;
            default: ending = "th";
                break;
        }
        return ending;
    }
}
