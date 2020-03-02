package io.zipcoder.casino.utilities.persistence;

import io.zipcoder.casino.players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private static Map<String, Player> usersByName;

    public static boolean isUser(String name) {
        return usersByName.containsKey(name) ? true : false;
    }

    public static Player getPlayer(String name) {
        return usersByName.get(name);
    }

    public static void addUser(Player user) {
        usersByName.put(user.getName(), user);
    }

    public static ArrayList<Player> getAllPlayers() {
        ArrayList<Player> toRet = new ArrayList<>();
        for (Map.Entry<String, Player> entry : usersByName.entrySet()) {
            toRet.add(entry.getValue());
        }

        return toRet;
    }

    public static void processStats(Player processedPlayer) {
        processedPlayer.getStats().setBlackJackWins(StatTracker.blackJackWins);
        processedPlayer. getStats().setLoopyWins(StatTracker.loopyWins);
        processedPlayer. getStats().setCrapsWins(StatTracker.crapsWins);
        processedPlayer.getStats().setGoFishWins(StatTracker.goFishWins);
        processedPlayer. getStats().setOverallLosses(StatTracker.overallLosses);
        processedPlayer.getStats().setHighestChipValue(StatTracker.highestChipValue);
        processedPlayer. getStats().setTotalCashSpent(StatTracker.totalCashSpent);
        processedPlayer. getStats().setTotalLifetimeChipWinnings(StatTracker.totalLifetimeChipWinnings);
        int gamblingWins = StatTracker.crapsWins + StatTracker.blackJackWins;
        int nonGamblingWins = StatTracker.loopyWins + StatTracker.goFishWins;
        int totalWins = gamblingWins + nonGamblingWins;
        processedPlayer.getStats().setOverallWins(totalWins);
        processedPlayer.getStats().setGamblingWins(gamblingWins);
    }

    public static void reloadAllUsers(ArrayList<Player> players) {
        usersByName.clear();
        for (Player p : players) {
            usersByName.put(p.getName(), p);
        }
    }

    public static boolean canLogin(String name, String pass) {
        return (usersByName.containsKey(name)) ? (usersByName.get(name).getPassword().equals(pass)) : false;
    }

    public static void clear() {
        usersByName.clear();
    }

    static {
        usersByName = new HashMap<>();
    }

}
