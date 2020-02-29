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

    public void addChip(Chip chip) {
        addChip(chip, 1);
    }


    public void addChip(Chip chip, int amt) {
        if (!chips.containsKey(chip)){
            chips.put(chip, amt);
        }
        else if (chips.containsKey(chip)) {
            chips.put(chip,chips.get(chip)+amt);
        }
    }

    public Boolean subDollar(int amt) {
        if (dollars >= amt) {
            dollars -= amt;
            return true;
        } else {
            return false;
        }
    }

    public boolean subChip(Chip chip) {
        if (chips.containsKey(chip)){
            chips.put(chip,chips.get(chip)-1);
            return true;
        }
        return false;
    }

    public int getDollars() {
        return dollars;
    }

    public Map<Chip, Integer> getChips() {
        return chips;
    }
}
