package io.zipcoder.casino.utilities.persistence;

import io.zipcoder.casino.games.Game;
import io.zipcoder.casino.games.specific.BlackJack;
import io.zipcoder.casino.games.specific.Craps;
import io.zipcoder.casino.games.specific.GoFish;
import io.zipcoder.casino.games.specific.LoopyDice;
import io.zipcoder.casino.models.Chip;
import io.zipcoder.casino.models.StatsModel;

import java.util.ArrayList;

public class StatTracker {
    public static int blackJackWins;
    public static int goFishWins;
    public static int loopyWins;
    public static int crapsWins;
    public static int highestChipValue;
    public static int overallLosses;
    public static int totalLifetimeChipWinnings;
    public static int totalCashSpent;

    static {
        blackJackWins = 0;
        goFishWins = 0;
        loopyWins = 0;
        crapsWins = 0;
        highestChipValue = 0;
        overallLosses = 0;
        totalLifetimeChipWinnings = 0;
        totalCashSpent = 0;
    }

    public static void setStats(StatsModel mod) {
        StatTracker.crapsWins = mod.getCrapsWins();
        StatTracker.goFishWins = mod.getGoFishWins();
        StatTracker.blackJackWins = mod.getBlackJackWins();
        StatTracker.loopyWins = mod.getLoopyWins();
        StatTracker.overallLosses = mod.getOverallLosses();
        StatTracker.totalCashSpent = mod.getTotalCashSpent();
        StatTracker.totalLifetimeChipWinnings = mod.getTotalLifetimeChipWinnings();
        StatTracker.highestChipValue = mod.getHighestChipValue();
    }

    public static void clearAllStats() {
        blackJackWins = 0;
        goFishWins = 0;
        loopyWins = 0;
        crapsWins = 0;
        highestChipValue = 0;
        overallLosses = 0;
        totalLifetimeChipWinnings = 0;
        totalCashSpent = 0;
    }

    public static void finishGame(Game game, boolean win) {
        if (win) {
            if (game instanceof BlackJack) {
                blackJackWins++;
            } else if (game instanceof Craps) {
                crapsWins++;
            } else if (game instanceof GoFish) {
                goFishWins++;
            } else if (game instanceof LoopyDice) {
                loopyWins++;
            }
        } else {
            overallLosses++;
        }
    }

    public static void updateCashSpent(int spent) {
        totalCashSpent+=spent;
        if (totalCashSpent<0) { totalCashSpent = 0; }
    }

}
