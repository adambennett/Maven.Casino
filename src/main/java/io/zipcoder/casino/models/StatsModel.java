package io.zipcoder.casino.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StatsModel implements Comparable<StatsModel> {

    private int blackJackWins = 0;
    private int goFishWins = 0;
    private int loopyWins = 0;
    private int crapsWins = 0;
    private int highestChipValue = 0;
    private int overallWins = 0;
    private int overallLosses = 0;
    private int gamblingWins = 0;
    private int totalLifetimeChipWinnings = 0;
    private int totalCashSpent = 0;
    private int overallScore = 0;

    public StatsModel() {
        this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    @JsonCreator
    public StatsModel(
            @JsonProperty("blackJackWins") int blackJackWins,
            @JsonProperty("goFishWins")int goFishWins,
            @JsonProperty("loopyWins")int loopyWins,
            @JsonProperty("crapsWins")int crapsWins,
            @JsonProperty("highestChipValue")int highestChipValue,
            @JsonProperty("overallWins")int overallWins,
            @JsonProperty("overallLosses")int overallLosses,
            @JsonProperty("gamblingWins")int gamblingWins,
            @JsonProperty("totalLifetimeChipWinnings")int totalLifetimeChipWinnings,
            @JsonProperty("totalCashSpent")int totalCashSpent,
            @JsonProperty("overallScore")int overallScore) {
        this.blackJackWins = blackJackWins;
        this.goFishWins = goFishWins;
        this.loopyWins = loopyWins;
        this.crapsWins = crapsWins;
        this.highestChipValue = highestChipValue;
        this.overallWins = overallWins;
        this.overallLosses = overallLosses;
        this.gamblingWins = gamblingWins;
        this.totalLifetimeChipWinnings = totalLifetimeChipWinnings;
        this.totalCashSpent = totalCashSpent;
        this.overallScore = overallScore;
    }

    public int getBlackJackWins() {
        return blackJackWins;
    }

    public void setBlackJackWins(int blackJackWins) {
        this.blackJackWins = blackJackWins;
    }

    public int getGoFishWins() {
        return goFishWins;
    }

    public void setGoFishWins(int goFishWins) {
        this.goFishWins = goFishWins;
    }

    public int getLoopyWins() {
        return loopyWins;
    }

    public void setLoopyWins(int loopyWins) {
        this.loopyWins = loopyWins;
    }

    public int getCrapsWins() {
        return crapsWins;
    }

    public void setCrapsWins(int crapsWins) {
        this.crapsWins = crapsWins;
    }

    public int getHighestChipValue() {
        return highestChipValue;
    }

    public void setHighestChipValue(int highestChipValue) {
        this.highestChipValue = highestChipValue;
    }

    public int getOverallWins() {
        return overallWins;
    }

    public void setOverallWins(int overallWins) {
        this.overallWins = overallWins;
    }

    public int getOverallLosses() {
        return overallLosses;
    }

    public void setOverallLosses(int overallLosses) {
        this.overallLosses = overallLosses;
    }

    public int getGamblingWins() {
        return gamblingWins;
    }

    public void setGamblingWins(int gamblingWins) {
        this.gamblingWins = gamblingWins;
    }

    public int getTotalLifetimeChipWinnings() {
        return totalLifetimeChipWinnings;
    }

    public void setTotalLifetimeChipWinnings(int totalLifetimeChipWinnings) {
        this.totalLifetimeChipWinnings = totalLifetimeChipWinnings;
    }

    public int getTotalCashSpent() {
        return totalCashSpent;
    }

    public void setTotalCashSpent(int totalCashSpent) {
        this.totalCashSpent = totalCashSpent;
    }

    public int getOverallScore() {
        int score = 0;
        score += this.overallWins * 3;
        score -= this.overallLosses;
        score += this.gamblingWins;
        score += this.highestChipValue / 3.0f;
        if (score < 0) { score = 0; }
        return score;
    }

    @Override
    public int compareTo(StatsModel o) {
        int weightScoreThis = this.getOverallScore();
        int weightScoreOther = o.getOverallScore();
        return (weightScoreThis > weightScoreOther) ? 1 : (weightScoreThis < weightScoreOther) ? -1 : 0;
    }

    // Needed for proper JSON deserialization
    public void setOverallScore(int overallScore) {
        this.overallScore = overallScore;
    }
}