package io.zipcoder.casino.games.specific;

import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.players.CardPlayer;
import io.zipcoder.casino.players.GoFishPlayer;
import org.junit.Assert;
import org.junit.Test;

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
}
