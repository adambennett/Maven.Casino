package io.zipcoder.casino.games.specific;

import io.zipcoder.casino.App;
import io.zipcoder.casino.games.CardGame;
import io.zipcoder.casino.games.Game;
import io.zipcoder.casino.models.Deck;
import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.players.CardPlayer;
import io.zipcoder.casino.players.GoFishNPC;
import io.zipcoder.casino.players.GoFishPlayer;
import io.zipcoder.casino.utilities.MenuStrings;
import io.zipcoder.casino.utilities.io.ConsoleServices;
import io.zipcoder.casino.utilities.persistence.StatTracker;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GoFish implements Game<GoFishPlayer>, CardGame {

    private GoFishPlayer currentPlayer;
    private GoFishNPC opponent;
    private Deck gameDeck;
    private boolean playerTurn;
    private int playerScore;
    private int opponentScore;
    private int gameDrawAmt;
    private int scoreToWin;

    public GoFish() {
        this(0, true, 1);
    }

    public GoFish(int drawAmt) {
        this(0, true, drawAmt);
    }

    public GoFish(boolean playerGoesFirst) {
        this(0, playerGoesFirst, 1);
    }

    public GoFish(boolean playerGoesFirst, int drawAmt) {
        this(0, playerGoesFirst, drawAmt);
    }

    public GoFish(int handicap, boolean playerGoesFirst, int drawAmt) {
        this.gameDeck = new Deck();
        this.playerTurn = playerGoesFirst;
        this.playerScore = handicap;
        this.opponentScore = 0;
        this.gameDrawAmt = drawAmt;
        this.scoreToWin = 20;
    }

    public Map<Integer, Integer> fillMapWithOccurrences(CardPlayer playerToRemoveFrom) {
        Map<Integer, Integer> toRet = new HashMap<>();
        for (PlayingCard card : playerToRemoveFrom.getHand()) {
            if (toRet.containsKey(card.getValueAsInt())) {
                toRet.put(card.getValueAsInt(), toRet.get(card.getValueAsInt()) + 1);
            } else {
                toRet.put(card.getValueAsInt(), 1);
            }
        }
        return toRet;
    }

    public ArrayList<PlayingCard> fillToRemove(CardPlayer player, Map<Integer, Integer> mapToCheck) {
        ArrayList<PlayingCard> toRemove = new ArrayList<>();
        for (PlayingCard card : this.currentPlayer.getHand()) {
            if (mapToCheck.containsKey(card.getValueAsInt()) && mapToCheck.get(card.getValueAsInt()) > 1) {
                toRemove.add(card);
            }
        }
        return toRemove;
    }

    public Integer removePairsFromHand(CardPlayer player, ArrayList<PlayingCard> cardsToRemove) {

        int cardsRemoved = 0;
        for (PlayingCard remove : cardsToRemove) {
            player.getHand().remove(remove);
            cardsRemoved++;
        }
        return cardsRemoved;
    }


    public Integer updateHandAndGetScore(CardPlayer player) {
        Map<Integer, Integer> playOcc = fillMapWithOccurrences(player);
        ArrayList<PlayingCard> toRemovePlayer = fillToRemove(player, playOcc);
        return removePairsFromHand(player, toRemovePlayer);
    }

    public void updateScores() {
        this.playerScore += updateHandAndGetScore(this.currentPlayer);
        this.opponentScore += updateHandAndGetScore(this.opponent);
    }

    public boolean pollCard(PlayingCard card, CardPlayer playerToPoll) {
        return (playerToPoll.getHand().size() > 0) ? playerToPoll.getHand().contains(card) : false;
    }

    @Override
    public void runGame(GoFishPlayer player) {
        this.currentPlayer = player;
        this.opponent = new GoFishNPC();
        this.currentPlayer.setGameDrawAmt(this.gameDrawAmt);
        this.opponent.setGameDrawAmt(this.gameDrawAmt);
        boolean playerWon = false;
        boolean gameOver = false;

        for (int i = 0; i < 10; i++) {
            if (i%2==0) { this.currentPlayer.getHand().addAll(this.gameDeck.draw(1)); }
            else { this.opponent.getHand().addAll(this.gameDeck.draw(1)); }
        }

        updateScores();
        ConsoleServices.print("Go Fish!");
        ConsoleServices.print(this.opponent.generateWelcomeMessage());

        // Game loop
        while (!gameOver) {
            // Player turn
            if (this.playerTurn)
            {
                if (this.currentPlayer.getHand().size() < 1 && gameDeck.getCards().size() > 0) {
                    ConsoleServices.print("Your hand is empty! Drawing 5 new cards.");
                    this.currentPlayer.getHand().addAll(this.gameDeck.draw(5));
                } else if (this.currentPlayer.getHand().size() > 0) {
                    ConsoleServices.print("\nCards in hand: " + this.currentPlayer.printHand());
                    String input = ConsoleServices.getStringInput("Enter the Rank value of the card you wish to fish for: ");
                    Integer guessedNum = -1;
                    try {
                        guessedNum = Integer.parseInt(input);
                    } catch (NumberFormatException ex) { ConsoleServices.print("Please enter a card to fish for by number!"); continue; }

                    if (guessedNum > -1 && guessedNum < 14) {
                        PlayingCard polledCard = new PlayingCard(guessedNum);
                        boolean poll = pollCard(polledCard, this.opponent);
                        if (poll) {
                            ConsoleServices.print("\n                                 SUCCESS! " + this.opponent.getName() + " did have a " + polledCard.getValue() + "!");
                            this.opponent.getHand().remove(polledCard);
                            this.currentPlayer.getHand().add(polledCard);
                        }
                        else {
                            ConsoleServices.print("\n                                 Go Fish!");
                            this.currentPlayer.goFish(this.gameDeck);
                        }
                        this.playerTurn = false;
                    } else { ConsoleServices.print("Please enter a card to fish for by number!"); }
                }
            }

            // Opponent turn
            else
            {
                if (this.opponent.getHand().size() < 1 && gameDeck.getCards().size() > 0) {
                    ConsoleServices.print("Your hand is empty! Drawing 5 new cards.");
                    this.opponent.getHand().addAll(this.gameDeck.draw(5));
                } else if (this.opponent.getHand().size() > 0) {
                    PlayingCard askCard = this.opponent.generateCardToAsk();
                    ConsoleServices.print("\n" + this.opponent.getName() + ": Fishing for " + askCard.getValue());
                    boolean poll = pollCard(askCard, this.currentPlayer);
                    if (poll) {
                        ConsoleServices.print("                                 FISHED! You had a " + askCard.getValueAsInt() + "! Your opponent has now taken it from you.");
                        this.currentPlayer.getHand().remove(askCard);
                        this.opponent.getHand().add(askCard);
                    } else {
                        ConsoleServices.print("\n                                 Go Fish!");
                        this.opponent.goFish(this.gameDeck);
                    }
                    this.playerTurn = true;
                }
            }

            updateScores();
            ConsoleServices.print("\nScoring...");
            ConsoleServices.print("Player Score: " + this.playerScore);
            ConsoleServices.print("Opponent Score: " + this.opponentScore);
            ConsoleServices.print("Opponent Hand Size: " + this.opponent.getHand().size());
            ConsoleServices.print("Draw Deck Size: " + this.gameDeck.getCards().size());

            if (playerScore > this.scoreToWin || opponentScore > this.scoreToWin || gameDeck.getCards().size() < 1) {
                if (playerScore > this.scoreToWin || opponentScore > this.scoreToWin) {
                    ConsoleServices.print("Game over! At least one player reached " + this.scoreToWin + "+ points!");
                } else {
                    ConsoleServices.print("Game over! Draw deck has run out of cards!");
                }
                gameOver = true;
                playerWon = playerScore > opponentScore;
                if (playerWon) { ConsoleServices.print("You won!"); } else { ConsoleServices.print("You lost!"); }
                StatTracker.finishGame(this, playerWon);
            }

        }
    }

    public GoFishPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public GoFishNPC getOpponent() {
        return opponent;
    }

    public Deck getGameDeck() {
        return gameDeck;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public int getGameDrawAmt() {
        return gameDrawAmt;
    }

    public int getScoreToWin() {
        return scoreToWin;
    }
}

// game logic
//       dealers deal players 7 cards
//       deal cards to 7 method


//        public static void main(String args[]){
//            int hand = 0;
//            CardPlayer player[] = new CardPlayer[];
//            for(int i = 0; i < 7; i++) {
//                for (int j = 0; j < 2; j++) {
//                    ArrayList<PlayingCard> c = new ArrayList<PlayingCard>();
//                    c = deck.draw(i);
//                    player[j].getHand();
//
//                }
//
//            }
//}



//       put remaining deck in center of playing field method


//       players put down matchingPairs dealt method

//       currentPlayer ==  activePlayer method

//                activePlayer asks opponent if have askedCard that matches in remaining hand
//                if activePlayer has opponent gives to activePlayer
//                    activePlayer puts matchingPairs down and continues
//                     activePlayer asks for another askedCard to match in hand
//                else if opponent doesnt have askedCard
//                        tells activePlayer  "go fish"
//                        activePlayer must draw card from deck
//                        if pulledCard matches askedCard, activePlayer can put
//                            down matchingPairs and say "got what i asked" and
//                            continue to be activePlayer
//                        else if pulledCard matches another card in hand activePlayer
//                                puts down matchingPairs and loses turn
//                else play goes to opponent
//         Once a player has 0 cards game over players count matchingPairs most wins



