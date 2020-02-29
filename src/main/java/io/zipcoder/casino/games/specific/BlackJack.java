package io.zipcoder.casino.games.specific;

import io.zipcoder.casino.App;
import io.zipcoder.casino.games.CardGame;
import io.zipcoder.casino.games.Game;
import io.zipcoder.casino.models.Chip;
import io.zipcoder.casino.models.Deck;
import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.models.Wallet;
import io.zipcoder.casino.players.BlackJackPlayer;
import io.zipcoder.casino.players.Dealer;
import io.zipcoder.casino.utilities.MenuStrings;
import io.zipcoder.casino.utilities.io.ConsoleServices;
import io.zipcoder.casino.utilities.persistence.StatTracker;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class BlackJack extends Game<BlackJackPlayer, Dealer> implements CardGame {

    private ArrayList<Chip> bets = new ArrayList<>();
    private Deck gameDeck;
    private boolean playerTurn;
    private boolean findWinner;
    private Chip white = new Chip(Chip.ChipValue.WHITE);
    private Chip blue = new Chip(Chip.ChipValue.BLUE);
    private Chip green = new Chip(Chip.ChipValue.GREEN);
    private Chip black = new Chip(Chip.ChipValue.BLACK);


    public BlackJack(){this(1, true);}


    public BlackJack(int drawAmt, boolean playerGoesFirst){
        this.gameDeck = new Deck();
        this.playerTurn = playerGoesFirst;
    }

    @Override
    public void runGame(BlackJackPlayer player) {
        this.setCurrentPlayer(player);
        this.setOpponent(new Dealer());
        Boolean playerWon = false;
        Boolean gameOver = false;

        // game logic
        String betInput = ConsoleServices.getStringInput("Please place your bet\n");
        String betAmount = "";
        betAmount = (betInput.toLowerCase());
        if(betAmount.equals("black")){
            bets.add(0, black);
        }
        if(betAmount.equals("green")){
            bets.add(green);
        }
        if(betAmount.equals("blue")){
            bets.add(blue);
        }
        if(betAmount.equals("green")){
            bets.add(white);
        }

        for (int i = 0; i < 4 ; i++) {
            if (i%2 == 0){
                this.getCurrentPlayer().getHand().addAll(
                        this.gameDeck.draw(1));}

            else { this.getOpponent().getHand().addAll(this.gameDeck.draw(1));}
        }
        this.getCurrentPlayer().getValue();
        while (!gameOver) {
            if (this.playerTurn)
            {
                ConsoleServices.print("Cards in hand: " + this.getCurrentPlayer().printHand());
                ConsoleServices.print("Your hand value is: "+ this.getCurrentPlayer().getValue());
                ConsoleServices.print("The dealers' hand value is: "+ this.getOpponent().getValue());
                String input = ConsoleServices.getStringInput("Would you like to Hit or Stay? ");
                String hitOrStay = "";
                hitOrStay = input.toLowerCase();
                if (hitOrStay.equals("hit")) {
                    this.getCurrentPlayer().getHand().addAll(this.gameDeck.draw(1));
                    ConsoleServices.print("Your hand value is: "+ this.getCurrentPlayer().getValue());
                    if(this.getCurrentPlayer().getValue() > 21){
                        playerWon = false;
                        ConsoleServices.print("Sorry you lost!");
                        break;
                    }
                    String input2 = ConsoleServices.getStringInput("Would you like to Hit or Stay? ");
                    hitOrStay = (input2.toLowerCase());
                    if (hitOrStay.equals("hit")){
                        this.getCurrentPlayer().getHand().addAll(this.gameDeck.draw(1));
                        ConsoleServices.print("Your hand value is: "+ this.getCurrentPlayer().getValue());
                        if(this.getCurrentPlayer().getValue() > 21){
                            playerWon = false;
                            ConsoleServices.print("Sorry you lost!");
                            break;
                        }
                    }
                }
                if (hitOrStay.equals("stay")){
                    ConsoleServices.print("It's the dealers turn now."); this.playerTurn = false;
                }
            }
            else {
                if (this.getOpponent().isHitting()){
                    this.getOpponent().getHand().addAll(this.gameDeck.draw(1));
                    ConsoleServices.print("Dealers' hand value is: "+ this.getOpponent().getValue());
                }
                this.findWinner = true;
            }


            if (this.findWinner) {
                ConsoleServices.print("Let's see who won!\nYour hand: "+ this.getCurrentPlayer().getValue()+"\nDealers hand: "+ this.getOpponent().getValue());
                if(this.getCurrentPlayer().getValue() < 22 && this.getCurrentPlayer().getValue() > this.getOpponent().getValue() || this.getOpponent().getValue() > 21) {
                    playerWon = true;
                    ConsoleServices.print("You've won! "+ bets.get(0).getDollarVal()+ "$");

                }
               else {
                   playerWon = false;
                    ConsoleServices.print("Sorry you lost!");
                }
            }
            gameOver = true;
        }



        //ask player to hit, stand, split, doubleDown
        //reveal dealers hand, if dealer is < 16 hit, if dealer is >=17 stand
        //if dealer isbust || player hand > dealer, Player wins

        StatTracker.finishGame(this, playerWon);
    }
}
