package org.pixlies.pixlieshungergames.utils;

import java.util.NavigableMap;
import java.util.TreeMap;

public class KillUtils {
    //TODO: Test
    static TreeMap<String, Integer> killmap = new TreeMap<>();

    public static void addKill(String playerName){
        if(!killmap.containsKey(playerName)) {
            killmap.put(playerName, 1);
            return;
        }
        Integer kills = killmap.get(playerName) + 1;
        killmap.put(playerName, kills);
    }

    public static Integer getKills(String playerName){
        if(!killmap.containsKey(playerName)) return 0;
        return killmap.get(playerName);
    }

    public static NavigableMap<String, Integer> getKillMap(){
        return killmap.descendingMap();
    }
}
