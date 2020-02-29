package io.zipcoder.casino.utilities;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MenuStringsTest {

    @Test
    public void getRandomOpponentName() {
        String actual = MenuStrings.getRandomOpponentName();
        Assert.assertTrue(!actual.equals(""));
    }
}