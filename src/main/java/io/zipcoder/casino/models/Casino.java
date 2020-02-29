package io.zipcoder.casino.models;


import io.zipcoder.casino.games.Game;
import io.zipcoder.casino.games.specific.BlackJack;
import io.zipcoder.casino.games.specific.Craps;
import io.zipcoder.casino.games.specific.GoFish;
import io.zipcoder.casino.games.specific.LoopyDice;
import io.zipcoder.casino.players.*;
import io.zipcoder.casino.utilities.io.AbstractConsole;
import io.zipcoder.casino.utilities.io.GamesConsole;
import io.zipcoder.casino.utilities.io.MainConsole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Casino {

    private Map<AbstractConsole.Command, Game> games;
    private Player currentPlayer;

    public void startGame(AbstractConsole.Command cmd) {
        startGame(cmd, new ArrayList<>());
    }

    public void startGame(AbstractConsole.Command cmd, ArrayList<String> args) {
        switch (cmd) {
            case BLACKJACK:
                BlackJack blackJack = new BlackJack();
                updateCurrentPlayer(blackJack);
                blackJack.runGame((BlackJackPlayer) currentPlayer);
                break;
            case GOFISH:
                GoFish gofish = new GoFish();
                updateCurrentPlayer(gofish);
                gofish.runGame((GoFishPlayer) currentPlayer);
                break;
            case LOOPY_DICE:
                LoopyDice loop = new LoopyDice();
                updateCurrentPlayer(loop);
                loop.runGame((LoopyDicePlayer) currentPlayer);
                break;
            case CRAPS:
                Craps craps = new Craps();
                updateCurrentPlayer(craps);
                craps.runGame((DicePlayer) currentPlayer);
                break;
        }
        GamesConsole games = new GamesConsole();
        games.printPrompt(AbstractConsole.PromptMessage.GAMES_MENU, true);
    }


    public Casino() {
        currentPlayer = new Player("temp");
        games = new HashMap<>();
        games.put(AbstractConsole.Command.BLACKJACK, new BlackJack());
        games.put(AbstractConsole.Command.GOFISH, new GoFish());
        games.put(AbstractConsole.Command.LOOPY_DICE, new LoopyDice());
        games.put(AbstractConsole.Command.CRAPS, new Craps());
    }

    public Map<AbstractConsole.Command, Game> getGames() {
        return games;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) { currentPlayer = player;}

    public Boolean isGame(AbstractConsole.Command gameCmd) {
        return this.games.containsKey(gameCmd);
    }

    public void updateCurrentPlayer(Game currentGame) {
        if (currentGame instanceof BlackJack) {
           this.currentPlayer = new BlackJackPlayer(this.currentPlayer);
        } else if (currentGame instanceof Craps) {
            this.currentPlayer = new DicePlayer(this.currentPlayer);
        } else if (currentGame instanceof GoFish) {
            this.currentPlayer = new GoFishPlayer(this.currentPlayer);
        } else if (currentGame instanceof LoopyDice) {
            this.currentPlayer = new LoopyDicePlayer(this.currentPlayer);
        }
    }

}
