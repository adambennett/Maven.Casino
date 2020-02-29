package io.zipcoder.casino.utilities.io;

import io.zipcoder.casino.App;
import io.zipcoder.casino.models.Casino;
import io.zipcoder.casino.models.Chip;
import io.zipcoder.casino.players.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BuyChipsConsoleTest {

    @Test
    public void buyChip() {
        App appl = new App();
        appl.runCasino();
        Player adam = new Player("Adam");
        appl.logPlayerIn(adam);
        adam.getWallet().addDollar(10);
        BuyChipsConsole buyChipsConsole = new BuyChipsConsole();
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("10");
        Boolean bought = buyChipsConsole.buyChip(AbstractConsole.Command.WHITE, arguments);
        Integer whiteChips = adam.getWallet().getNumOfChips(Chip.ChipValue.WHITE);
        Assert.assertTrue(bought);
        Assert.assertEquals(new Integer(10), whiteChips);
    }
}