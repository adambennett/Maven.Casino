package io.zipcoder.casino.utilities.persistence;

import io.zipcoder.casino.models.StatsModel;
import io.zipcoder.casino.models.Wallet;
import io.zipcoder.casino.players.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DatabaseTest {

    @Before
    public void setup() {
        StatTracker.clearAllStats();
        Database.clear();
    }

    @Test
    public void isUser() {
        Player adam = new Player("adam");
        Database.addUser(adam);
        Assert.assertTrue(Database.isUser("adam"));
        Assert.assertFalse(Database.isUser("bill"));
    }

    @Test
    public void getPlayer() {
        Player adam = new Player("adam");
        Database.addUser(adam);
        Assert.assertTrue(Database.getPlayer("adam").equals(adam));
    }


    @Test
    public void getAllPlayers() {
        Player adam = new Player("adam");
        Player bill = new Player("bill");
        Database.addUser(bill);
        Database.addUser(adam);
        ArrayList<Player> expected = new ArrayList<>();
        ArrayList<Player> actual = Database.getAllPlayers();
        expected.add(adam);
        expected.add(bill);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void processStats() {
        StatsModel stats = new StatsModel(5, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0 );
        Player adam = new Player("adam", "pass", new Wallet(), stats);
        StatTracker.blackJackWins = 5;
        StatTracker.crapsWins = 1;
        StatTracker.goFishWins = 3;
        StatTracker.loopyWins = 2;
        Database.processStats(adam);
        Assert.assertEquals(StatTracker.blackJackWins, adam.getStats().getBlackJackWins());
        Assert.assertEquals(StatTracker.goFishWins, adam.getStats().getGoFishWins());
        Assert.assertEquals(StatTracker.loopyWins, adam.getStats().getLoopyWins());
        Assert.assertEquals(StatTracker.crapsWins, adam.getStats().getCrapsWins());
    }

    @Test
    public void reloadAllUsers() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("adam"));
        players.add(new Player("bill"));
        Database.reloadAllUsers(players);
        ArrayList<Player> actual = Database.getAllPlayers();
        Assert.assertEquals(players, actual);
    }

    @Test
    public void canLogin() {
        Player adam = new Player("Adam", "pass", new Wallet());
        Database.addUser(adam);
        Assert.assertFalse(Database.canLogin("bill", "pass"));
        Assert.assertFalse(Database.canLogin("Adam", "Fart"));
        Assert.assertTrue(Database.canLogin("Adam", "pass"));
    }
}