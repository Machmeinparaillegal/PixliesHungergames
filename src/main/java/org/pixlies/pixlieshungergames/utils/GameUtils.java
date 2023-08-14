package org.pixlies.pixlieshungergames.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.pixlies.pixlieshungergames.PixliesHungergames;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

public class GameUtils {

    private static boolean initiated = false;
    private static boolean preStart = false;
    private static boolean started = false;
    private static ArrayList<String> trackerCooldown = new ArrayList<>();
    public static void setPreStart(boolean ps) {
        preStart = ps;
    }
    public static boolean isPreStart(){
        return preStart;
    }

    public static void setStarted(boolean s){
        started = s;
    }

    public static boolean isStarted(){
        return started;
    }

    public static void setInitiated(boolean i){
        initiated = i;
    }

    public static boolean getInitiated(){
        return initiated;
    }
    public static World getSpawnWorld(){
        File file = new File("plugins/PixliesHungergames", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        String spawns = (String) config.getList("spawnpoints").get(0);
        String[] spawnsdeserialized = spawns.split(";");
        return Bukkit.getWorld(spawnsdeserialized[0]);
    }
    public static void stopGame(){
        GameUtils.setInitiated(false);
        GameUtils.setStarted(false);
        GameUtils.setPreStart(false);

        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.hasPermission("pixlies.staff.hungergames.bypass") && !p.getGameMode().equals(GameMode.SPECTATOR)) continue;
            p.setGameMode(GameMode.SURVIVAL);
            PlayerUtils.clearEntireInventory(p);
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setFireTicks(0);
            p.getActivePotionEffects().clear();
        }
        getSpawnWorld().setGameRule(GameRule.MOB_GRIEFING, false);
        ParticipatorUtils.clear();
    }

    public static ItemStack createTracker(){
        NamespacedKey key = new NamespacedKey(JavaPlugin.getPlugin(PixliesHungergames.class), "tracker");
        ItemStack stack = new ItemStack(Material.COMPASS);
        ItemMeta meta = stack.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "tracker");
        meta.displayName(Component.text("§cTracker"));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("§bTracks a random player"));
        lore.add(Component.text("§bClick on a block to use!"));
        lore.add(Component.text("§cCooldown: 1 Minute"));
        meta.lore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public static boolean getTrackerCooldown(String p){
        return trackerCooldown.contains(p);
    }

    public static void setTrackerCooldown(String p){
        if(!trackerCooldown.contains(p)){
            trackerCooldown.add(p);
        }else{
            trackerCooldown.remove(p);
        }
    }

    public static void fillChests(){
        JavaPlugin.getPlugin(PixliesHungergames.class).getLogger().log(Level.INFO, "Filling chests");
        World world = getSpawnWorld();
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.WOODEN_SWORD));
        items.add(new ItemStack(Material.STONE_AXE));
        items.add(new ItemStack(Material.COOKED_BEEF, 2));
        items.add(new ItemStack(Material.STICK, 2));
        items.add(new ItemStack(Material.ROTTEN_FLESH, 3));
        items.add(new ItemStack(Material.LEATHER_CHESTPLATE));
        for(Chunk chunk : world.getLoadedChunks()){
            for(BlockState ent : chunk.getTileEntities()){
                ArrayList<ItemStack> openItems = new ArrayList<>(items);
                if(ent instanceof Chest){
                    Chest chest = (Chest) ent;
                    chest.getBlockInventory().clear();
                    int i = 0;
                    ArrayList<Integer> slots = new ArrayList<>();
                    int s = 1;
                    while(s <= chest.getBlockInventory().getSize()){
                        slots.add(s);
                        s++;
                    }

                    int amountItems = ThreadLocalRandom.current().nextInt(2, 5);
                    while(i < amountItems){
                        //Puts 2-4 items in the chest
                        int randomItem = ThreadLocalRandom.current().nextInt(0, openItems.size());
                        chest.getBlockInventory().setItem( slots.get(ThreadLocalRandom.current().nextInt(0, slots.size()-1)) ,openItems.get(randomItem));
                        openItems.remove(randomItem);
                        i++;
                    }
                }
            }
        }
    }
    public static void gameFinished(){
        File file = new File("plugins/PixliesHungergames", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        GameUtils.stopGame();
        Player firstPlace = Bukkit.getPlayer(ParticipatorUtils.getParticipants().get(0));
        assert firstPlace != null;
        firstPlace.sendMessage(config.getString("prefix") + "§aCongratulations! You won the Hunger Games!");
        String[] endingspawnall = config.getString("endingmain").split(";");
        String[] endingspawn1 = config.getString("endingfirst").split(";");
        String[] endingspawn2 = config.getString("endingsecond").split(";");
        String[] endingspawn3 = config.getString("endingthird").split(";");
        PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 20*7, 8, false, false);
        for (Player p : Bukkit.getOnlinePlayers()){
            if(p == firstPlace){
                p.teleport(deserialize(endingspawn1));
                p.addPotionEffect(effect);
            } else if (ParticipatorUtils.getLeaders().get(0).equals(p.getName())) {
                p.teleport(deserialize(endingspawn2));
                p.addPotionEffect(effect);
            }else if(ParticipatorUtils.getLeaders().get(1).equals(p.getName())){
                p.teleport(deserialize(endingspawn3));
                p.addPotionEffect(effect);
            }else{
                p.teleport(deserialize(endingspawnall));
            }
        }
        Firework fw = deserialize(endingspawnall).getWorld().spawn(deserialize(endingspawnall), Firework.class);
        FireworkMeta fwm = fw.getFireworkMeta();
        fwm.setPower(4);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.AQUA).flicker(true).build());
        fw.setFireworkMeta(fwm);
        fw.detonate();

        PlayerUtils.messageToAll("§k.     §bHunger Games - Results     §k.\n" +
                "§k.     §61st Place - §f" + firstPlace.getName() + " - §c" + KillUtils.getKills(firstPlace.getName()) +" §fkills" + "     §k.\n" +
                "§k.     §62nd Place - §f" + ParticipatorUtils.getLeaders().get(0) + " - §c" + KillUtils.getKills(ParticipatorUtils.getLeaders().get(0)) +" §fkills" + "     §k.\n" +
                "§k.     §63rd Place - §f" + ParticipatorUtils.getLeaders().get(1) + " - §c" + KillUtils.getKills(ParticipatorUtils.getLeaders().get(1)) +" §fkills" + "     §k.\n" +
                "§k.     §64th Place - §f" + ParticipatorUtils.getLeaders().get(2) + " - §c" + KillUtils.getKills(ParticipatorUtils.getLeaders().get(2)) +" §fkills" + "     §k.\n" +
                "§k.     §65th Place - §f" + ParticipatorUtils.getLeaders().get(3) + " - §c" + KillUtils.getKills(ParticipatorUtils.getLeaders().get(3)) +" §fkills" + "     §k.\n");
        NavigableMap<String, Integer> killmap = KillUtils.getKillMap();
        String firstKills =  killmap.isEmpty() ? "No one :(" : killmap.firstKey();
        String secondKills = killmap.lowerKey(firstKills) == null ? "No one :(" : killmap.lowerKey(firstKills);
        String thirdKills = secondKills.equals("No one :(")|| killmap.lowerKey(secondKills) == null ? "No one :(" : killmap.lowerKey(secondKills);
        String mostKills = killmap.get(firstKills) == null ? "None" : killmap.get(firstKills).toString();
        String secondmostKills = secondKills.equals("No one :(") ? "None" : killmap.get(secondKills).toString();
        String thirdmostKills = thirdKills.equals("No one :(") ? "None" : killmap.get(thirdKills).toString();
        PlayerUtils.messageToAll("§k.     §bHunger Games - Most kills\n" +
                "§k.     §f" + firstKills + " - §c" + mostKills + " §fkills" + "     §k.\n" +
                "§k.     §f" + secondKills + " - §c" + secondmostKills + " §fkills" + "     §k.\n" +
                "§k.     §f" + thirdKills + " - §c" + thirdmostKills + " §fkills" + "     §k.");
    }

    public static Location deserialize(String[] s){
        return new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), Float.parseFloat(s[4]), Float.parseFloat(s[5]));
    }
}
