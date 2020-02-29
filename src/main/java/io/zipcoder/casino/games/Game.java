package io.zipcoder.casino.games;

import io.zipcoder.casino.players.Player;
import io.zipcoder.casino.utilities.io.ConsoleServices;

import java.util.ArrayList;

public abstract  class Game<PlayerType extends Player, OtherPlayerType extends Player> {
    private PlayerType currentPlayer;
    private OtherPlayerType opponent;
    private String gameName;

    public abstract void runGame(PlayerType player);

    @Override
    public boolean equals(Object o) {
        if (o instanceof Game) {
            return ((Game) o).getCurrentPlayer().equals(this.getCurrentPlayer()) && ((Game) o).getOpponent().equals(this.getOpponent());
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        if (this.currentPlayer != null && this.opponent != null) {
            return this.currentPlayer.getName() + this.opponent.getName();
        }
        return "New Game";
    }

    public PlayerType getCurrentPlayer() {
        return currentPlayer;
    }

    public OtherPlayerType getOpponent() {
        return opponent;
    }

    public void setCurrentPlayer(PlayerType currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setOpponent(OtherPlayerType opponent) {
        this.opponent = opponent;
    }
}
