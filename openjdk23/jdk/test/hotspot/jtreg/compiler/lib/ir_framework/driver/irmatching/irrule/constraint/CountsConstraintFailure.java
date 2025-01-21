/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.constraint;

import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.Counts;
import compiler.lib.ir_framework.driver.irmatching.visitor.MatchResultVisitor;
import compiler.lib.ir_framework.shared.Comparison;

import java.util.List;

/**
 * This class represents a failed match result of a counts {@link Constraint} on a compile phase output.
 *
 * @see Constraint
 * @see Counts
 */
public record CountsConstraintFailure(String nodeRegex, int constraintId, List<String> matchedNodes,
                                      Comparison<Integer> comparison) implements ConstraintFailure {
    @Override
    public void accept(MatchResultVisitor visitor) {
        visitor.visitCountsConstraint(this);
    }
}
