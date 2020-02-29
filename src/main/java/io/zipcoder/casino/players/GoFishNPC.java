package io.zipcoder.casino.players;

import io.zipcoder.casino.models.Deck;
import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.utilities.MenuStrings;

import java.util.ArrayList;
import java.util.Random;


public class GoFishNPC extends GoFishPlayer {

    private Random rng;
    private ArrayList<String> welcomeMessages;

    public GoFishNPC() {
        this(MenuStrings.getRandomOpponentName(), new ArrayList<>());
    }

    public GoFishNPC(String name, ArrayList<String> welcomeMessages) {
        super(name);
        this.rng = new Random();
        this.welcomeMessages = new ArrayList<>();
        this.welcomeMessages.addAll(welcomeMessages);
    }

    public String generateWelcomeMessage() {
       return (this.welcomeMessages.size() > 0) ? this.welcomeMessages.get(this.rng.nextInt(this.welcomeMessages.size())) : "Hello and good luck!";
    }

    public PlayingCard generateCardToAsk() {
        return (this.getHand().size() > 0) ? this.getHand().get(this.rng.nextInt(this.getHand().size())) : null;
    }

}
