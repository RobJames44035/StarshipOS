/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.constraint;

import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.MatchResult;

import java.util.List;

/**
 * This class provides a check on a single {@link IR#failOn()} {@link Constraint}.
 *
 * @see Constraint
 */
class FailOnConstraintCheck implements ConstraintCheck {
    private final String nodeRegex;
    private final int constraintId; // constraint indices start at 1.

    public FailOnConstraintCheck(String nodeRegex, int constraintId) {
        this.nodeRegex = nodeRegex;
        this.constraintId = constraintId;
    }

    @Override
    public MatchResult check(List<String> matchedNodes) {
        if (!matchedNodes.isEmpty()) {
            return new FailOnConstraintFailure(nodeRegex, constraintId, matchedNodes);
        }
        return SuccessResult.getInstance();
    }
}
