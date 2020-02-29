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


public class GoFish extends Game<GoFishPlayer, GoFishNPC> implements CardGame {

    private Deck gameDeck;
    private boolean playerTurn;
    private int playerScore;
    private int opponentScore;
    private int gameDrawAmt;
    private int scoreToWin;
    private int startingHandSize;

    public GoFish() {
        this(true, 0, 0, 1, 20, 5, 52);
    }

    public GoFish(int amountToDrawOnFish, int scoreToWin, int startingHandSize, int deckSize) {
        this(true, 0, 0, amountToDrawOnFish, scoreToWin, startingHandSize, deckSize);
    }

    public GoFish(boolean playerGoesFirst, int startingPlayerScore, int startingOpponentScore, int amountToDrawOnFish, int scoreToWin, int startingHandSize, int deckSize) {
        this.gameDeck = new Deck(deckSize);
        this.playerTurn = playerGoesFirst;
        this.playerScore = startingPlayerScore;
        this.opponentScore = startingOpponentScore;
        this.gameDrawAmt = amountToDrawOnFish;
        this.scoreToWin = scoreToWin;
        this.startingHandSize = startingHandSize;
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
        for (PlayingCard card : player.getHand()) {
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
        this.playerScore += updateHandAndGetScore(this.getCurrentPlayer());
        this.opponentScore += updateHandAndGetScore(this.getOpponent());
    }

    public boolean pollCard(PlayingCard card, GoFishPlayer playerToPoll) {
        return (playerToPoll.hasCard(card));
    }

    public void setup(GoFishPlayer player) {
        this.setCurrentPlayer(player);
        this.setOpponent(new GoFishNPC());
        for (int i = 0; i < this.startingHandSize * 2; i++) {
            if (i%2==0) { this.getCurrentPlayer().getHand().addAll(this.gameDeck.draw(1)); }
            else { this.getOpponent().getHand().addAll(this.gameDeck.draw(1)); }
        }
        ConsoleServices.print("Go Fish!");
        ConsoleServices.print(this.getOpponent().generateWelcomeMessage());
        updateScores();
    }

    public void playerTurn() {
        refreshPlayerHand();
        ConsoleServices.print("\nCards in hand: " + this.getCurrentPlayer().printHand());
        String input = ConsoleServices.getStringInput("Enter the Rank value of the card you wish to fish for: ");
        Integer guessedNum = -1;
        try {
            guessedNum = Integer.parseInt(input);
        } catch (NumberFormatException ex) { ConsoleServices.print("Please enter a card to fish for by number!"); return; }

        if (guessedNum > -1 && guessedNum < 14) {
            PlayingCard polledCard = new PlayingCard(guessedNum);
            boolean poll = pollCard(polledCard, this.getOpponent());
            if (poll) {
                ConsoleServices.print("\n                                 SUCCESS! " + this.getOpponent().getName() + " did have a " + polledCard.getValue() + "!");
                this.getOpponent().getHand().remove(polledCard);
                this.getCurrentPlayer().getHand().add(polledCard);
            }
            else {
                ConsoleServices.print("\n                                 Go Fish!");
                this.getCurrentPlayer().draw(this.gameDeck, this.gameDrawAmt);
            }
        } else { ConsoleServices.print("Please enter a card to fish for by number!"); }
    }

    public void opponentTurn() {
        refreshOpponentHand();
        PlayingCard askCard = this.getOpponent().generateCardToAsk();
        ConsoleServices.print("\n" + this.getOpponent().getName() + ": Fishing for " + askCard.getValue());
        boolean poll = pollCard(askCard, this.getCurrentPlayer());
        if (poll) {
            ConsoleServices.print("                                 FISHED! You had a " + askCard.getValueAsInt() + "! " + this.getOpponent().getName() + " has now taken it from you.");
            this.getCurrentPlayer().getHand().remove(askCard);
            this.getOpponent().getHand().add(askCard);
        } else {
            ConsoleServices.print("\n                                 Go Fish!");
            this.getOpponent().draw(this.gameDeck, this.gameDrawAmt);
        }
    }

    @Override
    public void runGame(GoFishPlayer player) {
        setup(player);
        while (!gameEndCheck()) {
            if (this.playerTurn) {
                playerTurn();
            } else {
                opponentTurn();
            }
            this.playerTurn = !this.playerTurn;
            score();
        }
    }

    public Boolean refreshPlayerHand() {
        if (this.getCurrentPlayer().getHand().size() < 1 && gameDeck.getCards().size() > 0) {
            ConsoleServices.print("Your hand is empty! Drawing 5 new cards.");
            this.getCurrentPlayer().getHand().addAll(this.gameDeck.draw(5));
            return true;
        }
        return false;
    }

    public Boolean refreshOpponentHand() {
        if (this.getOpponent().getHand().size() < 1 && gameDeck.getCards().size() > 0) {
            ConsoleServices.print(this.getOpponent().getName() + "'s hand is empty! Drawing 5 new cards.");
            this.getOpponent().getHand().addAll(this.gameDeck.draw(5));
            return true;
        }
        return false;
    }

    public void score() {
        updateScores();
        ConsoleServices.print("\nScoring...");
        ConsoleServices.print("Player Score: " + this.playerScore);
        ConsoleServices.print("Opponent Score: " + this.opponentScore);
        ConsoleServices.print("Opponent Hand Size: " + this.getOpponent().getHand().size());
        ConsoleServices.print("Draw Deck Size: " + this.gameDeck.getCards().size());
    }

    public Boolean gameEndCheck() {
        if (playerScore > this.scoreToWin || opponentScore > this.scoreToWin || gameDeck.getCards().size() < 1) {
            if (playerScore > this.scoreToWin || opponentScore > this.scoreToWin) {
                ConsoleServices.print("Game over! At least one player reached " + this.scoreToWin + "+ points!");
            } else {
                ConsoleServices.print("Game over! Draw deck has run out of cards!");
            }
            boolean playerWon = playerScore > opponentScore;
            if (playerWon) { ConsoleServices.print("You won!"); } else { ConsoleServices.print("You lost!"); }
            StatTracker.finishGame(this, playerWon);
            return true;
        }
        return false;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    public void setGameDeck(Deck gameDeck) {
        this.gameDeck = gameDeck;
    }

    public Deck getGameDeck() {
        return gameDeck;
    }
}

