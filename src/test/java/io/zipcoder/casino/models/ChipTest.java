package io.zipcoder.casino.models;

import org.junit.Assert;
import org.junit.Test;

public class ChipTest {

    @Test
    public void dollarValTest(){

        int expected = 1;
        int actual = Chip.ChipValue.WHITE.getValue();

        Assert.assertEquals(expected, actual);

    }

    @Test
    public void dollarValTest2(){
        int expected = 5;
        int actual = Chip.ChipValue.BLUE.getValue();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void dollarValTest3() {
        int expected = 25;
        int actual = Chip.ChipValue.GREEN.getValue();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void dollarValTest4() {
        int expected = 100;
        int actual = Chip.ChipValue.BLACK.getValue();

        Assert.assertEquals(expected, actual);
    }

}
