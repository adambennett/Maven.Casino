package io.zipcoder.casino.players;

import io.zipcoder.casino.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Logger;

public class BlackJackPlayerTest {
    private static final Logger LOGGER = Logger.getLogger(BankTest.class.getName());

    @Test
    public void doubleDownTest() {
        Wallet myWallet = new Wallet();
        Chip chip = new Chip(Chip.ChipValue.BLACK);
        myWallet.addChip(chip);
        myWallet.addChip(chip);
        PlayingCard card = new PlayingCard(5, PlayingCard.Suit.HEARTS);
        BlackJackPlayer Raheel = new BlackJackPlayer("Raheel");
        Raheel.doubleDown(card, chip);
        Integer expected = 1;
        Integer actual = Raheel.getHand().size();
        LOGGER.info(""+ actual);
        Assert.assertEquals(expected,actual);
    }

}
