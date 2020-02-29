package io.zipcoder.casino.models;

import io.zipcoder.casino.App;

import java.util.ArrayList;

public class Bank {

    public static int convertChipsToDollars(ArrayList<Chip> chips) {
        int sum = 0;
        for (Chip i : chips) {
            sum += i.getDollarVal();
        }
        return sum;
    }

    public static ArrayList<Chip> buyBestValue(int amt) {

        ArrayList<Chip> myChips = new ArrayList<>();
        int black = 0;
        int green = 0;
        int blue = 0;
        int white = 0;

        if (amt > 100){
            black = amt / 100;
        }

        if (amt % 100 > 25){
            green = ((amt % 100)/25);
        }

        if (amt % 100 > 25 && amt % 25 > 5){
            blue = ((amt % 100 % 25) / 5);
        }

        if (amt % 100 > 25 && amt % 25 > 5 && amt % 5 > 1){
            white = amt % 100 % 25 % 5;
        }

        myChips.addAll(buyBlack(black));
        myChips.addAll(buyBlue(blue));
        myChips.addAll(buyGreen(green));
        myChips.addAll(buyWhite(white));

        return myChips;
    }

    public static ArrayList<Chip> buyBlack(int amt) {
        ArrayList<Chip> blackChips = new ArrayList<>();
        amt = notEnoughChips(amt, Chip.ChipValue.BLACK.getValue());
        for (int i = 0; i < amt ; i++) {
            blackChips.add(new Chip(Chip.ChipValue.BLACK));
        }

        return blackChips;

    }
    public static ArrayList<Chip> buyBlue(int amt) {
        ArrayList<Chip> blueChips = new ArrayList<>();
        amt = notEnoughChips(amt, Chip.ChipValue.BLUE.getValue());
        for (int i = 0; i <amt ; i++) {
            blueChips.add(new Chip(Chip.ChipValue.BLUE));
        }
        return blueChips;

    }
    public static ArrayList<Chip> buyGreen(int amt) {

        ArrayList<Chip> greenChips = new ArrayList<>();
        amt = notEnoughChips(amt, Chip.ChipValue.GREEN.getValue());
        for (int i = 0; i <amt ; i++) {
            greenChips.add(new Chip(Chip.ChipValue.GREEN));
        }
        return greenChips;

    }
    public static ArrayList<Chip> buyWhite(int amt) {

        ArrayList<Chip> whiteChips = new ArrayList<>();
        amt = notEnoughChips(amt, Chip.ChipValue.WHITE.getValue());

        for (int i = 0; i <amt ; i++) {
            whiteChips.add(new Chip(Chip.ChipValue.WHITE));
        }
        return whiteChips;

    }

    public static Integer notEnoughChips(int amt, int chipType){
        Wallet myWallet = App.getCasino().getCurrentPlayer().getWallet();
        int totalDollars = myWallet.getDollars();
        int howManyCanIBuy = totalDollars / chipType;

        if(amt > howManyCanIBuy){
            amt = howManyCanIBuy;
        }

        return amt;
    }

}
