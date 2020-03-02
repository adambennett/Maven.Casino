package io.zipcoder.casino.players;

import io.zipcoder.casino.models.PlayingCard;


public class GoFishPlayer extends CardPlayer {

    public GoFishPlayer(String name) {
        super(name);
    }

    public GoFishPlayer(Player player) {
        super(player);
    }

    public boolean hasCard(PlayingCard card) {
        return (this.getHand().size() > 0) ? this.getHand().contains(card) : false;
    }
}
