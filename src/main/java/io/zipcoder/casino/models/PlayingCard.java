package io.zipcoder.casino.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayingCard implements Comparable<PlayingCard> {

    private Rank value;
    private Suit suit;

    public PlayingCard(int value) {
        this(value, Suit.DIAMONDS);
    }

    public PlayingCard(Rank value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public PlayingCard(int value, Suit suit) {
        this.suit = suit;
        for (Rank r : Rank.values()) {
            if (r.getVal() == value) {
                this.value = r;
                break;
            }
        }
    }

    public Rank getValue() {
        return value;
    }

    public int getValueAsInt() {
        return value.getVal();
    }

    @Override
    public int compareTo(PlayingCard o) {
        return (this.getValueAsInt() > o.getValueAsInt()) ? 1 : (o.getValueAsInt() > this.getValueAsInt()) ? -1 : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PlayingCard) {
            return this.getValueAsInt() == ((PlayingCard) o).getValueAsInt();
        }
        return super.equals(o);
    }

    public enum Suit {
        HEARTS,
        SPADES,
        CLUBS,
        DIAMONDS
    }

    public enum Rank {
        ACE(1),
        TWO (2),
        THREE (3),
        FOUR (4),
        FIVE (5),
        SIX (6),
        SEVEN (7),
        EIGHT (8),
        NINE (9),
        TEN (10),
        JACK (11),
        QUEEN (12),
        KING (13);

        private final int value;

        Rank(int i) {
            this.value = i;
        }

        public int getVal() {
            return this.value;
        }
    }

    @Override
    public String toString() {
        return this.value + " of " + this.suit;
    }
}
