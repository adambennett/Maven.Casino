package io.zipcoder.casino.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.zipcoder.casino.players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {

    private int dollars;
    private Map<Chip, Integer> chips;
    private Player owner;

    public Wallet() {
        this(0);
    }

    public Wallet(int dollars) {
        this.dollars = dollars;
        this.chips = new HashMap<>();
    }

    @JsonCreator
    public Wallet(@JsonProperty("dollars") int dollars, @JsonProperty("chips") Map<Chip, Integer> chips, @JsonProperty("owner") Player owner) {
        this.dollars = dollars;
        this.chips = new HashMap<>();
        this.chips.putAll(chips);
        this.owner = owner;
    }

    public boolean addDollar(int amt) {
        dollars += amt;
        return false;
    }

    public boolean addChip(Chip chip) {
        if (!chips.containsKey(chip)){
            chips.put(chip, 1);
        }
        else if (chips.containsKey(chip)) {
            chips.put(chip,chips.get(chip)+1);
                    }

        return false;
    }

    public boolean subDollar(int amt) {
        dollars -= amt;
        return false;
    }

    public boolean subChip(Chip chip) {
        if (chips.containsKey(chip)){
            chips.put(chip,chips.get(chip)-1);
        }

        return false;
    }

    public boolean subChip(int dollarAmt) {
        return false;
    }

    public int getDollars() {
        return dollars;
    }

    public Map<Chip, Integer> getChips() {
        return chips;
    }

    public Player getOwner() {
        return owner;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public void setChips(Map<Chip, Integer> chips) {
        this.chips = chips;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
