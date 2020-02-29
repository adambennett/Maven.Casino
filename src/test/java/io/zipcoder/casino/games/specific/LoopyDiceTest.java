package io.zipcoder.casino.games.specific;

import io.zipcoder.casino.players.LoopyDicePlayer;
import org.junit.Assert;
import org.junit.Test;

public class LoopyDiceTest {

    @Test
    public void TestPlayerIsWinner() {
        LoopyDice testGame = new LoopyDice();
        LoopyDicePlayer adam = new LoopyDicePlayer("Adam");
        LoopyDicePlayer op = new LoopyDicePlayer("Opponent");
        testGame.setCurrentPlayer(adam);
        testGame.setOpponent(op);
        testGame.setPlayerScore(0);
        testGame.setOpponentScore(5);
        testGame.setPar(5);
        Assert.assertTrue(testGame.playerIsWinner());
    }

    @Test
    public void TestGameOver() {
        LoopyDice testGame = new LoopyDice();
        LoopyDicePlayer adam = new LoopyDicePlayer("Adam");
        LoopyDicePlayer op = new LoopyDicePlayer("Opponent");
        testGame.setCurrentPlayer(adam);
        testGame.setOpponent(op);
        testGame.setPlayerScore(3);
        testGame.setOpponentScore(3);
        testGame.setPar(5);
        Assert.assertFalse(testGame.gameOver());
        testGame.setPlayerScore(5);
        Assert.assertTrue(testGame.gameOver());
    }

    @Test
    public void TestSimulatedGame() {
        LoopyDice testGame = new LoopyDice();
        LoopyDicePlayer adam = new LoopyDicePlayer("Adam");
        LoopyDicePlayer op = new LoopyDicePlayer("Opponent");
        adam.addDice(3);
        op.addDice(3);
        adam.setSeed(58l);
        op.setSeed(33l);
        testGame.setCurrentPlayer(adam);
        testGame.setOpponent(op);
        Assert.assertEquals(LoopyDice.RoundResult.OPPONENT_DICE_UP, testGame.runRound());
    }
}