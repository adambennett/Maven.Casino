package io.zipcoder.casino;

import io.zipcoder.casino.players.Player;
import org.junit.Assert;
import org.junit.Test;

public class AppTest {
    @Test
    public void getCasino() {
        App application = new App();
        application.runCasino();
        Assert.assertNotNull(application.getCasino());
    }

    @Test
    public void getCurrentPlayer() {
        App application = new App();
        application.runCasino();
        Player adam = new Player("Adam");
        application.getCasino().setCurrentPlayer(adam);
        Player expected = adam;
        Player actual = application.getCasino().getCurrentPlayer();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void isLoggedIn() {
        App application = new App();
        application.runCasino();
        Player adam = new Player("Adam");
        application.getCasino().setCurrentPlayer(adam);
        application.setLoginStatus();
        Assert.assertTrue(application.isLoggedIn());
    }

    @Test
    public void isLoggedInB() {
        App application = new App();
        application.runCasino();
        Player adam = new Player("Adam");
        application.logPlayerIn(adam);
        Assert.assertTrue(application.isLoggedIn());
    }
}
