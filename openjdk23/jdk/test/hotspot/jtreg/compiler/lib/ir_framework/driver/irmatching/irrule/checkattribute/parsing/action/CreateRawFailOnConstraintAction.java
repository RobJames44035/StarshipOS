/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.action;

import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.RawIRNode;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.raw.RawConstraint;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.raw.RawFailOnConstraint;

import java.util.ListIterator;

/**
 * This action class creates a {@link RawConstraint}.
 */
public class CreateRawFailOnConstraintAction implements CreateRawConstraintAction {
    @Override
    public RawConstraint apply(ListIterator<String> iterator, RawIRNode rawIRNode, int constraintIndex) {
        return new RawFailOnConstraint(rawIRNode, constraintIndex);
    }
}
