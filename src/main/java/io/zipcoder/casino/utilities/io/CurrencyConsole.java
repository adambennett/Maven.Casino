package io.zipcoder.casino.utilities.io;

import io.zipcoder.casino.App;

import java.util.ArrayList;

public class CurrencyConsole extends AbstractConsole {

    @Override
    protected void initializeCommands() {
        consoleCommands.put("help", Command.HELP);
        consoleCommands.put("0", Command.MAIN_MENU);
        consoleCommands.put("1", Command.BUY_CHIPS);
        consoleCommands.put("2", Command.CASHOUT);
        consoleCommands.put("3", Command.DEPOSIT);
        consoleCommands.put("4", Command.VIEW_CHIPS);
    }


    @Override
    public void processCommand(Command cmd, ArrayList<String> args) {
        switch (cmd) {
            case VIEW_CHIPS:
                ViewChipsConsole viewChipsConsole = new ViewChipsConsole();
                viewChipsConsole.printPrompt(PromptMessage.VIEW_CHIPS_MENU, true);
                return;
            case BUY_CHIPS:
                BuyChipsConsole buyChipsConsole = new BuyChipsConsole();
                buyChipsConsole.printPrompt(PromptMessage.BUY_CHIPS_MENU, true);
                return;
            case CASHOUT:
                if (App.isLoggedIn()) {
                    App.getCurrentPlayer().getWallet().subDollar(App.getCurrentPlayer().getWallet().getDollars());
                }
                printPrompt(PromptMessage.CURRENCY_MENU, true);
                return;
            case DEPOSIT:
                String input = ConsoleServices.getStringInput("How much would you like to deposit?");
                try {
                    Integer amt = Integer.parseInt(input);
                    App.getCurrentPlayer().getWallet().addDollar(amt);
                } catch (NumberFormatException ex) {
                    ConsoleServices.print("Not a number, no deposit occured.");
                }
                printPrompt(PromptMessage.CURRENCY_MENU, true);
                return;
            case HELP:
                printHelpCommand(this);
                if (App.isLoggedIn()) {
                    printPrompt(PromptMessage.CURRENCY_MENU, true);
                } else {
                    printPrompt(PromptMessage.LOGIN, true);
                }
                return;
            case MAIN_MENU:
                MainConsole console = new MainConsole();
                console.printPrompt(PromptMessage.STANDARD, true);
                return;
            default:
                printPrompt(PromptMessage.CURRENCY_MENU, true);
                return;
        }
    }

    public void processMenuChanges(Command cmd, ArrayList<String> args) {
        switch (cmd) {
            case CURRENCY:
                new CurrencyConsole().printPrompt(PromptMessage.CURRENCY_MENU, true);
                return;
            case MAIN_MENU:
                new MainConsole().printPrompt(PromptMessage.STANDARD, true);
                return;
            default:
                if (this instanceof BuyChipsConsole) { new BuyChipsConsole().printPrompt(PromptMessage.BUY_CHIPS_MENU, true); }
                if (this instanceof ViewChipsConsole) { new ViewChipsConsole().printPrompt(PromptMessage.VIEW_CHIPS_MENU, true); }
                return;
        }
    }
}
