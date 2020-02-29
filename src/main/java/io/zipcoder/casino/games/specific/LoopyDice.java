package io.zipcoder.casino.games.specific;

import io.zipcoder.casino.App;
import io.zipcoder.casino.games.DiceGame;
import io.zipcoder.casino.games.Game;
import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.players.LoopyDicePlayer;
import io.zipcoder.casino.utilities.MenuStrings;
import io.zipcoder.casino.utilities.io.AbstractConsole;
import io.zipcoder.casino.utilities.io.ConsoleServices;
import io.zipcoder.casino.utilities.io.MainConsole;
import io.zipcoder.casino.utilities.persistence.StatTracker;

import java.io.Console;

public class LoopyDice extends Game<LoopyDicePlayer, LoopyDicePlayer> implements DiceGame {

    private int playerScore = 0;
    private int opponentScore = 0;
    private int par = 3;
    private int round = 1;

    public LoopyDice() {
        this(3);
    }

    public LoopyDice(int par) {
        this.par = par;
    }

    @Override
    public void runGame(LoopyDicePlayer player) {
        ConsoleServices.print(MenuStrings.asciiDice);
        ConsoleServices.print("Let's get Loopy!");
        this.setCurrentPlayer(player);
        this.setOpponent(new LoopyDicePlayer("Looper"));
        this.getCurrentPlayer().addDice(3);
        this.getOpponent().addDice(3);

        ConsoleServices.print("Each player has 3 dice. Enter 'Roll' to calculate the next turn, or enter 'AutoRoll' to simulate the entire game.");
        while (!gameOver()) {
            String input = ConsoleServices.getStringInput("Enter 'Roll' to calculate the next turn, or enter 'AutoRoll' to simulate the entire game.");
            if (input.toLowerCase().equals("roll")) {
                if (loopyDiceTurnLogic()) {
                    return;
                }
            } else if (input.toLowerCase().equals("autoroll")) {
               while (!gameOver()) {
                   if (loopyDiceTurnLogic()) {
                       return;
                   }
               }
            } else {
                input = ConsoleServices.getStringInput("Bad command!\nEnter 'Roll' to calculate the next turn, or enter 'AutoRoll' to simulate the entire game.");
            }
        }
    }

    private boolean loopyDiceTurnLogic() {
        Boolean playerWon;
        runRound();
        if (gameOver() && playerIsWinner()) {
            playerWon = true;
            ConsoleServices.print("You won!");
            StatTracker.finishGame(this, playerWon);
            return true;
        } else if (gameOver()) {
            playerWon = false;
            ConsoleServices.print("You lost!");
            StatTracker.finishGame(this, playerWon);
            return true;
        }
        this.round++;
        return false;
    }

    public Boolean playerIsWinner() {
        return (this.playerScore < this.par && this.opponentScore >= this.par) ? true : false;
    }

    public Boolean gameOver() {
        if (this.playerScore >= this.par || this.opponentScore >= this.par) {
            return true;
        }
        return false;
    }


    public RoundResult runRound() {
        int playerSum = this.getCurrentPlayer().rollDice();
        int oppSum = this.getOpponent().rollDice();
        int playerBustVal = 15 + (2 * (this.getCurrentPlayer().getNumDice() - 3));
        int oppBustVal = 15 + (2 * (this.getOpponent().getNumDice() - 3));
        if (playerSum > playerBustVal || oppSum > oppBustVal) {
            if (playerSum > playerBustVal) {
                this.getCurrentPlayer().emptyDice();
                this.getCurrentPlayer().addDice(3);
                this.playerScore++;
                int neededToLose = this.par - this.playerScore;
                if (neededToLose < 1) {
                    ConsoleServices.print("\nYou busted with a roll of " + playerSum + "!!!\n");
                } else {
                    ConsoleServices.print("\nYou busted with a roll of " + playerSum + "!\nYou have scored 1 point. Score " + neededToLose + " more points and you lose!\n");
                }
            }

            if (oppSum > oppBustVal) {
                this.getOpponent().emptyDice();
                this.getOpponent().addDice(3);
                this.opponentScore++;
                int neededToLose = this.par - this.opponentScore;
                if (neededToLose < 1) {
                    ConsoleServices.print("\nOpponent busted with a roll of " + oppSum + "!!!\n");
                } else {
                    ConsoleServices.print("\nOpponent busted with a roll of " + oppSum + "!\nOpponent has scored 1 point. If they score " + neededToLose + " more points, you win!\n");
                }

            }

            if (playerSum > playerBustVal && oppSum > oppBustVal) {
                return RoundResult.BOTH_BUST;
            } else if (playerSum > playerBustVal) {
                return RoundResult.PLAYER_BUST;
            } else {
                return RoundResult.OPPONENT_BUST;
            }

        } else {
            if (playerSum > oppSum) {
                this.getCurrentPlayer().addDice(1);
                ConsoleServices.print("\nYou rolled a higher number than the opponent (" + playerSum + " vs " + oppSum + ")!\nPLAYER DICE + 1");
                printResultsOfRound(playerSum, oppSum);
            } else if (oppSum > playerSum) {
                this.getOpponent().addDice(1);
                ConsoleServices.print("\nThe opponent rolled a higher number than you (" + oppSum + " vs " + playerSum + ")!\nOPPONENT DICE + 1");
                printResultsOfRound(playerSum, oppSum);
            } else {
                ConsoleServices.print("Push! No dice added.\n");
            }

            if (playerSum > oppSum) {
                return RoundResult.PLAYER_DICE_UP;
            } else if (oppSum > playerSum) {
                return RoundResult.OPPONENT_DICE_UP;
            } else {
                return RoundResult.PUSH;
            }
        }
    }


    private void printResultsOfRound(int playerRoll, int oppRoll) {
        int playerBustVal;
        int oppBustVal;
        playerBustVal = 15 + (2 * (this.getCurrentPlayer().getNumDice() - 3));
        oppBustVal = 15 + (2 * (this.getOpponent().getNumDice() - 3));

        String test = "" +
                "*************************************************************************\n" +
                "***                      Round " + this.round + "                     ***\n" +
                "*************************************************************************\n" +
                "***        |  Player                   ||    Opponent                 ***\n" +
                "***  Roll  |  "+playerRoll+"           ||    "+oppRoll+"              ***\n" +
                "***  Dice  |  Player                   ||    Opponent                 ***\n" +
                "***  Score |  Player                   ||    Opponent                 ***\n" +
                "***  Bust  |  Player                   ||    Opponent                 ***\n" +
                "***  Score |  Player                   ||    Opponent                 ***\n" +
                "*************************************************************************\n" +
                "***                          Enter a command                          ***\n" +
                "*************************************************************************\n" ;

        ConsoleServices.print("Player Dice   | " + this.getCurrentPlayer().getNumDice() + " - [Bust: " + playerBustVal + "]");
        ConsoleServices.print("Opponent Dice | " + this.getOpponent().getNumDice() + " - [Bust: " + oppBustVal + "]" + "\n\n");
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public enum RoundResult {
        PUSH,
        OPPONENT_DICE_UP,
        PLAYER_DICE_UP,
        PLAYER_BUST,
        OPPONENT_BUST,
        BOTH_BUST
    }
}
