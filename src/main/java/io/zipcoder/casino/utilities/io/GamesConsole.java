package io.zipcoder.casino.utilities.io;

import io.zipcoder.casino.App;

import java.util.ArrayList;

public class GamesConsole extends AbstractConsole {

    @Override
    protected void initializeCommands() {
        consoleCommands.put("loopy", Command.LOOPY_DICE);
        consoleCommands.put("gofish", Command.GOFISH);
        consoleCommands.put("blackjack", Command.BLACKJACK);
        consoleCommands.put("craps", Command.CRAPS);
        consoleCommands.put("help", Command.HELP);
        consoleCommands.put("1", Command.BLACKJACK);
        consoleCommands.put("2", Command.GOFISH);
        consoleCommands.put("3", Command.LOOPY_DICE);
        consoleCommands.put("4", Command.CRAPS);
        consoleCommands.put("0", Command.MAIN_MENU);
    }

    @Override
    public void processCommand(Command cmd, ArrayList<String> args) {
        if (App.getCasino().isGame(cmd)) {
            App.getCasino().startGame(cmd);
        } else {
            switch (cmd) {
                case MAIN_MENU:
                    MainConsole main = new MainConsole();
                    main.printPrompt(PromptMessage.STANDARD, true);
                    return;
                case HELP:
                    printHelpCommand(this);
                    if (App.isLoggedIn()) {
                        printPrompt(PromptMessage.GAMES_MENU, true);
                    } else {
                        printPrompt(PromptMessage.LOGIN, true);
                    }
                    return;
                default:
                    printPrompt(PromptMessage.GAMES_MENU, true);
                    return;
            }
        }
    }
}

