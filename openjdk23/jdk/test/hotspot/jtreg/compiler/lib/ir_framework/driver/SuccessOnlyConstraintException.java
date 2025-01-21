/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package compiler.lib.ir_framework.driver;

import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.Constraint;

/**
 * Exception used to signal that a {@link Constraint} should always succeed.
 */
public class SuccessOnlyConstraintException extends RuntimeException {
    public SuccessOnlyConstraintException(String message) {
        super("Unhandled SuccessOnlyConstraintException, should have created a Constraint that always succeeds:" + System.lineSeparator() + message);
    }
}
