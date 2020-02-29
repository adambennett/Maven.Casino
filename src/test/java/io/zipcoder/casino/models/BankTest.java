package io.zipcoder.casino.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Logger;

public class BankTest {
    private static final Logger LOGGER = Logger.getLogger(BankTest.class.getName());


    @Test
    public void convertChipsToDollarsTest() {

        ArrayList<Chip> myChips = new ArrayList<>();

        int sum = 0;

        myChips.add(new Chip(Chip.ChipValue.BLACK));
        myChips.add(new Chip(Chip.ChipValue.BLUE));
        myChips.add(new Chip(Chip.ChipValue.GREEN));
        myChips.add(new Chip(Chip.ChipValue.WHITE));

        for (int i = 0; i < myChips.size(); i++) {
            sum += myChips.get(i).getDollarVal();
            LOGGER.info("" + sum);
        }

        Integer expected = Bank.convertChipsToDollars(myChips);
        Integer actual = 131;

        Assert.assertEquals(expected, actual);

    }

    @Test
    public void BuyBestValueTest() {

        ArrayList<Chip> myChips = Bank.buyBestValue(583);
        int blue = 0;
        int black = 0;
        int green = 0;
        int white = 0;


        for (Chip myChip : myChips) {

            if (myChip.getDollarVal() == Chip.ChipValue.BLACK.getValue()) {
                black++;

            } else if (myChip.getDollarVal() == Chip.ChipValue.GREEN.getValue()) {
                green++;

            } else if (myChip.getDollarVal() == Chip.ChipValue.BLUE.getValue()) {
                blue++;

            } else {
                white++;
            }
        }
        int blackExpected = 5;
        int greenExpected = 3;
        int blueExpected = 1;
        int whiteExpected = 3;

        Assert.assertEquals(blackExpected,black);
        Assert.assertEquals(greenExpected,green);
        Assert.assertEquals(blueExpected,blue);
        Assert.assertEquals(whiteExpected,white);
        LOGGER.info("" + black + " " + green + " " +   blue + " " + white);

    }

    @Test
    public void BuyChipsTest() {
        ArrayList<Chip> blueChips;
        ArrayList<Chip> blackChips;
        ArrayList<Chip> whiteChips;
        ArrayList<Chip> greenChips;


        blackChips = Bank.buyBlack(3);
        blueChips = Bank.buyBlue(2);
        greenChips = Bank.buyGreen(1);
        whiteChips = Bank.buyWhite(5);


        int nmbrOfBlackExpected = 3;
        int nmbrOfGreenExpected = 1;
        int nmbrOfWhiteExpected = 5;
        int nmbrOfBlueExpected = 2;

        int nmbrOfBlackActual = blackChips.size();
        int nmbrOfGreenActual = greenChips.size();
        int nmbrOfWhiteActual = whiteChips.size();
        int nmbrOfBlueActual = blueChips.size();

        Assert.assertEquals(nmbrOfBlackExpected,nmbrOfBlackActual);
        Assert.assertEquals(nmbrOfBlueExpected, nmbrOfBlueActual);
        Assert.assertEquals(nmbrOfGreenExpected,nmbrOfGreenActual);
        Assert.assertEquals(nmbrOfWhiteExpected,nmbrOfWhiteActual);
        LOGGER.info(" " + nmbrOfBlackActual + " " + nmbrOfBlueActual + " " + nmbrOfGreenActual + " " + nmbrOfWhiteActual);
    }

}
