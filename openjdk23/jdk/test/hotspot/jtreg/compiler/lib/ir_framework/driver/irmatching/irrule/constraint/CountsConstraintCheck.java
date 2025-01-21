/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.constraint;

import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.MatchResult;
import compiler.lib.ir_framework.shared.Comparison;

import java.util.List;

/**
 * This class provides a check on a single {@link IR#counts()} {@link Constraint}.
 *
 * @see Constraint
 */
class CountsConstraintCheck implements ConstraintCheck {
    private final String nodeRegex;
    private final int constraintId; // constraint indices start at 1.
    private final Comparison<Integer> comparison;

    public CountsConstraintCheck(String nodeRegex, int constraintId, Comparison<Integer> comparison) {
        this.comparison = comparison;
        this.nodeRegex = nodeRegex;
        this.constraintId = constraintId;
    }

    @Override
    public MatchResult check(List<String> matchedNodes) {
        if (!comparison.compare(matchedNodes.size())) {
            return new CountsConstraintFailure(nodeRegex, constraintId, matchedNodes, comparison);
        }
        return SuccessResult.getInstance();
    }
}
