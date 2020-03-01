package io.zipcoder.casino.players;

import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.models.Wallet;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Logger;

public class CardPlayerTest {

    private static final Logger LOGGER = Logger.getLogger(CardPlayerTest.class.getName());


    @Test
    public void getHandTest(){
        String name = "Raheel Uppal";
        int age = 25;
        PlayingCard ACE = new PlayingCard(PlayingCard.Rank.ACE, PlayingCard.Suit.HEARTS);
        ArrayList<PlayingCard> hand = new ArrayList<>();
        hand.add(ACE);
        hand.add(ACE);
        hand.add(ACE);
        hand.add(ACE);

        CardPlayer Raheel = new CardPlayer(name);

        Raheel.addCardToHand(ACE);
        Raheel.addCardToHand(ACE);
        Raheel.addCardToHand(ACE);
        Raheel.addCardToHand(ACE);

        ArrayList playerHand = Raheel.getHand();

        String output = "";

        for (int i = 0; i <playerHand.size() ; i++) {
            output += playerHand.get(i).toString();
        }
        LOGGER.info(output);

        String expected = output;
        String actual = "ACE of HEARTSACE of HEARTSACE of HEARTSACE of HEARTS";

        Assert.assertEquals(expected,actual);

    }

    @Test
    public void removeFromHandTest(){
        String name = "Raheel Uppal";
        int age = 25;
        PlayingCard ACE = new PlayingCard(PlayingCard.Rank.ACE, PlayingCard.Suit.HEARTS);
        ArrayList<PlayingCard> hand = new ArrayList<>();
        hand.add(ACE);
        hand.add(ACE);
        hand.add(ACE);
        hand.add(ACE);
        hand.remove(ACE);

        CardPlayer Raheel = new CardPlayer(name);

        Raheel.addCardToHand(ACE);
        Raheel.addCardToHand(ACE);
        Raheel.addCardToHand(ACE);
        Raheel.addCardToHand(ACE);
        Raheel.removeCard(ACE);

        ArrayList playerHand = Raheel.getHand();

        String output = "";

        for (int i = 0; i <playerHand.size() ; i++) {
            output += playerHand.get(i).toString();
        }
        LOGGER.info(output);

        String expected = output;
        String actual = "ACE of HEARTSACE of HEARTSACE of HEARTS";

        Assert.assertEquals(expected,actual);

    }

    @Test
    public void addCardToHandTest() {
        CardPlayer adam = new CardPlayer("Adam");
        adam.addCardToHand(new PlayingCard(5));
        Assert.assertTrue(adam.getHand().get(0).compareTo(new PlayingCard(5)) == 0);
    }

    @Test
    public void addCardToHandTestB() {
        CardPlayer adam = new CardPlayer("Adam");
        adam.addCardToHand(new PlayingCard(5));
        Assert.assertTrue(adam.getHand().get(0).equals(new PlayingCard(5)));
    }

    @Test
    public void addCardToHandTestC() {
        CardPlayer adam = new CardPlayer("Adam");
        adam.addCardToHand(new PlayingCard(5));
        Assert.assertTrue(!adam.getHand().get(0).equals(new PlayingCard(10)));
    }

    @Test
    public void addCardToHandTestD() {
        CardPlayer adam = new CardPlayer("Adam");
        adam.addCardToHand(new PlayingCard(5));
        Assert.assertTrue(adam.getHand().get(0).compareTo(new PlayingCard(4)) == 1);
    }

    @Test
    public void addCardToHandTestE() {
        CardPlayer adam = new CardPlayer("Adam");
        adam.addCardToHand(new PlayingCard(5));
        Assert.assertTrue(adam.getHand().get(0).compareTo(new PlayingCard(6)) == -1);
    }

    @Test
    public void addCardToHandTestF() {
        CardPlayer adam = new CardPlayer("Adam");
        adam.addCardToHand(new PlayingCard(5));
        Assert.assertFalse(adam.getHand().get(0).compareTo(new PlayingCard(5)) == -1);
    }

    @Test
    public void addCardToHandTestG() {
        CardPlayer adam = new CardPlayer("Adam");
        adam.addCardToHand(new PlayingCard(5));
        Assert.assertFalse(adam.getHand().get(0).compareTo(new PlayingCard(5)) == 1);
    }

    @Test
    public void addCardToHandTestH() {
        CardPlayer adam = new CardPlayer("Adam");
        adam.addCardToHand(new PlayingCard(5));
        Object test = new PlayingCard(10);
        Assert.assertTrue(!adam.getHand().get(0).equals(test));
    }

    @Test
    public void addCardToHandTestI() {
        CardPlayer adam = new CardPlayer("Adam");
        adam.addCardToHand(new PlayingCard(5));
        Object test = "Test";
        Assert.assertFalse(adam.getHand().get(0).equals(test));
    }

    @Test
    public void addCardToHandTestJ() {
       PlayingCard test = new PlayingCard(5);
       PlayingCard.Rank expected = PlayingCard.Rank.FIVE;
       PlayingCard.Rank actual = test.getValue();
       Assert.assertEquals(expected, actual);
    }

}
