package io.zipcoder.casino.utilities.io;

import java.util.ArrayList;

public class ViewChipsConsole extends CurrencyConsole {
    @Override
    protected void initializeCommands() {
        consoleCommands.put("0", Command.CURRENCY);
        consoleCommands.put("1", Command.MAIN_MENU);
    }

    @Override
    public void processCommand(Command cmd, ArrayList<String> args) {
        processMenuChanges(cmd, args);
    }
}
