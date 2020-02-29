package io.zipcoder.casino.utilities;

import io.zipcoder.casino.App;
import io.zipcoder.casino.models.Chip;
import io.zipcoder.casino.models.StatsModel;
import io.zipcoder.casino.models.Wallet;
import io.zipcoder.casino.players.Player;
import io.zipcoder.casino.utilities.io.AbstractConsole;
import io.zipcoder.casino.utilities.persistence.Database;
import io.zipcoder.casino.utilities.persistence.StatTracker;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MenuStrings {

    public static String login;
    public static String onceLogin;
    public static String standard;
    public static String commands;
    public static String goodbye;
    public static String register;
    public static String badLogin;
    public static String gamesMenu;
    public static String currencyMenu;
    public static String statsMenu;
    public static String buyChipsMenu;
    public static String viewChipsMenu;
    public static String leaderBoard;
    public static String leaderboardPlayers;
    public static String individualStats;
    public static String loginMenuCommands;
    public static String currencyMenuCommands;
    public static String gamesMenuCommands;
    public static String statsMenuCommands;
    public static String mainMenuCommands;
    public static String asciiDice;
    public static String asciiCards;

    public static int dollars = 0;
    public static int blackChips = 0;
    public static int blueChips = 0;
    public static int greenChips = 0;
    public static int whiteChips = 0;
    public static StatsModel playerStats;

    public static void updateValues() {
        if (App.getCurrentPlayer() != null && App.isLoggedIn()) {
            Wallet playerWallet = App.getCurrentPlayer().getWallet();
            dollars = playerWallet.getDollars();
            blackChips = 0;
            blueChips = 0;
            whiteChips = 0;
            greenChips = 0;
            for (Map.Entry<Chip, Integer> entry : playerWallet.getChips().entrySet()) {
                Chip.ChipValue type = entry.getKey().getVal();
                switch (type) {
                    case BLACK: blackChips += entry.getValue(); break;
                    case BLUE: blueChips += entry.getValue(); break;
                    case GREEN: greenChips += entry.getValue(); break;
                    case WHITE: whiteChips += entry.getValue(); break;
                }
            }
            Database.processStats(App.getCurrentPlayer());
            playerStats = App.getCurrentPlayer().getStats();
        }
        leaderboardPlayers = "";
        ArrayList<Player> sortedList = Database.getAllPlayers();
        Collections.sort(sortedList);
        String lengthCheck = "*************************************************************************";
        int length = lengthCheck.length();
        int rank = 1;
        for (Player p : sortedList) {
            Database.processStats(p);
            String nextString = "*** " + rank + ": " + p.getName() + " -- " + p.getStats().getOverallScore();
            while (nextString.length() < length - 4) {
                nextString += " ";
            }
            leaderboardPlayers += nextString + " ***\n";
            rank++;
        }

        if (playerStats == null) {
            playerStats = new StatsModel();
        }

    }

    public static void loadStrings() {
        updateValues();
        String loopyText =
                "        ██╗     ███████╗████████╗███████╗          \n" +
                "        ██║     ██╔════╝╚══██╔══╝██╔════╝          \n" +
                "        ██║     █████╗     ██║   ███████╗          \n" +
                "        ██║     ██╔══╝     ██║   ╚════██║          \n" +
                "        ███████╗███████╗   ██║   ███████║          \n" +
                "        ╚══════╝╚══════╝   ╚═╝   ╚══════╝          \n" +
                "             ██████╗ ███████╗████████╗                 \n" +
                "            ██╔════╝ ██╔════╝╚══██╔══╝                 \n" +
                "            ██║  ███╗█████╗     ██║                    \n" +
                "            ██║   ██║██╔══╝     ██║                    \n" +
                "            ╚██████╔╝███████╗   ██║                    \n" +
                "             ╚═════╝ ╚══════╝   ╚═╝                    \n" +
                "    ██╗      ██████╗  ██████╗ ██████╗ ██╗   ██╗\n" +
                "    ██║     ██╔═══██╗██╔═══██╗██╔══██╗╚██╗ ██╔╝\n" +
                "    ██║     ██║   ██║██║   ██║██████╔╝ ╚████╔╝ \n" +
                "    ██║     ██║   ██║██║   ██║██╔═══╝   ╚██╔╝  \n" +
                "    ███████╗╚██████╔╝╚██████╔╝██║        ██║   \n" +
                "    ╚══════╝ ╚═════╝  ╚═════╝ ╚═╝        ╚═╝  \n";

        asciiCards = "";

        asciiDice = "              _______.\n" +
                "   ______    | .   . |\\\n" +
                "  /     /\\   |   .   |.\\\n" +
                " /  '  /  \\  | .   . |.'|\n" +
                "/_____/. . \\ |_______|.'|\n" +
                "\\ . . \\    /  \\ ' .   \\'|\n" +
                " \\ . . \\  /    \\____'__\\|\n" +
                "  \\_____\\/\n";

        onceLogin = loopyText +
                "**************************************************\n" +
                "***                  ZipCasino                 ***\n" +
                "***                                            ***\n" +
                "***                Keep it Loopy               ***\n" +
                "***                                            ***\n" +
                "***        Enter [USERNAME] [PASSWORD]         ***\n" +
                "***               or 'Register'                ***\n" +
                "**************************************************\n";

        login =
                "**************************************************\n" +
                "***                  ZipCasino                 ***\n" +
                "***                                            ***\n" +
                "***                Keep it Loopy               ***\n" +
                "***                                            ***\n" +
                "***        Enter [USERNAME] [PASSWORD]         ***\n" +
                "***               or 'Register'                ***\n" +
                "**************************************************\n";

        standard = "" +
                "*************************************************************************\n" +
                "***                             ZipCasino                             ***\n" +
                "*************************************************************************\n" +
                "***    Games    |  Play Games                                         ***\n" +
                "***    Currency |  Exchange money and chips, cash-out                 ***\n" +
                "***    Stats    |  View leaderboards and individual player stats      ***\n" +
                "***    Help     |  Get more information about how to use the app      ***\n" +
                "***    Logout   |  Save and quit the ZipCasino app                    ***\n" +
                "*************************************************************************\n" +
                "***                          Enter a command                          ***\n" +
                "*************************************************************************\n" ;

        register = "" +
                "**************************************************\n" +
                "***                Registration                ***\n" +
                "***                                            ***\n" +
                "***      Enter a new [USERNAME] [PASSWORD]     ***\n" +
                "**************************************************\n";

        badLogin = "" +
                "**************************************************\n" +
                "***                Bad Login!                  ***\n" +
                "***                                            ***\n" +
                "***         Enter [USERNAME] [PASSWORD]        ***\n" +
                "**************************************************\n";

        gamesMenu = "" +
                "*************************************************************************\n" +
                "***                      ZipCasino - Games Menu                       ***\n" +
                "*************************************************************************\n" +
                "***    1 |  Play BlackJack                                            ***\n" +
                "***    2 |  Play Go Fish                                              ***\n" +
                "***    3 |  Play Loopy Dice                                           ***\n" +
                "***    4 |  Play Craps                                                ***\n" +
                "***    0 |  Return to the main menu                                   ***\n" +
                "*************************************************************************\n" +
                "***                          Enter a command                          ***\n" +
                "*************************************************************************\n" ;
        String dollarText =  "*** $$: " + dollars;
        int lengthTo = "**********************************************************************".length();
        while (dollarText.length() < lengthTo) { dollarText += " "; }
        dollarText += "***\n";
        currencyMenu = "" +
                "*************************************************************************\n" +
                "***                     ZipCasino - Currency Menu                     ***\n" +
                "*************************************************************************\n" +
                dollarText +
                "*************************************************************************\n" +
                "***    1 |  Buy Chips                                                 ***\n" +
                "***    2 |  Cashout                                                   ***\n" +
                "***    3 |  Deposit                                                   ***\n" +
                "***    4 |  View Chips                                                ***\n" +
                "***    0 |  Return to the main menu                                   ***\n" +
                "*************************************************************************\n" +
                "***                          Enter a command                          ***\n" +
                "*************************************************************************\n" ;

        dollarText =  "*** $$: " + dollars;
        lengthTo = "*****************************************************************************************************************************".length();
        while (dollarText.length() < lengthTo) { dollarText += " "; }
        dollarText += "***\n";
        buyChipsMenu = "" +
                "********************************************************************************************************************************\n" +
                "***                                                   ZipCasino - Buy Chips                                                  ***\n" +
                "********************************************************************************************************************************\n" +
                dollarText +
                "********************************************************************************************************************************\n" +
                "***                  1 |  Buy Black                                                                                          ***\n" +
                "***                  2 |  Buy Green                                                                                          ***\n" +
                "***                  3 |  Buy Blue                                                                                           ***\n" +
                "***                  4 |  Buy White                                                                                          ***\n" +
                "***--------------------------------------------------------------------------------------------------------------------------***\n" +
                "***                  5 |  Return to the currency menu                                                                        ***\n" +
                "***                  6 |  Return to the main menu                                                                            ***\n" +
                "********************************************************************************************************************************\n" +
                "*** Enter a command - By default will purchase the maximum amount of chips, unless an additional amount argument is provided ***\n" +
                "********************************************************************************************************************************\n";
        individualStats = "" +
                "*************************************************************************\n" +
                "***                      ZipCasino - Stats Menu                       ***\n" +
                "*************************************************************************\n" +
                "***    "+ playerStats.getBlackJackWins() +" |  Black Jack Wins                                           ***\n" +
                "***    "+ playerStats.getGoFishWins() +" |  Go Fish Wins                                              ***\n" +
                "***    "+ playerStats.getLoopyWins() +" |  Loopy Dice Wins                                           ***\n" +
                "***    "+ playerStats.getCrapsWins() +" |  Craps Wins                                                ***\n" +
                "***    "+ playerStats.getOverallWins() +" |  Overall Wins                                              ***\n" +
                "***    "+ playerStats.getOverallLosses() +" |  Overall Losses                                            ***\n" +
                "***    "+ playerStats.getGamblingWins() +" |  Gambling Wins                                             ***\n" +
                "***    "+ playerStats.getTotalLifetimeChipWinnings() +" |  Total Lifetime Chip Winnings                              ***\n" +
                "***    "+ playerStats.getTotalCashSpent() +" |  Total Cash Spent                                          ***\n" +
                "***-------------------------------------------------------------------***\n" +
                "***    0 |  Return to main menu                                       ***\n" +
                "***    1 |  Leaderboard                                               ***\n" +
                "*************************************************************************\n" +
                "***                          Enter a command                          ***\n" +
                "*************************************************************************\n" ;

        String toCheck = "*********************************************************************";
        String front = "***    ";
        String black = "" + blackChips;
        String green = ""+ greenChips;
        String blue = ""+ blueChips;
        String white = ""+ whiteChips;

        String formattedBlack = front + String.format("%12s", black) + " |  Black";
        String formattedGreen = front + String.format("%12s", green) + " |  Green";
        String formattedBlue = front + String.format("%12s", blue) + " |  Blue";
        String formattedWhite = front + String.format("%12s", white) + " |  White";

        while (formattedBlack.length() < toCheck.length()) { formattedBlack += " "; } formattedBlack += " ***\n";
        while (formattedGreen.length() < toCheck.length()) { formattedGreen += " "; } formattedGreen += " ***\n";
        while (formattedBlue.length() < toCheck.length()) { formattedBlue += " "; } formattedBlue += " ***\n";
        while (formattedWhite.length() < toCheck.length()) { formattedWhite += " "; }  formattedWhite += " ***\n";

        viewChipsMenu = "" +
                "*************************************************************************\n" +
                "***                       ZipCasino - View Chips                      ***\n" +
                "*************************************************************************\n" +
                formattedBlack +
                formattedGreen +
                formattedBlue +
                formattedWhite +
                "***-------------------------------------------------------------------***\n" +
                "***    0 |  Return to currency menu                                   ***\n" +
                "***    1 |  Return to main menu                                       ***\n" +
                "*************************************************************************\n" +
                "***                          Enter a command                          ***\n" +
                "*************************************************************************\n" ;

        statsMenu = "" +
                "*************************************************************************\n" +
                "***                      ZipCasino - Stats Menu                       ***\n" +
                "*************************************************************************\n" +
                "***    1 |  Leaderboard                                               ***\n" +
                "***    2 |  Individual stats                                          ***\n" +
                "***    0 |  Return to the main menu                                   ***\n" +
                "*************************************************************************\n" +
                "***                          Enter a command                          ***\n" +
                "*************************************************************************\n" ;

        leaderBoard = "" +
                "*************************************************************************\n" +
                "***                      ZipCasino - Leaderboard                      ***\n" +
                "*************************************************************************\n" +
                leaderboardPlayers +
                "***-------------------------------------------------------------------***\n" +
                "***    0 |  Return to the main menu                                   ***\n" +
                "***    2 |  Individual stats                                          ***\n" +
                "*************************************************************************\n" +
                "***                          Enter a command                          ***\n" +
                "*************************************************************************\n" ;

        commands = "";

        goodbye = "" +
                "**************************************************\n" +
                "***             Always Be Loopin'              ***\n" +
                "**************************************************\n";

        loginMenuCommands = "" +
                "*******************************************************************\n" +
                "**                     Login Menu Commands                       **\n" +
                "**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Login      | login [USER] [PASS]                              **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Register   | register [USER] [PASS]                           **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Help       | Prints all commands in any menu                  **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Logout     | Save, Logout and Quit ZipCasino App              **\n" +
                "**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "*******************************************************************\n";

        currencyMenuCommands = "" +
                "*******************************************************************\n" +
                "**                   Currency Menu Commands                      **\n" +
                "**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Buy Chips   | Trade cash for chips                            **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Cashout     | Empty account of all cash                       **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Deposit     | Add cash to account                             **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** View Chips  | View all currently held chips                   **\n" +
                "**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "*******************************************************************\n";

        gamesMenuCommands = "" +
                "*******************************************************************\n" +
                "**                     Games Menu Commands                       **\n" +
                "**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** BlackJack   | Play BlackJack                                  **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Go Fish     | Play Go Fish                                    **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Loopy Dice  | Play Loopy Dice                                 **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Craps       | Play Craps                                      **\n" +
                "**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "*******************************************************************\n";

        statsMenuCommands = "" +
                "*******************************************************************\n" +
                "**                     Stats Menu Commands                       **\n" +
                "**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Leaderboard        | View rankings of all players in database **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Individual Stats   | View your own personal stats             **\n" +
                "**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "*******************************************************************\n";

        mainMenuCommands = "" +
                "*******************************************************************************************\n" +
                "**                                  Main Menu Commands                                   **\n" +
                "**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Games     | Enter the games menu and play BlackJack, GoFish, Loopy Dice or Craps      **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Currency  | Enter the currency menu and modify your current wallet                    **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Money     | Enter the currency menu and modify your current wallet                    **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Stats     | Enter the stats menu and view leaderboards and personal stats             **\n" +
                "** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "** Main      | Enter at any point outside of gameplay to return to main menu             **\n" +
                "**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**\n" +
                "*******************************************************************************************\n";

    }

    public static String getStringFromPromptType(AbstractConsole.PromptMessage msg) {
        loadStrings();
        switch (msg) {
            case LOGIN:
                return login;
            case STANDARD:
                return standard;
            case BAD_LOGIN:
                return badLogin;
            case GAMES_MENU:
                return gamesMenu;
            case STATS_MENU:
                return statsMenu;
            case CURRENCY_MENU:
                return currencyMenu;
            case BUY_CHIPS_MENU:
                return buyChipsMenu;
            case VIEW_CHIPS_MENU:
                return viewChipsMenu;
            case LEADERBOARD:
                return leaderBoard;
            case IND_STATS:
                return individualStats;
            case GOODBYE:
                return goodbye;
            case WELCOME:
                return onceLogin;
            default:
                return "";
        }
    }

    public static String getRandomOpponentName() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("Scuba Steve");
        names.add("Gandalf");
        names.add("Sauron");
        names.add("Darth Vader");
        names.add("Dealer");
        names.add("John");
        names.add("Pete");
        names.add("Joe");
        names.add("Jack");
        names.add("Mary");
        names.add("Jill");
        names.add("Ashley");
        names.add("Steve-O");
        names.add("Joe Rogan");
        names.add("Kris Younger");
        names.add("Chris Nobles");
        names.add("Roberto");
        names.add("Adam");
        names.add("Raheel");
        names.add("Mo");
        names.add("Sandy");
        names.add("Dave");
        names.add("Zeth");
        names.add("Fat Guy");
        names.add("Noob");
        names.add("RoboCop");
        names.add("Mr. Nice Guy");
        names.add("Your Girlfriend");
        names.add("Hobo Jim");
        names.add("Hobo Jeff");
        names.add("Dan Stabb");
        names.add("Desa");
        names.add("Opponent");
        names.add("Bus Stop Guy");
        names.add("Russian Spy");
        names.add("Donald Trump");
        names.add("John Oliver");
        names.add("Hot Girl");
        names.add("Timmy Turner");
        names.add("Spongebob");
        names.add("Patrick Star");
        names.add("Ash Ketchum");
        names.add("My Dad");
        names.add("A Monkey");
        names.add("A Platypus");
        names.add("Farmer Joe");
        names.add("Farmer Rick");
        names.add("Ashy Larry");
        names.add("Rick James");
        names.add("Ric Flair");
        names.add("Post Malone");
        names.add("Danny Carey");
        names.add("Neil Peart");
        names.add("Jedi");
        names.add("Spock");
        names.add("Beady Eyes McGee");
        names.add("Yosemite Sam");
        names.add("Peter Griffin");
        names.add("Easter Bunny");
        names.add("Uncle Bob");
        names.add("Santa Claus");
        names.add("Kitty Rotten");
        names.add("Blue Man Group");
        names.add("Nick Foles");
        return names.get(ThreadLocalRandom.current().nextInt(names.size()));
    }

}
