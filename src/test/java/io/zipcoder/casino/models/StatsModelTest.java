package io.zipcoder.casino.models;

import org.junit.Assert;
import org.junit.Test;

public class StatsModelTest {

    @Test
    public void TestGettersAndSetters() {
        StatsModel testModel = new StatsModel();
        testModel.setBlackJackWins(1);
        testModel.setCrapsWins(1);
        testModel.setGamblingWins(2);
        testModel.setGoFishWins(1);
        testModel.setHighestChipValue(100);
        testModel.setLoopyWins(1);
        testModel.setOverallLosses(5);
        testModel.setOverallWins(4);
        testModel.setTotalCashSpent(6000);
        testModel.setTotalLifetimeChipWinnings(5000);
        testModel.setOverallScore(999999999);

        Assert.assertEquals(1, testModel.getBlackJackWins());
        Assert.assertEquals(1, testModel.getCrapsWins());
        Assert.assertEquals(2, testModel.getGamblingWins());
        Assert.assertEquals(1, testModel.getGoFishWins());
        Assert.assertEquals(1, testModel.getLoopyWins());
        Assert.assertEquals(5, testModel.getOverallLosses());
        Assert.assertEquals(4, testModel.getOverallWins());
        Assert.assertEquals(6000, testModel.getTotalCashSpent());
        Assert.assertEquals(5000, testModel.getTotalLifetimeChipWinnings());
        Assert.assertEquals(100, testModel.getHighestChipValue());
        Assert.assertEquals(42, testModel.getOverallScore());

    }

    @Test
    public void negativeScoreTest() {
        StatsModel testModel = new StatsModel();
        testModel.setOverallLosses(500);
        Assert.assertEquals(0, testModel.getOverallScore());
    }

    @Test
    public void compareToTest() {
        StatsModel testModel = new StatsModel();
        StatsModel testModelB = new StatsModel();
        testModel.setOverallWins(5);
        testModelB.setOverallWins(3);
        Assert.assertEquals(1, testModel.compareTo(testModelB));

        testModel = new StatsModel();
        testModelB = new StatsModel();
        testModel.setOverallWins(3);
        testModelB.setOverallWins(5);
        Assert.assertEquals(-1, testModel.compareTo(testModelB));

        testModel = new StatsModel();
        testModelB = new StatsModel();
        testModel.setOverallWins(3);
        testModelB.setOverallWins(3);
        Assert.assertEquals(0, testModel.compareTo(testModelB));
    }

}
