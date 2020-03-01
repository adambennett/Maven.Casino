package io.zipcoder.casino.models;

import java.util.HashMap;
import java.util.Map;

public class Chip {

    private ChipValue val;

    public Chip(ChipValue val) {
        this.val = val;
    }

    public int getDollarVal() {
        return this.val.getValue();
    }

    public enum ChipValue {
        WHITE(1),
        BLUE(5),
        GREEN(25),
        BLACK(100);

        private int value;

        ChipValue(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    @Override
    public String toString() {
        return this.val.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chip) {
            return ((Chip) obj).toString().equals(this.toString());
        }
        return super.equals(obj);
    }

    public static ChipValue getEnumFromString(String chipType) {
        switch (chipType.toUpperCase()) {
            case "GREEN":
                return ChipValue.GREEN;
            case "BLUE":
                return ChipValue.BLUE;
            case "BLACK":
                return ChipValue.BLACK;
            default:
                return ChipValue.WHITE;
        }
    }

    // Needed for proper JSON deserialization
    public Chip(String test) {}

    // Needed for proper JSON deserialization
    public ChipValue getVal() {
        return val;
    }

    // Needed for proper JSON deserialization
    public void setVal(ChipValue val) {
        this.val = val;
    }


}
