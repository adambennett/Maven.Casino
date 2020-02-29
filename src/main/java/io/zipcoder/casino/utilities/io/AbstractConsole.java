package io.zipcoder.casino.utilities.io;

import io.zipcoder.casino.App;
import io.zipcoder.casino.utilities.MenuStrings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConsole {

    protected Map<String, Command> consoleCommands;

    public enum Command {
        GAMES,
        CURRENCY,
        STATS,
        HELP,
        LOGOUT,
        LEADERBOARD,
        INDIVIDUAL_STATS,
        BLACKJACK,
        GOFISH,
        LOOPY_DICE,
        CRAPS,
        REGISTER,
        LOGIN,
        MAIN_MENU,
        DEPOSIT,
        BUY_CHIPS,
        CASHOUT,
        VIEW_CHIPS
    }

    public enum PromptMessage {
        STANDARD,
        GAMES_MENU,
        CURRENCY_MENU,
        STATS_MENU,
        LOGIN,
        BAD_LOGIN,
        BUY_CHIPS_MENU,
        VIEW_CHIPS_MENU,
        LEADERBOARD,
        IND_STATS,
        GOODBYE,
        WELCOME
    }

    public AbstractConsole() {
        consoleCommands = new HashMap<>();
        initializeCommands();
    }

    protected void printHelpCommand(AbstractConsole consoleType) {
        if (consoleType instanceof LoginConsole) {
           ConsoleServices.print(MenuStrings.loginMenuCommands);
        } else if (consoleType instanceof MainConsole) {
            ConsoleServices.print(MenuStrings.mainMenuCommands);
        }
        else if (consoleType instanceof CurrencyConsole) {
            ConsoleServices.print(MenuStrings.currencyMenuCommands);
        } else if (consoleType instanceof GamesConsole) {
            ConsoleServices.print(MenuStrings.gamesMenuCommands);
        } else if (consoleType instanceof StatsConsole) {
            ConsoleServices.print(MenuStrings.statsMenuCommands);
        }
    }

    private ArrayList<String> generateUserInput() {
        String input = ConsoleServices.getStringInput("");
        String[] splice = input.split("\\s+");
        ArrayList<String> argus = new ArrayList<>();
        for (String s : splice) {
            argus.add(s);
        }
        return argus;
    }

    public boolean commandExists(String cmd) {
        if (this.consoleCommands != null && this.consoleCommands.containsKey(cmd.toLowerCase())) {
            return true;
        }
        return false;
    }

    protected void getUserInput() {
        String input = ConsoleServices.getStringInput("");
        String[] splited = input.split("\\s+");
        ArrayList<String> argus = new ArrayList<>();
        for (String s : splited) {
            argus.add(s);
        }
        while (argus.size() < 1) {
            argus = generateUserInput();
        }
        findAndProcessCommand(argus);
    }

    public void findAndProcessCommand(ArrayList<String> args) {
        String command = args.remove(0).toLowerCase();
        String hiddenCmd = "";
        boolean allowOtherConsoles = false;
        AbstractConsole consoleToExecuteFrom = null;

        if (isValidCommandInAnyConsole(command)) {
            consoleToExecuteFrom = whichConsoleHasThisCommand(command);
            allowOtherConsoles = true;
        }
        if (commandExists(command)) {
            this.processCommand(this.consoleCommands.get(command), args);
        }
        else if (allowOtherConsoles && App.isLoggedIn()) {
            consoleToExecuteFrom.processCommand(consoleToExecuteFrom.consoleCommands.get(command), args);
        }
        else if (command.equals("best") && hiddenCmd.toLowerCase().equals("programmer")) {
            ConsoleServices.print("Nobles");
            printPrompt(PromptMessage.STANDARD, true);
        }
        else {
            runOnInvalidCommand(this);
        }
    }

    public void printPrompt(PromptMessage message, boolean promptForInput) {
        MenuStrings.loadStrings();
        String promptString = MenuStrings.getStringFromPromptType(message);
        ConsoleServices.print(promptString);
        if (promptForInput) {
            getUserInput();
        }
    }

    private static boolean isValidCommandInAnyConsole(String cmd) {
        MainConsole console = new MainConsole();
        CurrencyConsole currencyConsole = new CurrencyConsole();
        GamesConsole gamesConsole = new GamesConsole();
        LoginConsole loginConsole = new LoginConsole();
        StatsConsole statsConsole = new StatsConsole();
        try {
            Integer.parseInt(cmd);
        } catch (NumberFormatException ex) {
            if (console.commandExists(cmd)) { return true; }
            else if (currencyConsole.commandExists(cmd)) { return true; }
            else if (gamesConsole.commandExists(cmd)) { return true; }
            else if (loginConsole.commandExists(cmd)) { return true; }
            else if (statsConsole.commandExists(cmd)) { return true; }
        }
        return false;
    }

    private static AbstractConsole whichConsoleHasThisCommand(String cmd) {
        MainConsole console = new MainConsole();
        CurrencyConsole currencyConsole = new CurrencyConsole();
        GamesConsole gamesConsole = new GamesConsole();
        LoginConsole loginConsole = new LoginConsole();
        StatsConsole statsConsole = new StatsConsole();
        try {
            Integer.parseInt(cmd);
        } catch (NumberFormatException ex) {
            if (console.commandExists(cmd)) { return console; }
            else if (currencyConsole.commandExists(cmd)) { return currencyConsole; }
            else if (gamesConsole.commandExists(cmd)) { return gamesConsole; }
            else if (loginConsole.commandExists(cmd)) { return loginConsole; }
            else if (statsConsole.commandExists(cmd)) { return statsConsole; }
        }
        return null;
    }

    protected abstract void initializeCommands();

    private void runOnInvalidCommand(AbstractConsole currentConsole) {
        ConsoleServices.print("Bad command! Please enter a valid command, or enter 'Help'.");

        if (App.isLoggedIn()) {
            if (currentConsole instanceof MainConsole) {
                MainConsole console = (MainConsole) this;
                console.printPrompt(PromptMessage.STANDARD, true);
            }
            else if (currentConsole instanceof GamesConsole) {
                GamesConsole games = (GamesConsole) this;
                games.printPrompt(PromptMessage.GAMES_MENU, true);
            }
            else if (currentConsole instanceof StatsConsole) {
                StatsConsole stat = (StatsConsole) this;
                stat.printPrompt(PromptMessage.STATS_MENU, true);
            }
            else if (currentConsole instanceof CurrencyConsole) {
                CurrencyConsole curr = (CurrencyConsole) this;
                curr.printPrompt(PromptMessage.CURRENCY_MENU, true);
            }
            else {
                this.printPrompt(PromptMessage.STANDARD, true);
            }
        }

        else {
            printPrompt(PromptMessage.LOGIN, true);
        }
        return;
    }


    public abstract void processCommand(Command cmd, ArrayList<String> args);

}
