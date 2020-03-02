package io.zipcoder.casino.games.specific;

import io.zipcoder.casino.models.Chip;
import io.zipcoder.casino.models.Deck;
import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.players.BlackJackPlayer;
import io.zipcoder.casino.players.Dealer;
import io.zipcoder.casino.players.Player;
import io.zipcoder.casino.utilities.persistence.StatTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlackJackTest {

    private static BlackJack game;
    private static BlackJackPlayer adam;

    @Before
    public void setup() {
        game = new BlackJack();
        adam = new BlackJackPlayer("Adam");
        StatTracker.clearAllStats();
    }

    @Test
    public void setupPlayers() {
        Assert.assertTrue(game.setupPlayers(adam));
        Assert.assertTrue(game.getCurrentPlayer().equals(adam));
        Assert.assertNotNull(game.getOpponent());
    }

    @Test
    public void initializeGame() {
        boolean actual = game.initializeGame(adam);
        Assert.assertTrue(actual);
    }

    @Test
    public void initializeGameB() {
        boolean actual = game.initializeGame(null);
        Assert.assertFalse(actual);
    }

    @Test
    public void hit() {
        game.initializeGame(adam);
        Assert.assertEquals(2, game.getCurrentPlayer().getHand().size());
        game.hit(adam);
        Assert.assertEquals(3, game.getCurrentPlayer().getHand().size());
    }

    @Test
    public void doubleDown() {
        game.getChipsBet().add(new Chip(Chip.ChipValue.BLACK));
        Assert.assertFalse(game.getChipsBet().size() == 2);
        game.doubleDown();
        Assert.assertTrue(game.getChipsBet().size() == 2);
    }

    @Test
    public void canDoubleDown() {
        Assert.assertFalse(game.canDoubleDown());
        game.getChipsBet().add(new Chip(Chip.ChipValue.WHITE));
        Assert.assertTrue(game.canDoubleDown());
    }

    @Test
    public void surrender() {
        game.initializeGame(adam);
        Assert.assertFalse(StatTracker.overallLosses == 1);
        game.surrender();
        Assert.assertTrue(StatTracker.overallLosses == 1);
    }

    @Test
    public void dealerTurn() {
        game.initializeGame(adam);
        game.getOpponent().getHand().clear();
        game.getOpponent().getHand().add(new PlayingCard(PlayingCard.Rank.ACE, PlayingCard.Suit.DIAMONDS));
        game.getOpponent().getHand().add(new PlayingCard(PlayingCard.Rank.KING, PlayingCard.Suit.DIAMONDS));
        Integer expected = 21;
        Integer actual = game.dealerTurn();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void runGameAfterPlayerTurn() {
        game.initializeGame(adam);
        game.getOpponent().getHand().clear();
        game.getCurrentPlayer().getHand().clear();

        game.getOpponent().getHand().add(new PlayingCard(PlayingCard.Rank.ACE, PlayingCard.Suit.DIAMONDS));
        game.getOpponent().getHand().add(new PlayingCard(PlayingCard.Rank.KING, PlayingCard.Suit.SPADES));
        game.getOpponent().getHand().add(new PlayingCard(PlayingCard.Rank.KING, PlayingCard.Suit.CLUBS));

        game.getCurrentPlayer().getHand().add(new PlayingCard(PlayingCard.Rank.ACE, PlayingCard.Suit.DIAMONDS));
        game.getCurrentPlayer().getHand().add(new PlayingCard(PlayingCard.Rank.ACE, PlayingCard.Suit.SPADES));
        Assert.assertTrue(game.runGameAfterPlayerTurn() == -1);
    }

    @Test
    public void runGameAfterPlayerTurnB() {
        game.initializeGame(adam);
        game.getOpponent().getHand().clear();
        game.getCurrentPlayer().getHand().clear();

        game.getOpponent().getHand().add(new PlayingCard(PlayingCard.Rank.TEN, PlayingCard.Suit.DIAMONDS));
        game.getOpponent().getHand().add(new PlayingCard(PlayingCard.Rank.SEVEN, PlayingCard.Suit.SPADES));

        game.getCurrentPlayer().getHand().add(new PlayingCard(PlayingCard.Rank.ACE, PlayingCard.Suit.DIAMONDS));
        game.getCurrentPlayer().getHand().add(new PlayingCard(PlayingCard.Rank.KING, PlayingCard.Suit.SPADES));
        game.getCurrentPlayer().getHand().add(new PlayingCard(PlayingCard.Rank.KING, PlayingCard.Suit.CLUBS));
        Assert.assertTrue(game.runGameAfterPlayerTurn() == 1);
    }

    @Test
    public void runGameAfterPlayerTurnC() {
        game.initializeGame(adam);
        game.getOpponent().getHand().clear();
        game.getCurrentPlayer().getHand().clear();

        game.getOpponent().getHand().add(new PlayingCard(PlayingCard.Rank.ACE, PlayingCard.Suit.HEARTS));
        game.getOpponent().getHand().add(new PlayingCard(PlayingCard.Rank.KING, PlayingCard.Suit.HEARTS));
        game.getOpponent().getHand().add(new PlayingCard(PlayingCard.Rank.KING, PlayingCard.Suit.DIAMONDS));

        game.getCurrentPlayer().getHand().add(new PlayingCard(PlayingCard.Rank.ACE, PlayingCard.Suit.DIAMONDS));
        game.getCurrentPlayer().getHand().add(new PlayingCard(PlayingCard.Rank.KING, PlayingCard.Suit.SPADES));
        game.getCurrentPlayer().getHand().add(new PlayingCard(PlayingCard.Rank.KING, PlayingCard.Suit.CLUBS));
        Assert.assertTrue(game.runGameAfterPlayerTurn() == 0);
    }

    @Test
    public void beatDealer() {
        game.getChipsBet().add(new Chip(Chip.ChipValue.BLACK));
        game.initializeGame(adam);
        Assert.assertFalse(StatTracker.blackJackWins == 1);
        game.beatDealer();
        Integer expected = 100;
        Integer actual = StatTracker.totalLifetimeChipWinnings;
        Assert.assertTrue(StatTracker.blackJackWins == 1);
        Assert.assertEquals(expected, actual);
        Assert.assertTrue(adam.getWallet().getNumberOfAllChips() == 2);
    }

    @Test
    public void push() {
        game.getChipsBet().add(new Chip(Chip.ChipValue.BLACK));
        game.initializeGame(adam);
        game.push();
        Integer expected = 1;
        Integer actual = adam.getWallet().getNumberOfAllChips();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getBetterScore() {
        Integer testA = 5;
        Integer testB = 10;

        Integer expected = testB;
        Integer actual = game.getBetterScore(testA, testB);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void finalHandValueEval() {
        Integer testA = 21;
        Integer testB = 20;
        Integer testC = 22;
        Integer expected = testA;
        Integer actual = game.finalHandValueEval(testC, testA, testB);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getHandValue() {
        ArrayList<PlayingCard> testHand = new ArrayList<>();
        testHand.add(new PlayingCard(PlayingCard.Rank.ACE, PlayingCard.Suit.DIAMONDS));
        testHand.add(new PlayingCard(PlayingCard.Rank.FOUR, PlayingCard.Suit.DIAMONDS));
        testHand.add(new PlayingCard(PlayingCard.Rank.FIVE, PlayingCard.Suit.DIAMONDS));
        testHand.add(new PlayingCard(PlayingCard.Rank.ACE, PlayingCard.Suit.DIAMONDS));
        Integer expected = 21;
        Integer actual = game.getHandValue(testHand);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getHandValueB() {
        ArrayList<PlayingCard> testHand = new ArrayList<>();
        testHand.add(new PlayingCard(PlayingCard.Rank.KING, PlayingCard.Suit.DIAMONDS));
        testHand.add(new PlayingCard(PlayingCard.Rank.QUEEN, PlayingCard.Suit.DIAMONDS));
        testHand.add(new PlayingCard(PlayingCard.Rank.JACK, PlayingCard.Suit.DIAMONDS));
        testHand.add(new PlayingCard(PlayingCard.Rank.ACE, PlayingCard.Suit.DIAMONDS));
        Integer expected = 31;
        Integer actual = game.getHandValue(testHand);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getHandValueC() {
        ArrayList<PlayingCard> testHand = new ArrayList<>();
        testHand.add(new PlayingCard(PlayingCard.Rank.FIVE, PlayingCard.Suit.DIAMONDS));
        testHand.add(new PlayingCard(PlayingCard.Rank.SIX, PlayingCard.Suit.DIAMONDS));
        testHand.add(new PlayingCard(PlayingCard.Rank.EIGHT, PlayingCard.Suit.DIAMONDS));
        testHand.add(new PlayingCard(PlayingCard.Rank.TWO, PlayingCard.Suit.DIAMONDS));
        Integer expected = 21;
        Integer actual = game.getHandValue(testHand);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void isPlayerBust() {
        adam.getHand().add(new PlayingCard(10));
        adam.getHand().add(new PlayingCard(10));
        adam.getHand().add(new PlayingCard(10));
        Assert.assertTrue(game.isPlayerBust(adam));
    }


    @Test
    public void initialHandBlackjackCheck() {
        game.getChipsBet().add(new Chip(Chip.ChipValue.BLACK));
        game.initializeGame(adam);
        game.getCurrentPlayer().getHand().clear();
        game.getCurrentPlayer().getHand().add(new PlayingCard(10));
        game.getCurrentPlayer().getHand().add(new PlayingCard(10));
        game.getCurrentPlayer().getHand().add(new PlayingCard(1));

        game.getOpponent().getHand().clear();
        game.getOpponent().getHand().add(new PlayingCard(3));
        game.getOpponent().getHand().add(new PlayingCard(3));
        game.getOpponent().getHand().add(new PlayingCard(1));

        Assert.assertFalse(StatTracker.blackJackWins == 1);
        boolean check = game.initialHandBlackjackCheck();
        Assert.assertTrue(check);
        Assert.assertTrue(StatTracker.blackJackWins == 1);
        Assert.assertEquals(100, StatTracker.totalLifetimeChipWinnings);
        Assert.assertEquals(new Integer(2), adam.getWallet().getNumberOfAllChips());
    }

    @Test
    public void initialHandBlackjackCheckPush() {
        game.getChipsBet().add(new Chip(Chip.ChipValue.BLACK));
        game.initializeGame(adam);
        game.getCurrentPlayer().getHand().clear();
        game.getCurrentPlayer().getHand().add(new PlayingCard(10));
        game.getCurrentPlayer().getHand().add(new PlayingCard(10));
        game.getCurrentPlayer().getHand().add(new PlayingCard(1));

        game.getOpponent().getHand().clear();
        game.getOpponent().getHand().add(new PlayingCard(10));
        game.getOpponent().getHand().add(new PlayingCard(10));
        game.getOpponent().getHand().add(new PlayingCard(1));

        boolean check = game.initialHandBlackjackCheck();
        Assert.assertTrue(check);
        Assert.assertTrue(StatTracker.blackJackWins == 0);
        Assert.assertEquals(0, StatTracker.totalLifetimeChipWinnings);
        Assert.assertEquals(new Integer(1), adam.getWallet().getNumberOfAllChips());
    }

    @Test
    public void initialHandBlackjackCheckNoBlackjack() {
        game.getChipsBet().add(new Chip(Chip.ChipValue.BLACK));
        game.initializeGame(adam);
        game.getCurrentPlayer().getHand().clear();
        game.getCurrentPlayer().getHand().add(new PlayingCard(6));
        game.getCurrentPlayer().getHand().add(new PlayingCard(5));
        game.getCurrentPlayer().getHand().add(new PlayingCard(1));

        game.getOpponent().getHand().clear();
        game.getOpponent().getHand().add(new PlayingCard(10));
        game.getOpponent().getHand().add(new PlayingCard(10));
        game.getOpponent().getHand().add(new PlayingCard(1));

        boolean check = game.initialHandBlackjackCheck();
        Assert.assertFalse(check);
        Assert.assertTrue(StatTracker.blackJackWins == 0);
        Assert.assertEquals(0, StatTracker.totalLifetimeChipWinnings);
    }

    @Test
    public void processUserBetInput() {
        Map<String, Integer> expected = new HashMap<>();
        ArrayList<String> chipColors = new ArrayList<>();
        ArrayList<Integer> chipAmts = new ArrayList<>();
        chipColors.add("GREEN");
        chipAmts.add(10);
        expected.put("GREEN", 10);
        Map<String, Integer> actual = game.processUserBetInput(chipColors, chipAmts);
        for (Map.Entry<String, Integer> entry : expected.entrySet()) {
            Assert.assertEquals(entry.getValue(), actual.get(entry.getKey()));
        }
    }


    @Test
    public void handleUserBets() {
        game.initializeGame(adam);
        ArrayList<String> chipColors = new ArrayList<>();
        ArrayList<Integer> chipAmts = new ArrayList<>();
        chipColors.add("GREEN");
        chipAmts.add(10);
        adam.getWallet().addChip(new Chip(Chip.ChipValue.GREEN), 10);
        Integer expected = 10;
        Integer actual = game.handleUserBets(chipColors, chipAmts);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void handleUserBetsB() {
        game.initializeGame(adam);
        ArrayList<String> chipColors = new ArrayList<>();
        ArrayList<Integer> chipAmts = new ArrayList<>();
        chipColors.add("GREEN");
        chipAmts.add(10);
        adam.getWallet().addChip(new Chip(Chip.ChipValue.GREEN), 5);
        Integer expected = 5;
        Integer actual = game.handleUserBets(chipColors, chipAmts);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getDollarValueOfBet() {
        Assert.assertEquals(new Integer(0), game.getDollarValueOfBet());
        game.getChipsBet().add(new Chip(Chip.ChipValue.BLACK));
        Assert.assertEquals(new Integer(100), game.getDollarValueOfBet());
    }

    @Test
    public void printBet() {
        Assert.assertFalse(game.printBet());
        game.getChipsBet().add(new Chip(Chip.ChipValue.BLACK));
        Assert.assertTrue(game.printBet());
    }

    @Test
    public void printBetB() {
        Assert.assertFalse(game.printBet());
        game.getChipsBet().add(new Chip(Chip.ChipValue.GREEN));
        Assert.assertTrue(game.printBet());
    }

    @Test
    public void printBetC() {
        Assert.assertFalse(game.printBet());
        game.getChipsBet().add(new Chip(Chip.ChipValue.BLUE));
        Assert.assertTrue(game.printBet());
    }

    @Test
    public void printBetD() {
        Assert.assertFalse(game.printBet());
        game.getChipsBet().add(new Chip(Chip.ChipValue.WHITE));
        Assert.assertTrue(game.printBet());
    }

    @Test
    public void getDealerHand() {
        game.initializeGame(adam);
        game.getOpponent().getHand().clear();
        game.getOpponent().getHand().add(new PlayingCard(5));
        game.getOpponent().getHand().add(new PlayingCard(6));
        String expected = "FIVE";
        String actual = game.getDealerHand();
        Assert.assertEquals(expected, actual);
    }
}
