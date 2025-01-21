/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */
package vm.gc.containers;

import nsk.share.TestBug;

public enum Speed {

    LOW(25), HIGH(75), MEDIUM(50);
    int value;

    Speed(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

    public static Speed fromString(String id) {
        if (id.equalsIgnoreCase("low")) {
            return LOW;
        } else if (id.equalsIgnoreCase("medium")) {
            return MEDIUM;
        } else if (id.equalsIgnoreCase("high")) {
            return HIGH;
        } else {
            throw new TestBug("Unknown speed identifier: " + id);
        }
    }
}
