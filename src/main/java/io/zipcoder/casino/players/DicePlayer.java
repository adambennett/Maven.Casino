package io.zipcoder.casino.players;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.zipcoder.casino.models.Dice;
import io.zipcoder.casino.models.Wallet;

import java.util.ArrayList;

public class DicePlayer extends Player {
    private ArrayList<Dice> dice = new ArrayList<>();

    // For tests
    public DicePlayer(String name) {
        super(name);
    }

    public DicePlayer(Player player) {
        super(player.getName(), player.getPassword(), player.getWallet());
    }

    public int rollDice() {
        int sum = 0;
        for (Dice die : dice) {
            sum += die.toss();
        }
        return sum;
    }

    @JsonIgnore
    public Integer getNumDice() {
        return this.dice.size();
    }

    public void emptyDice() {
        this.dice.clear();
    }

    public void addDice(int amt) {
        for (int i = 0; i < amt; i++) {
            this.dice.add(new Dice());
        }
    }

    public void setSeed() {
        for (Dice d : this.dice) {
            d.setSeed();
        }
    }

    public void setSeed(Long seed) {
        for (Dice d : this.dice) {
            d.setSeed(seed);
        }
    }

}
