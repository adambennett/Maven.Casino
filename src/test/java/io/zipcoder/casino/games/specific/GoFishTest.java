package io.zipcoder.casino.games.specific;

import io.zipcoder.casino.models.Deck;
import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.players.CardPlayer;
import io.zipcoder.casino.players.GoFishNPC;
import io.zipcoder.casino.players.GoFishPlayer;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GoFishTest {
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
}
