package io.zipcoder.casino.games;

import io.zipcoder.casino.games.specific.BlackJack;
import io.zipcoder.casino.players.BlackJackPlayer;
import io.zipcoder.casino.players.Dealer;
import io.zipcoder.casino.players.Player;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testToString() {
        BlackJack bj = new BlackJack();
        bj.initializeGame(new BlackJackPlayer("adam"));
        bj.setOpponent(new Dealer("ted"));
        String expected = "adamted";
        String actual = bj.toString();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void testEquals() {
        BlackJack other = new BlackJack();
        BlackJack bj = new BlackJack();
        other.initializeGame(new BlackJackPlayer("robbie"));
        bj.initializeGame(new BlackJackPlayer("adam"));
        bj.setOpponent(new Dealer("ted"));
        Assert.assertFalse(other.equals(bj));
    }

    @Test
    public void testEqualsB() {
        BlackJack other = new BlackJack();
        BlackJack bj = new BlackJack();
        other.initializeGame(new BlackJackPlayer("adam"));
        bj.initializeGame(new BlackJackPlayer("adam"));
        bj.setOpponent(new Dealer("ted"));
        Assert.assertFalse(other.equals(bj));
    }

    @Test
    public void testEqualsC() {
        BlackJack other = new BlackJack();
        BlackJack bj = new BlackJack();
        other.initializeGame(new BlackJackPlayer("robbie"));
        bj.initializeGame(new BlackJackPlayer("adam"));
        bj.setOpponent(new Dealer("ted"));
        Assert.assertFalse(other.equals(bj));
    }

    @Test
    public void testEqualsD() {
        BlackJack other = new BlackJack();
        BlackJack bj = new BlackJack();
        other.initializeGame(new BlackJackPlayer("robbie"));
        bj.initializeGame(new BlackJackPlayer("adam"));
        bj.setOpponent(new Dealer("ted"));
        other.setOpponent(new Dealer("ted"));
        Assert.assertFalse(other.equals(bj));
    }

    @Test
    public void testEqualsE() {
        BlackJack other = new BlackJack();
        String temp = "";
        other.initializeGame(new BlackJackPlayer("robbie"));
        Assert.assertFalse(other.equals(temp));
    }


    @Test
    public void testEqualsF() {
        BlackJack other = new BlackJack();
        String expected = "New Game";
        String actual = other.toString();
        Assert.assertEquals(expected, actual);
    }
}