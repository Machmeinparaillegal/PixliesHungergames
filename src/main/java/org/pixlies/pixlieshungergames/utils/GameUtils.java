package org.pixlies.pixlieshungergames.utils;

import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.pixlies.pixlieshungergames.PixliesHungergames;

import java.io.File;
import java.util.ArrayList;
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
            if(p.hasPermission("pixlies.staff.hungergames.bypass") && !ParticipatorUtils.getParticipants().contains(p.getName())) continue;
            p.setGameMode(GameMode.SURVIVAL);
            PlayerUtils.clearEntireInventory(p);
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setFireTicks(0);
            p.getActivePotionEffects().clear();
        }
        getSpawnWorld().setGameRule(GameRule.MOB_GRIEFING, false);
    }

    public static ItemStack createTracker(){
        NamespacedKey key = new NamespacedKey(JavaPlugin.getPlugin(PixliesHungergames.class), "tracker");
        ItemStack stack = new ItemStack(Material.COMPASS);
        ItemMeta meta = stack.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "tracker");
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
}
