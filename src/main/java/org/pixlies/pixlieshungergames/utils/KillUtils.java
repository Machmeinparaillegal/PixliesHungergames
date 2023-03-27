package org.pixlies.pixlieshungergames.utils;

import java.util.HashMap;
import java.util.Map;

public class KillUtils {
    //TODO: Test
    static Map<String, Integer> killmap = new HashMap<>();

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
}
