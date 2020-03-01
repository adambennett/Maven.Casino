package io.zipcoder.casino.games.specific;

import io.zipcoder.casino.games.CardGame;
import io.zipcoder.casino.games.Game;
import io.zipcoder.casino.models.Chip;
import io.zipcoder.casino.models.Deck;
import io.zipcoder.casino.models.Wallet;
import io.zipcoder.casino.players.BlackJackPlayer;
import io.zipcoder.casino.players.Dealer;
import io.zipcoder.casino.utilities.io.ConsoleServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewBlackjack extends Game<BlackJackPlayer, Dealer> implements CardGame {

    private ArrayList<Chip> chipsBet;
    private Deck gameDeck;
    private boolean playerTurn;
    private ArrayList<String> namesOfChipColors;
    private Integer amountOfChipsBet;
    private Integer dollarValueOfAllChipsBet;

    public NewBlackjack() {
        chipsBet = new ArrayList<>();
        namesOfChipColors = new ArrayList<>();
        namesOfChipColors.add("black");
        namesOfChipColors.add("green");
        namesOfChipColors.add("white");
        namesOfChipColors.add("blue");
    }

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
        String[] spliceArgs = ConsoleServices.getStringInput("Please place a bet. (Enter a chip color and an amount): ").split(" ");
        for (String arg : spliceArgs) {
            try {
                chipAmts.add(Integer.parseInt(arg));
            } catch (NumberFormatException ex) { if (namesOfChipColors.contains(arg.toLowerCase())) { chipColors.add(arg); }}
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
        String chips = "\nChips Bet:\nBlack: " + black + "\nGreen: " + green + "\nBlue: " + blue + "\nWhite: " + white + "\n\nTotal ($$): " + this.dollarValueOfAllChipsBet + "\nTotal (Chips): " + this.amountOfChipsBet;
        ConsoleServices.print(chips);
        return true;
    }

    public Boolean setupPlayers(BlackJackPlayer player) {
        if (player == null) { return false; }
        this.setCurrentPlayer(player);
        this.setOpponent(new Dealer());
        return true;
    }

    public Boolean canDoubleDown() {
        return this.chipsBet.size() > 0;
    }

    public void doubleDown() {
        ArrayList<Chip> newChips = new ArrayList<>();
        if (canDoubleDown()) {
            newChips.addAll(this.chipsBet);
        }
        this.chipsBet.addAll(newChips);
    }

    public Boolean initializeGame(BlackJackPlayer player) {
        if (!setupPlayers(player)) {
            return false;
        }
        this.amountOfChipsBet = setupBet();
        this.dollarValueOfAllChipsBet = getDollarValueOfBet();
        printBet();
        return true;
    }

    @Override
    public void runGame(BlackJackPlayer player) {
        initializeGame(player);
    }
}
