package io.zipcoder.casino.players;

import org.junit.Assert;
import org.junit.Test;

public class DicePlayerTest {
    private DicePlayer plr;

    @Test
    public void rollDiceTest (){
        //given
        DicePlayer plr = new DicePlayer("Moe");
        plr.addDice(2);
        plr.setSeed();
        //when
        int result = plr.rollDice();
        //then
        Assert.assertEquals(8, result);

    }

    @Test
    public void dicePlayerContTest(){
        //given
        String expectedName = "Knuckles Lou";
        DicePlayer dplay = new DicePlayer(expectedName);
        //when
        String actualName = dplay.getName();
        //then
        Assert.assertEquals(expectedName, actualName);
    }

    @Test
    public void dicePlayerContTest2(){
        //given
        String expectedName = "Ashy Larry";
        DicePlayer dplay = new DicePlayer(expectedName);
        //when
        String actualName = dplay.getName();
        //then
        Assert.assertEquals(expectedName, actualName);
    }

    @Test
    public void emptyDice() {
        DicePlayer player = new DicePlayer("Adam");
        player.addDice(5);
        Assert.assertEquals(new Integer(5), player.getNumDice());
        player.emptyDice();
        Assert.assertEquals(new Integer(0), player.getNumDice());
    }
}
