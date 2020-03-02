package io.zipcoder.casino.models;

import io.zipcoder.casino.App;
import io.zipcoder.casino.players.Player;
import io.zipcoder.casino.utilities.io.AbstractConsole;
import io.zipcoder.casino.utilities.io.BuyChipsConsole;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WalletTest{

    @Test
    public void addDollarTest(){
        Wallet wallet = new Wallet(0);
        int amt = 150;
        int dollarsTotal = wallet.getDollars() + amt;
        wallet.addDollar(dollarsTotal);
        int expected = wallet.getDollars();
        Assert.assertEquals(expected, dollarsTotal);
    }

    @Test
    public void addDollarTestB(){
        Wallet wallet = new Wallet(0, new HashMap<>(), new Player("Adam"));
        int amt = 150;
        int dollarsTotal = wallet.getDollars() + amt;
        wallet.addDollar(dollarsTotal);
        int expected = wallet.getDollars();
        Assert.assertEquals(expected, dollarsTotal);
    }

    @Test
    public void addChipTest(){
        Wallet wallet = new Wallet(100);
        Chip white = new Chip(Chip.ChipValue.WHITE);
        Chip black = new Chip(Chip.ChipValue.BLACK);
        wallet.addChip(white);
        wallet.addChip(white);
        wallet.addChip(black);
        int expected = 3;
        int totalChips = 0;
        for (Map.Entry<Chip, Integer> i : wallet.getChips().entrySet()) {
            totalChips += i.getValue();
        }

        Assert.assertEquals(expected, totalChips);
    }

    @Test
    public void subDollarTest(){
        Wallet wallet = new Wallet(90);
        int amt = 25;
        wallet.subDollar(amt);
        int actual = wallet.getDollars();
        int expected = 90 - amt;

        Assert.assertEquals(expected, actual);

    }

    @Test
    public void subChipTest(){
        Wallet wallet = new Wallet();
        wallet.addChip(new Chip(Chip.ChipValue.BLACK), 10);
        wallet.subChip("BLACK", 5);
        int totalChips = 0;
        for (Map.Entry<Chip, Integer> chipType : wallet.getChips().entrySet()) {
            totalChips+= chipType.getValue();
        }

        int expected = 5;
        int actual = totalChips;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyChipTypeTest() {
        App appl = new App();
        appl.runCasino();
        Player adam = new Player("Adam");
        appl.logPlayerIn(adam);
        adam.getWallet().addDollar(10);
        BuyChipsConsole buyChipsConsole = new BuyChipsConsole();
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("10");
        Boolean bought = buyChipsConsole.buyChip(AbstractConsole.Command.WHITE, arguments);
        Integer actual = adam.getWallet().getNumOfChips(Chip.ChipValue.BLUE);
        Integer expected = 0;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notEnoughMoneyTest() {
        Player adam = new Player("Adam");
        adam.getWallet().addDollar(10);
        Assert.assertFalse(adam.getWallet().subDollar(50));
    }
}
