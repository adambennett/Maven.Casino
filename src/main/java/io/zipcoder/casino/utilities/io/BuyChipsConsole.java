package io.zipcoder.casino.utilities.io;

import io.zipcoder.casino.App;
import io.zipcoder.casino.models.Bank;
import io.zipcoder.casino.models.Chip;

import java.io.Console;
import java.util.ArrayList;

public class BuyChipsConsole extends CurrencyConsole {

    @Override
    protected void initializeCommands() {
        consoleCommands.put("6", Command.MAIN_MENU);
        consoleCommands.put("5", Command.CURRENCY);
        consoleCommands.put("1", Command.BLACK);
        consoleCommands.put("2", Command.GREEN);
        consoleCommands.put("3", Command.BLUE);
        consoleCommands.put("4", Command.WHITE);
    }

    public Boolean buyChip(Command cmd, ArrayList<String> args) {
        Chip.ChipValue chosen;
        switch (cmd) {
            case GREEN: chosen = Chip.ChipValue.GREEN; break;
            case BLACK: chosen = Chip.ChipValue.BLACK; break;
            case BLUE: chosen = Chip.ChipValue.BLUE; break;
            case WHITE: chosen = Chip.ChipValue.WHITE; break;
            default: return false;
        }
        if (App.isLoggedIn()) {
            Integer amt = 0;
            if (args.size() > 0) { try { amt = Integer.parseInt(args.get(0)); } catch (NumberFormatException ex) {}}
            if (amt == 0) {
                amt = App.getCurrentPlayer().getWallet().getDollars() / chosen.getValue();
            }
            boolean boughtAny = false;
            for (Chip boughtChip : Bank.buyChip(amt, chosen)) {
                App.getCurrentPlayer().getWallet().addChip(boughtChip);
                App.getCurrentPlayer().getWallet().subDollar(boughtChip.getDollarVal());
                boughtAny = true;
            }
            if (boughtAny) { ConsoleServices.print("Bought " + amt + " " + chosen.toString() + " chips."); }
            return true;
        }
        return false;
    }

    @Override
    public void processCommand(Command cmd, ArrayList<String> args) {
        if (buyChip(cmd, args)) {
            printPrompt(PromptMessage.BUY_CHIPS_MENU, true);
            return;
        }
        processMenuChanges(cmd, args);
    }
}
