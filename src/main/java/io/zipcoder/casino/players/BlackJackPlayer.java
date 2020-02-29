package io.zipcoder.casino.players;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.zipcoder.casino.games.specific.BlackJack;
import io.zipcoder.casino.models.Chip;
import io.zipcoder.casino.models.Deck;
import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.models.Wallet;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class BlackJackPlayer extends CardPlayer {

    private Wallet myWallet = new Wallet();

    // For tests
    public BlackJackPlayer(String name) {
        super(name);
    }

    public BlackJackPlayer(Player player) {
        super(player);
    }

    public void doubleDown(PlayingCard card, Chip chip) {
        addCardToHand(card);
        myWallet.subChip(chip);
    }

    @JsonIgnore
    public int getValue() {
        int value = 0;
        int handTotal = 0;
        for (int i = 0; i <this.getHand().size(); i++) {
            if (this.getHand().get(i).getValueAsInt() > 10) {
                value = 10;
            } else if (this.getHand().get(i).getValueAsInt() == 1) {
                value = 11;
            } else {
                value = this.getHand().get(i).getValueAsInt();
            }
            handTotal += value;
        }
        return handTotal;
    }
}
