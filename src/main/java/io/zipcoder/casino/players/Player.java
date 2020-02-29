package io.zipcoder.casino.players;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.zipcoder.casino.models.StatsModel;
import io.zipcoder.casino.models.Wallet;

public class Player implements Comparable<Player> {

    private String name;
    private String password;
    private Wallet wallet;
    private StatsModel stats;

    // For proper JSON deserialization
    public Player() {}

    // For tests
    public Player(String name) {
        this(name, "", new Wallet());
    }

    public Player(String name, String password, Wallet wallet) {
        this(name, password, wallet, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public Player(String name, String password, Wallet wallet, StatsModel stat) {
        this(name, password, wallet, stat.getBlackJackWins(), stat.getGoFishWins(), stat.getLoopyWins(), stat.getCrapsWins(), stat.getHighestChipValue(), stat.getOverallWins(), stat.getOverallLosses(), stat.getGamblingWins(), stat.getTotalLifetimeChipWinnings(), stat.getTotalCashSpent(), stat.getOverallScore());
    }

    @JsonCreator
    public Player(@JsonProperty("name")String name, @JsonProperty("password")String password, @JsonProperty("wallet")Wallet wallet, @JsonProperty("blackJackWins") int blackJackWins, @JsonProperty("goFishWins") int goFishWins, @JsonProperty("loopyWins") int loopyWins, @JsonProperty("crapsWins") int crapsWins, @JsonProperty("highestChipValue") int highestChipValue, @JsonProperty("overallWins") int overallWins, @JsonProperty("overallLosses") int overallLosses, @JsonProperty("gamblingWins") int gamblingWins, @JsonProperty("totalLifetimeChipWinnings") int totalLifetimeChipWinnings, @JsonProperty("totalCashSpent") int totalCashSpent, @JsonProperty("overallScore") int overallScore) {
        this.name = name;
        this.password = password;
        this.wallet = wallet;
        this.stats = new StatsModel(blackJackWins, goFishWins, loopyWins, crapsWins, highestChipValue,overallWins, overallLosses, gamblingWins, totalLifetimeChipWinnings, totalCashSpent, overallScore);
    }

    public String getName() {
        return name;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getPassword() { return password; }

    public StatsModel getStats() {
        return stats;
    }

    @Override
    public int compareTo(Player o) {
        return (this.stats.compareTo(o.getStats()));
    }
}
