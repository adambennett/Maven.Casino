package io.zipcoder.casino.players;

import io.zipcoder.casino.models.BankTest;
import io.zipcoder.casino.models.PlayingCard;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Logger;

public class DealerTest {
    private static final Logger LOGGER = Logger.getLogger(BankTest.class.getName());

    @Test
    public void hitTest(){
        Dealer newDealer = new Dealer();
        PlayingCard card = new PlayingCard(5, PlayingCard.Suit.HEARTS);
        newDealer.hit(card);
        Integer expected = 1;
        Integer actual = newDealer.getHand().size();
        LOGGER.info(""+ actual);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void isHittingTest(){
        Dealer newDealer = new Dealer();
        PlayingCard card = new PlayingCard(PlayingCard.Rank.KING, PlayingCard.Suit.HEARTS);
        PlayingCard card2 = new PlayingCard(5, PlayingCard.Suit.HEARTS);
        newDealer.addCardToHand(card);
        newDealer.addCardToHand(card2);
        LOGGER.info(""+ newDealer.getValue());
        Assert.assertTrue(newDealer.isHitting());
    }
}
