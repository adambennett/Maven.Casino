package io.zipcoder.casino.games.specific;

import io.zipcoder.casino.models.Deck;
import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.players.CardPlayer;
import io.zipcoder.casino.players.GoFishNPC;
import io.zipcoder.casino.players.GoFishPlayer;
import io.zipcoder.casino.utilities.persistence.StatTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GoFishTest {

    @Before
    public void setup() {
        StatTracker.clearAllStats();
    }

    @Test
    public void pollCard() {
        GoFish game = new GoFish();
        GoFishPlayer testPlayer = new GoFishPlayer("Adam");
        testPlayer.getHand().addAll(game.getGameDeck().draw(52));
        Assert.assertTrue(game.pollCard(new PlayingCard(2), testPlayer));
        testPlayer.getHand().clear();
        Assert.assertFalse(game.pollCard(new PlayingCard(2), testPlayer));
    }

    @Test
    public void TestRefreshPlayerHand() {
        GoFish game = new GoFish();
        game.setCurrentPlayer(new GoFishPlayer("Adam"));
        game.setOpponent(new GoFishNPC());
        game.setGameDeck(new Deck());
        Assert.assertTrue(game.refreshPlayerHand());
    }

    @Test
    public void TestRefreshPlayerHandB() {
        GoFish game = new GoFish();
        GoFishPlayer adam = new GoFishPlayer("Adam");
        game.setCurrentPlayer(adam);
        game.setOpponent(new GoFishNPC());
        game.setGameDeck(new Deck());
        game.setup(adam);
        Assert.assertFalse(game.refreshPlayerHand());
    }

    @Test
    public void TestRefreshOpponentHand() {
        GoFish game = new GoFish();
        game.setCurrentPlayer(new GoFishPlayer("Adam"));
        game.setOpponent(new GoFishNPC());
        game.setGameDeck(new Deck());
        Assert.assertTrue(game.refreshOpponentHand());
    }

    @Test
    public void TestGameEndOnDeckOut() {
        GoFish game = new GoFish();
        game.setGameDeck(new Deck(0));
        Assert.assertTrue(game.gameEndCheck());
    }

    @Test
    public void TestGameEndOnScore() {
        GoFish game = new GoFish();
        game.setCurrentPlayer(new GoFishPlayer("Adam"));
        game.setOpponent(new GoFishNPC());
        game.setPlayerScore(21);
        game.setOpponentScore(19);
        Assert.assertTrue(game.gameEndCheck());
    }

    @Test
    public void TestGameEndOnScoreB() {
        GoFish game = new GoFish();
        game.setCurrentPlayer(new GoFishPlayer("Adam"));
        game.setOpponent(new GoFishNPC());
        game.setPlayerScore(3);
        game.setOpponentScore(4);
        Assert.assertFalse(game.gameEndCheck());
    }

    @Test
    public void fillMapWithOccurrences() {
        GoFish game = new GoFish();
        GoFishPlayer adam = new GoFishPlayer("Adam");
        GoFishNPC npc = new GoFishNPC();
        adam.getHand().add(new PlayingCard(2));
        adam.getHand().add(new PlayingCard(2));
        adam.getHand().add(new PlayingCard(3));
        adam.getHand().add(new PlayingCard(1));
        adam.getHand().add(new PlayingCard(13));
        game.setCurrentPlayer(adam);
        game.setOpponent(npc);
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(2, 2);
        expected.put(3, 1);
        expected.put(1, 1);
        expected.put(13, 1);
        Map<Integer, Integer> actual = game.fillMapWithOccurrences(adam);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fillToRemove() {
        GoFish game = new GoFish();
        GoFishPlayer adam = new GoFishPlayer("Adam");
        GoFishNPC npc = new GoFishNPC();
        adam.getHand().add(new PlayingCard(2));
        adam.getHand().add(new PlayingCard(2));
        adam.getHand().add(new PlayingCard(3));
        adam.getHand().add(new PlayingCard(1));
        adam.getHand().add(new PlayingCard(13));
        game.setCurrentPlayer(adam);
        game.setOpponent(npc);
        Map<Integer, Integer> mapp = game.fillMapWithOccurrences(adam);
        ArrayList<PlayingCard> actual = game.fillToRemove(adam, mapp);
        ArrayList<PlayingCard> expected = new ArrayList<>();
        expected.add(new PlayingCard(2));
        expected.add(new PlayingCard(2));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removePairsFromHand() {
        GoFish game = new GoFish();
        GoFishPlayer adam = new GoFishPlayer("Adam");
        GoFishNPC npc = new GoFishNPC();
        adam.getHand().add(new PlayingCard(2));
        adam.getHand().add(new PlayingCard(2));
        adam.getHand().add(new PlayingCard(3));
        adam.getHand().add(new PlayingCard(1));
        adam.getHand().add(new PlayingCard(13));
        game.setCurrentPlayer(adam);
        game.setOpponent(npc);
        Map<Integer, Integer> mapp = game.fillMapWithOccurrences(adam);
        ArrayList<PlayingCard> listy = game.fillToRemove(adam, mapp);
        Integer actual = game.removePairsFromHand(adam, listy);
        Integer expected = 2;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateHandAndGetScore() {
        GoFish game = new GoFish();
        GoFishPlayer adam = new GoFishPlayer("Adam");
        GoFishNPC npc = new GoFishNPC();
        adam.getHand().add(new PlayingCard(2));
        adam.getHand().add(new PlayingCard(2));
        adam.getHand().add(new PlayingCard(3));
        adam.getHand().add(new PlayingCard(1));
        adam.getHand().add(new PlayingCard(13));
        game.setCurrentPlayer(adam);
        game.setOpponent(npc);
        Integer actual = game.updateHandAndGetScore(adam);
        Integer expected = 2;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void ConstructorTest() {
        GoFish test = new GoFish(2, 20, 6, 60);
        Integer drawExpected = 2;
        Integer scoreExpected = 20;
        Integer handExpected = 6;
        Integer deckExpected = 60;
        Integer drawActual = test.getGameDrawAmt();
        Integer scoreActual = test.getScoreToWin();
        Integer handActual = test.getStartingHandSize();
        Integer deckActual = test.getGameDeck().getCards().size();
        Assert.assertEquals(drawExpected, drawActual);
        Assert.assertEquals(scoreExpected, scoreActual);
        Assert.assertEquals(handExpected, handActual);
        Assert.assertEquals(deckExpected, deckActual);
    }

    @Test
    public void setupTest() {
        GoFish gofish = new GoFish();
        GoFishPlayer adam = new GoFishPlayer("Adam");
        GoFishNPC opp = new GoFishNPC();
        gofish.setCurrentPlayer(adam);
        gofish.setOpponent(opp);
        Assert.assertFalse(adam.getHand().size() > 0);
        gofish.setup(adam);
        Assert.assertTrue(adam.getHand().size() == gofish.getStartingHandSize() - gofish.getPlayerScore());
    }

    @Test
    public void updateScoresTest() {
        GoFish gofish = new GoFish();
        GoFishPlayer adam = new GoFishPlayer("Adam");
        GoFishNPC opp = new GoFishNPC();
        gofish.setCurrentPlayer(adam);
        gofish.setOpponent(opp);
        adam.getHand().add(new PlayingCard(5));
        adam.getHand().add(new PlayingCard(5));
        opp.getHand().add(new PlayingCard(5));
        opp.getHand().add(new PlayingCard(6));
        Assert.assertEquals(0, gofish.getPlayerScore());
        gofish.updateScores();
        Assert.assertEquals(2, gofish.getPlayerScore());
    }

    @Test
    public void scoreTest() {
        GoFish gofish = new GoFish();
        GoFishPlayer adam = new GoFishPlayer("Adam");
        GoFishNPC opp = new GoFishNPC();
        gofish.setCurrentPlayer(adam);
        gofish.setOpponent(opp);
        adam.getHand().add(new PlayingCard(5));
        adam.getHand().add(new PlayingCard(5));
        opp.getHand().add(new PlayingCard(5));
        opp.getHand().add(new PlayingCard(6));
        Assert.assertEquals(0, gofish.getPlayerScore());
        gofish.score();
        Assert.assertEquals(2, gofish.getPlayerScore());
    }

    @Test
    public void opponentTurnTestFishing() {
        GoFish gofish = new GoFish();
        GoFishPlayer adam = new GoFishPlayer("Adam");
        GoFishNPC opp = new GoFishNPC();
        gofish.setCurrentPlayer(adam);
        gofish.setOpponent(opp);
        adam.getHand().add(new PlayingCard(5));
        adam.getHand().add(new PlayingCard(4));
        opp.getHand().add(new PlayingCard(5));
        opp.getHand().add(new PlayingCard(4));
        Assert.assertTrue(gofish.opponentTurn());
    }

    @Test
    public void opponentTurnTestGoFish() {
        GoFish gofish = new GoFish();
        GoFishPlayer adam = new GoFishPlayer("Adam");
        GoFishNPC opp = new GoFishNPC();
        gofish.setCurrentPlayer(adam);
        gofish.setOpponent(opp);
        adam.getHand().add(new PlayingCard(6));
        adam.getHand().add(new PlayingCard(8));
        opp.getHand().add(new PlayingCard(5));
        opp.getHand().add(new PlayingCard(4));
        Assert.assertFalse(gofish.opponentTurn());
    }

    @Test
    public void runGameTest() {
        GoFish gofish = new GoFish(1, 1, 1, 1);
        GoFishPlayer adam = new GoFishPlayer("Adam");
        GoFishNPC opp = new GoFishNPC();
        gofish.setCurrentPlayer(adam);
        gofish.setOpponent(opp);
        adam.getHand().add(new PlayingCard(6));
        adam.getHand().add(new PlayingCard(6));
        opp.getHand().add(new PlayingCard(5));
        opp.getHand().add(new PlayingCard(4));
        Assert.assertFalse(StatTracker.goFishWins == 1);
        gofish.runGame(adam);
        Assert.assertTrue(StatTracker.goFishWins == 1);
    }
}
