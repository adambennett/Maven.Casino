package io.zipcoder.casino.models;

import java.util.Random;

public class Dice {

    private int numFaces;
    private Random rng;

    public Dice() {
        this(6);
    }

    public Dice(int numFaces) {
        this.numFaces = numFaces;
        this.rng = new Random();
    }

    public int toss() {
        return rng.nextInt(numFaces) + 1;
    }

    public int getNumFaces() {
        return this.numFaces;
    }

    public void setSeed() { this.rng.setSeed(72); }

    public void setSeed(Long seed) {
        this.rng.setSeed(seed);
    }

    public void setNumFaces(int numFaces) {
        this.numFaces = numFaces;
    }
}
