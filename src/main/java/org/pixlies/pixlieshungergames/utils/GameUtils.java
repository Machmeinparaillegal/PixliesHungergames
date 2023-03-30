package org.pixlies.pixlieshungergames.utils;

public class GameUtils {
    private static boolean preStart = false;
    private static boolean started = false;
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
}
