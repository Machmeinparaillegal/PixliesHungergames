package org.pixlies.pixlieshungergames.utils;

import java.util.ArrayList;

public class ParticipatorUtils {
    static ArrayList<String> participants = new ArrayList<>();
    static ArrayList<String> leaders = new ArrayList<>();
    public static ArrayList<String> getParticipants(){
        return participants;
    }
    public static void addParticipant(String player){
        participants.add(player);
    }
    public static void removeParticipant(String player){
        //Save the top 5 (except first place) for later
        if(GameUtils.isStarted()) {
            if(leaders.isEmpty()){
                leaders.add(0, "No one :(");
                leaders.add(1, "No one :(");
                leaders.add(2, "No one :(");
                leaders.add(3, "No one :(");
            }
            switch (participants.size()) {
                case 2:
                    leaders.set(0, player);
                    break;
                case 3:
                    leaders.set(1, player);
                    break;
                case 4:
                    leaders.set(2, player);
                    break;
                case 5:
                    leaders.set(3, player);
                    break;
            }
        }
        participants.remove(player);
    }
    public static ArrayList<String> getLeaders(){
        return leaders;
    }

    public static void clear(){
        participants.clear();
        leaders.clear();
    }
}
