package io.zipcoder.casino.players;

import io.zipcoder.casino.models.Dice;
import io.zipcoder.casino.models.Wallet;

import java.util.ArrayList;

public class DicePlayer extends Player {
    private ArrayList<Dice> dice = new ArrayList<>();

    // For tests
    public DicePlayer(String name) {
        super(name);
    }

    public DicePlayer(String name, String password, Wallet wallet) {
        super(name, password, wallet);
    }

    public DicePlayer(Player player) {
        super(player.getName(), player.getPassword(), player.getWallet());
    }

    public int rollDice() {
        // get number of dice from the array
        // produce a random number  from the dice in the method
        int sum = 0;
        for (Dice die : dice) {
            sum += die.toss();
        }
        return sum;
    }

    public void addDice(int amt) {
        for (int i = 0; i < amt; i++) {
            this.dice.add(new Dice());
        }
    }

}
