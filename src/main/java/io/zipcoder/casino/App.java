package io.zipcoder.casino;

import io.zipcoder.casino.games.Game;
import io.zipcoder.casino.players.Player;
import io.zipcoder.casino.models.Casino;
import io.zipcoder.casino.utilities.io.AbstractConsole;
import io.zipcoder.casino.utilities.io.LoginConsole;
import io.zipcoder.casino.utilities.persistence.SaveLoadServices;
import io.zipcoder.casino.utilities.persistence.StatTracker;

public class App {

    private static Casino casino;
    private static boolean isLoggedIn;

    public static void main(String[] args) {
        App application = new App();
        application.initialize();
    }

    public void initialize() {
        runCasino();
        StatTracker st = new StatTracker();
        SaveLoadServices.loadJSONData(SaveLoadServices.SAVE_FILE_NAME);
        LoginConsole login = new LoginConsole();
        login.printPrompt(AbstractConsole.PromptMessage.WELCOME, true);
    }

    public void runCasino() {
        casino = new Casino();
    }

    public static Casino getCasino() {
        return casino;
    }

    public static void logPlayerIn(Player p) {
        casino.setCurrentPlayer(p);
        setLoginStatus();
    }

    public static Player getCurrentPlayer() {
        return casino.getCurrentPlayer();
    }

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static void setLoginStatus() {
        if (casino.getCurrentPlayer() != null) {
            isLoggedIn = true;
        } else {
            isLoggedIn = false;
        }
    }
}
