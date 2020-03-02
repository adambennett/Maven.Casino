package io.zipcoder.casino.players;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.zipcoder.casino.models.Deck;
import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.models.Wallet;

import java.util.ArrayList;
import java.util.Collections;

public class CardPlayer extends Player {

    @JsonIgnore
    private ArrayList<PlayingCard> hand;

    // For tests
    public CardPlayer(String name) {
       super(name);
       this.hand = new ArrayList<>();
    }

    public CardPlayer(Player player) {
        super(player.getName(), player.getPassword(), player.getWallet());
        this.hand = new ArrayList<>();
    }

    @JsonIgnore
    public ArrayList<PlayingCard> getHand() {
        return hand;
    }

    public void addCardToHand(PlayingCard card) {
        this.hand.add(card);
    }

    public void removeCard(PlayingCard card) {
        this.hand.remove(card);
    }

    @JsonIgnore
    public String printHand() {
        String toRet = "";
        Collections.sort(this.getHand());
        for (PlayingCard card : this.getHand()) {
            toRet += card.getValue() + ", ";
        }
        toRet = toRet.substring(0, toRet.length() - 2);
        return toRet;
    }

    public Boolean draw(Deck gameDeck, int amt) {
        int size = this.getHand().size();
        this.getHand().addAll(gameDeck.draw(amt));
        int afterDrawSize = this.getHand().size();
        return afterDrawSize - amt == size;
    }

}
