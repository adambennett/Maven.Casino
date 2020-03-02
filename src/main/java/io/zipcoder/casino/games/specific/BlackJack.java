package io.zipcoder.casino.games.specific;

import io.zipcoder.casino.games.CardGame;
import io.zipcoder.casino.games.Game;
import io.zipcoder.casino.models.Chip;
import io.zipcoder.casino.models.Deck;
import io.zipcoder.casino.models.PlayingCard;
import io.zipcoder.casino.models.Wallet;
import io.zipcoder.casino.players.BlackJackPlayer;
import io.zipcoder.casino.players.CardPlayer;
import io.zipcoder.casino.players.Dealer;
import io.zipcoder.casino.utilities.io.ConsoleServices;
import io.zipcoder.casino.utilities.persistence.StatTracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class BlackJack extends Game<BlackJackPlayer, Dealer> implements CardGame {

    private Deck gameDeck;
    private Integer amountOfChipsBet;
    private Integer dollarValueOfAllChipsBet;
    private ArrayList<Chip> chipsBet;
    private ArrayList<String> namesOfChipColors;
    private Map<String, PlayerCommand> commandMap;

    public enum PlayerCommand {
        HIT,
        STAY,
        DOUBLE_DOWN,
        SURRENDER
    }

    public BlackJack() {
        commandMap = new HashMap<>();
        chipsBet = new ArrayList<>();
        namesOfChipColors = new ArrayList<>();
        namesOfChipColors.add("black");
        namesOfChipColors.add("green");
        namesOfChipColors.add("white");
        namesOfChipColors.add("blue");
        commandMap.put("hit", PlayerCommand.HIT);
        commandMap.put("stay", PlayerCommand.STAY);
        commandMap.put("double", PlayerCommand.DOUBLE_DOWN);
        commandMap.put("surrender", PlayerCommand.SURRENDER);
        gameDeck = new Deck(ThreadLocalRandom.current().nextInt(1, 9) * 52);
    }

    // Game setup
    public Boolean setupPlayers(BlackJackPlayer player) {
        if (player == null) { return false; }
        this.setCurrentPlayer(player);
        this.setOpponent(new Dealer());
        return true;
    }

    public void finalGameSetup() {
        this.getCurrentPlayer().draw(gameDeck, 2);
        this.getOpponent().draw(gameDeck, 2);
        ConsoleServices.print("\nPlayer: " + this.getCurrentPlayer().printHand() + " (" + getHandValue(this.getCurrentPlayer().getHand()) + ")");
        ConsoleServices.print("Dealer: " + getDealerHand());
    }

    public Boolean initializeGame(BlackJackPlayer player) {
        if (!setupPlayers(player)) {
            return false;
        }
        this.amountOfChipsBet = setupBet();
        this.dollarValueOfAllChipsBet = getDollarValueOfBet();
        printBet();
        finalGameSetup();
        return true;
    }

    // Player turn action functions
    public void hit(CardPlayer player) {
        player.draw(gameDeck, 1);
    }

    public void doubleDown() {
        ArrayList<Chip> newChips = new ArrayList<>();
        if (canDoubleDown()) {
            newChips.addAll(this.chipsBet);
        }
        this.chipsBet.addAll(newChips);
        printBet();
    }

    public Boolean canDoubleDown() {
        return this.chipsBet.size() > 0;
    }

    public void surrender() {
        StatTracker.finishGame(this, false);
        StatTracker.updateChipWinnings(this.dollarValueOfAllChipsBet / 2);
        this.getCurrentPlayer().getWallet().addChip(new Chip(Chip.ChipValue.WHITE), this.dollarValueOfAllChipsBet / 2);
        ConsoleServices.print("Surrendered! Game over! You lost! Returning half your bet in the form of " + this.dollarValueOfAllChipsBet / 2 + " White chips.");
    }

    // Player & Opponent turns
    public Boolean runPlayerTurn() {
        boolean stay = false;
        while (!isPlayerBust(this.getCurrentPlayer()) && !stay) {
            String input = ConsoleServices.getStringInput("\nEnter a turn action (hit, stay, double, surrender): ");
            PlayerCommand cmd = this.commandMap.get(input);
            if (cmd != null) {
                switch (cmd) {
                    case HIT: hit(this.getCurrentPlayer()); break;
                    case STAY: return true;
                    case DOUBLE_DOWN:
                        if (canDoubleDown()) {
                            doubleDown();
                            hit(this.getCurrentPlayer());
                            stay = true;
                            break;
                        } else {
                            ConsoleServices.print("Cannot double down, as you have not bet any chips.");
                            continue;
                        }
                    case SURRENDER: surrender(); return false;
                }
                ConsoleServices.print("\nPlayer: " + this.getCurrentPlayer().printHand() + " (" + getHandValue(this.getCurrentPlayer().getHand()) + ")");
                ConsoleServices.print("Dealer: " + getDealerHand());
            } else {
                ConsoleServices.print("Bad input. Please enter a valid game command.");
            }
        }
        return stay;
    }

    public Integer dealerTurn() {
        if (getHandValue(this.getOpponent().getHand()) < 17) {
            hit(this.getOpponent());
            ConsoleServices.print("Dealer is hitting...");
        }
        ConsoleServices.print("\nPlayer: " + this.getCurrentPlayer().printHand() + " (" + getHandValue(this.getCurrentPlayer().getHand()) + ")");
        ConsoleServices.print("Dealer: " + getDealerHand());
        return getHandValue(this.getOpponent().getHand());
    }

    // Running game logic
    @Override
    public void runGame(BlackJackPlayer player) {
        initializeGame(player);
        if (!initialHandBlackjackCheck()) {
            boolean stay = runPlayerTurn();
            if (!stay) {
                ConsoleServices.print("Busted! Your hand value has exceeded 21, and now you lose.");
                StatTracker.finishGame(this, false);
                return;
            } else {
                runGameAfterPlayerTurn();
            }
        }
    }

    public Integer runGameAfterPlayerTurn() {
        while (!isPlayerBust(this.getOpponent()) && getHandValue(this.getOpponent().getHand()) < 17) {
            dealerTurn();
        }
        if (isPlayerBust(this.getOpponent()) || getHandValue(this.getCurrentPlayer().getHand()) > getHandValue(this.getOpponent().getHand())) {
            beatDealer();
            return 1;
        } else if (!isPlayerBust(this.getOpponent()) && getHandValue(this.getCurrentPlayer().getHand()) == getHandValue(this.getOpponent().getHand())) {
            push();
            return 0;
        } else {
            loseToDealer();
            return -1;
        }
    }

    // Game outcome functions
    public void beatDealer() {
        ConsoleServices.print("You beat the dealer! You win!!");
        StatTracker.finishGame(this, true);
        StatTracker.updateChipWinnings(this.dollarValueOfAllChipsBet);
        for (Chip c : this.chipsBet) {
            this.getCurrentPlayer().getWallet().addChip((Chip) c.clone());
            this.getCurrentPlayer().getWallet().addChip((Chip) c.clone());
        }
    }

    public void push() {
        ConsoleServices.print("Push! You tied with the dealer. Your bet is being returned in full.");
        for (Chip c : this.chipsBet) {
            this.getCurrentPlayer().getWallet().addChip((Chip) c.clone());
        }
    }

    public void loseToDealer() {
        ConsoleServices.print("You lost to the dealer!");
        StatTracker.finishGame(this, false);
    }


    // Hand evaluation stuff
    public Integer getBetterScore(int scoreA, int scoreB) {
        if (scoreA > scoreB && scoreA < 22) {
            return scoreA;
        } else if (scoreB > scoreA && scoreB < 22) {
            return scoreB;
        }
        return (scoreA < 22) ? scoreA : scoreB;
    }

    public Integer finalHandValueEval(int bestScore, int highAceScore, int lowAceScore) {
        if (getBetterScore(bestScore, lowAceScore) == bestScore && getBetterScore(bestScore, highAceScore) == bestScore && bestScore < 22) {
            return bestScore;
        }

        if (highAceScore >= lowAceScore && highAceScore < 22) {
            return highAceScore;
        }

        return lowAceScore;
    }

    public Integer getHandValue(ArrayList<PlayingCard> hand) {
        int highAceScore = 0;
        int lowAceScore = 0;
        int bestScore = 0;
        int aces = 0;
        for (PlayingCard c : hand) {
            if (c.getValueAsInt() > 1 && c.getValueAsInt() < 11) {
                highAceScore += c.getValueAsInt();
                lowAceScore += c.getValueAsInt();
                bestScore += c.getValueAsInt();
            } else if (c.getValueAsInt() > 1) {
                highAceScore += 10;
                lowAceScore += 10;
                bestScore += 10;
            } else {
                aces++;
            }
        }

        for (int i = 0; i < aces; i++) {
            highAceScore += 11;
            lowAceScore++;
            if (bestScore + 11 > 21) {
                bestScore++;
            } else {
                bestScore += 11;
            }
        }
        return finalHandValueEval(bestScore, highAceScore, lowAceScore);
    }

    public Boolean isPlayerBust(CardPlayer player) {
        int playerScore = getHandValue(player.getHand());
        if (playerScore > 21) {
            return true;
        }
        return false;
    }

    public Boolean initialHandBlackjackCheck() {
        if (getHandValue(this.getCurrentPlayer().getHand()) == 21) {
            if (getHandValue(this.getOpponent().getHand()) != 21) {
                ConsoleServices.print("BlackJack! Your initial hand had a value of 21! You win!");
                StatTracker.finishGame(this, true);
                StatTracker.updateChipWinnings(this.dollarValueOfAllChipsBet);
                for (Chip c : this.chipsBet) {
                    this.getCurrentPlayer().getWallet().addChip((Chip) c.clone());
                    this.getCurrentPlayer().getWallet().addChip((Chip) c.clone());
                }

            } else {
                ConsoleServices.print("Push! Both you and the dealer were dealt a blackjack hand. Your bet is being returned in full.");
                for (Chip c : this.chipsBet) {
                    this.getCurrentPlayer().getWallet().addChip((Chip) c.clone());
                }
            }
            return true;
        }
        return false;
    }

    // Betting & Printing
    public Map<String, Integer> processUserBetInput(ArrayList<String> chipColors, ArrayList<Integer> chipAmts) {
        Map<String, Integer> chipsToBet = new HashMap<>();
        int amtIterator = 0;
        for (String s : chipColors) {
            if (chipAmts.size() > amtIterator) {
                chipsToBet.put(s, chipAmts.get(amtIterator));
                amtIterator++;
            } else { break; }
        }
        return chipsToBet;
    }

    public Integer setupBet() {
        ArrayList<String> chipColors = new ArrayList<>();
        ArrayList<Integer> chipAmts = new ArrayList<>();
        if (this.getCurrentPlayer().getWallet().getNumberOfAllChips() > 0) {
            String[] spliceArgs = ConsoleServices.getStringInput("Please place a bet. (Enter a chip color and an amount): ").split(" ");
            for (String arg : spliceArgs) {
                try {
                    chipAmts.add(Integer.parseInt(arg));
                } catch (NumberFormatException ex) {
                    if (namesOfChipColors.contains(arg.toLowerCase())) {
                        chipColors.add(arg);
                    }
                }
            }
        }
        return handleUserBets(chipColors, chipAmts);
    }

    public Integer handleUserBets(ArrayList<String> chipColors, ArrayList<Integer> chipAmts) {
        Wallet playerWallet = this.getCurrentPlayer().getWallet();
        Integer totalAmtBet = 0;
        for (Map.Entry<String, Integer> bettingEntry : processUserBetInput(chipColors, chipAmts).entrySet()) {
            String chipColor = bettingEntry.getKey();
            Integer amt = bettingEntry.getValue();
            if (amt > playerWallet.getNumOfChips(chipColor)) {
                amt = playerWallet.getNumOfChips(chipColor);
            }
            playerWallet.subChip(chipColor, amt);
            Chip betChip;
            totalAmtBet = amt;
            for (int i = 0; i < amt; i++) {
                betChip = new Chip(Chip.getEnumFromString(chipColor));
                chipsBet.add(betChip);
            }
        }
        return totalAmtBet;
    }

    public Integer getDollarValueOfBet() {
        int dollars = 0;
        for (Chip c : this.chipsBet) {
            dollars += c.getVal().getValue();
        }
        return dollars;
    }

    public Boolean printBet() {
        if (this.chipsBet.size() < 1) {
            return false;
        }
        int green = 0;
        int black = 0;
        int blue = 0;
        int white = 0;
        for (Chip c : this.chipsBet) {
            switch (c.getVal()) {
                case GREEN: green++;  break;
                case WHITE: white++; break;
                case BLACK: black++; break;
                case BLUE: blue++; break;
            }
        }
        String chips = "\nChips Bet:\nBlack: " + black + "\nGreen: " + green + "\nBlue: " + blue + "\nWhite: " + white + "\n\nTotal ($$): " + this.dollarValueOfAllChipsBet + "\nTotal (Chips): " + this.amountOfChipsBet + "\n\n";
        ConsoleServices.print(chips);
        return true;
    }

    public String getDealerHand() {
        String toRet = "";
        Collections.sort(this.getOpponent().getHand());
        for (int i = 0; i < this.getOpponent().getHand().size() - 1; i++) {
            toRet += this.getOpponent().getHand().get(i).getValue() + ", ";
        }
        toRet = toRet.substring(0, toRet.length() - 2);
        return toRet;
    }

    public ArrayList<Chip> getChipsBet() {
        return chipsBet;
    }
}
