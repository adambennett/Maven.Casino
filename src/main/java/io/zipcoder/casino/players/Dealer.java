package io.zipcoder.casino.players;

import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.utilities.MenuStrings;


public class Dealer extends BlackJackPlayer {

    public Dealer() {
        this(MenuStrings.getRandomOpponentName());
    }

    public Dealer(String name) {
        super(name);
    }

    public void hit(PlayingCard card){
        addCardToHand(card);
    }

    public boolean isHitting() {
        return (getValue() <= 16) ? true : false;
    }
}
