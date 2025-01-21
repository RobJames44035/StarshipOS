/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.shared;

/**
 * Exception thrown when {@link ComparisonConstraintParser} cannot find a value in a constraint after a comparator.
 */
class MissingConstraintValueException extends Exception {
    private final String comparator;

    public MissingConstraintValueException(String comparator) {
        this.comparator = comparator;
    }

    public String getComparator() {
        return comparator;
    }
}
