/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.internal.classfile.impl;

public final class UtilAccess {
    public static int significantOctalDigits() {
        return Util.SIGNIFICANT_OCTAL_DIGITS;
    }

    public static int powersIndex(int digit, int index) {
        return Util.powersIndex(digit, index);
    }

    public static int[] powersTable() {
        return Util.powers;
    }

    public static int reverse31() {
        return Util.INVERSE_31;
    }
}
