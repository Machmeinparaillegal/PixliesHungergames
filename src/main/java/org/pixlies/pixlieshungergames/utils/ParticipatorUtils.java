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
        switch (participants.size()){
            case 1:
                leaders.add(0, player);
                break;
            case 2:
                leaders.add(1, player);
                break;
            case 3:
                leaders.add(2, player);
                break;
            case 4:
                leaders.add(3, player);
                break;
        }
        participants.remove(player);
    }
}
