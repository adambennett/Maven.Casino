package io.zipcoder.casino.utilities.persistence;

import io.zipcoder.casino.games.specific.BlackJack;
import io.zipcoder.casino.games.specific.Craps;
import io.zipcoder.casino.games.specific.GoFish;
import io.zipcoder.casino.games.specific.LoopyDice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StatTrackerTest {

    @Before
    public void setup() {
        StatTracker.clearAllStats();
    }

    @Test
    public void finishGameBlackJackWin() {
        StatTracker.finishGame(new BlackJack(), false);
        StatTracker.finishGame(new LoopyDice(), false);
        StatTracker.finishGame(new BlackJack(), false);
        StatTracker.finishGame(new BlackJack(), true);
        Assert.assertTrue(StatTracker.blackJackWins == 1);
    }

    @Test
    public void finishGameLoopyWin() {
        StatTracker.finishGame(new LoopyDice(), false);
        StatTracker.finishGame(new LoopyDice(), false);
        StatTracker.finishGame(new LoopyDice(), true);
        StatTracker.finishGame(new LoopyDice(), true);
        Assert.assertTrue(StatTracker.loopyWins == 2);
    }

    @Test
    public void finishGameGoFishWin() {
        StatTracker.finishGame(new GoFish(), true);
        StatTracker.finishGame(new GoFish(), true);
        StatTracker.finishGame(new LoopyDice(), true);
        StatTracker.finishGame(new GoFish(), true);
        Assert.assertTrue(StatTracker.goFishWins == 3);
    }

    @Test
    public void finishGameCrapsWin() {
        StatTracker.finishGame(new Craps(), false);
        StatTracker.finishGame(new LoopyDice(), true);
        StatTracker.finishGame(new Craps(), false);
        StatTracker.finishGame(new Craps(), true);
        Assert.assertTrue(StatTracker.crapsWins == 1);
    }

    @Test
    public void finishGameLoss() {
        StatTracker.finishGame(new LoopyDice(), false);
        StatTracker.finishGame(new LoopyDice(), false);
        StatTracker.finishGame(new LoopyDice(), false);
        StatTracker.finishGame(new LoopyDice(), true);
        Assert.assertTrue(StatTracker.overallLosses == 3);
    }

    @Test
    public void updateCashSpent() {
        StatTracker.updateCashSpent(100);
        Assert.assertTrue(StatTracker.totalCashSpent == 100);
    }
}