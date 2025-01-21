/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.action;

import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.RawIRNode;

import java.util.ListIterator;

/**
 * Interface describing an action that can performed when reading a constraint of an {@link IR} check attribute. The
 * action creates a new object and returns it.
 */
public interface ConstraintAction<R> {
    R apply(ListIterator<String> iterator, RawIRNode rawIRNode, int constraintId);
}

