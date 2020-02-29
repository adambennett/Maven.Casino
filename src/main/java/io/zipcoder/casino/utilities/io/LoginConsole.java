package io.zipcoder.casino.utilities.io;

import io.zipcoder.casino.App;
import io.zipcoder.casino.models.Bank;
import io.zipcoder.casino.models.Chip;
import io.zipcoder.casino.models.Wallet;
import io.zipcoder.casino.players.Player;
import io.zipcoder.casino.utilities.persistence.Database;
import io.zipcoder.casino.utilities.persistence.SaveLoadServices;

import java.util.ArrayList;

public class LoginConsole extends AbstractConsole {

    @Override
    protected void initializeCommands() {
        consoleCommands.put("help", Command.HELP);
        consoleCommands.put("register", Command.REGISTER);
        consoleCommands.put("logout", Command.LOGOUT);
        consoleCommands.put("login", Command.LOGIN);
    }

    @Override
    public void processCommand(Command cmd, ArrayList<String> args) {
        switch (cmd) {
            case HELP:
                printHelpCommand(this);
                if (App.isLoggedIn()) {
                    printPrompt(PromptMessage.STANDARD, true);
                } else {
                    printPrompt(PromptMessage.LOGIN, true);
                }
                return;
            case REGISTER:
                if (attemptRegister(args)) {
                    MainConsole console = new MainConsole();
                    console.printPrompt(PromptMessage.STANDARD, true);
                } else {
                    printPrompt(PromptMessage.LOGIN, true);
                }
                return;
            case LOGOUT:
                printPrompt(PromptMessage.GOODBYE, false);
                if (App.isLoggedIn()) {
                    SaveLoadServices.saveJSON(SaveLoadServices.SAVE_FILE_NAME);
                }
                return;
            case LOGIN:
                if (attemptLogin(args)) {
                    MainConsole console = new MainConsole();
                    console.printPrompt(PromptMessage.STANDARD, true);
                } else {
                    printPrompt(PromptMessage.BAD_LOGIN, true);
                }
                return;
        }
    }

    public Boolean attemptLogin(ArrayList<String> args) {
        String user = getUserFromInput(args);
        Boolean loggedIn = Database.canLogin(user, getPasswordFromInput(args));
        if (loggedIn) {
           App.logPlayerIn(Database.getPlayer(user));
        }
        return loggedIn;
    }

    public Boolean attemptRegister(ArrayList<String> args) {
        if (args.size() > 1) {
            String user = args.get(0);
            String pass = args.get(1);
            if (!Database.isUser(user)) {
                Player newUser = new Player(user, pass, new Wallet());
                Database.addUser(newUser);
                App.logPlayerIn(Database.getPlayer(user));
                return true;
            }
        }
        return false;
    }

    private String getUserFromInput(ArrayList<String> args) {
        for (String arg : args) {
            if (Database.isUser(arg)) {
                return arg;
            }
        }
        return "";
    }

    private String getPasswordFromInput(ArrayList<String> args) {
        for (String arg : args) {
            if (!Database.isUser(arg)) {
                return arg;
            }
        }
        return "";
    }
}

