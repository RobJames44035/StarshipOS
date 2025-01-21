/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.shared;

/**
 * Exception thrown when {@link ComparisonConstraintParser} parses an invalid value.
 */
class InvalidConstraintValueException extends Exception {
    private final String invalidValue;
    private final String comparator;

    public InvalidConstraintValueException(String invalidValue, String comparator) {
        this.invalidValue = invalidValue;
        this.comparator = comparator;
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    public String getComparator() {
        return comparator;
    }
}
