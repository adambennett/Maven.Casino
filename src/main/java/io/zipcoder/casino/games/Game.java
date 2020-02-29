package io.zipcoder.casino.games;

import io.zipcoder.casino.players.Player;
import io.zipcoder.casino.utilities.io.ConsoleServices;

import java.util.ArrayList;

public interface Game<PlayerType extends Player> {
    void runGame(PlayerType player);
}
