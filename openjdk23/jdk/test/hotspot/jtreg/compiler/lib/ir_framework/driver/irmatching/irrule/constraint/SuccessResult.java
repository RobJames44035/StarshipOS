/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.constraint;

import compiler.lib.ir_framework.driver.irmatching.MatchResult;
import compiler.lib.ir_framework.driver.irmatching.Matchable;
import compiler.lib.ir_framework.driver.irmatching.visitor.MatchResultVisitor;

/**
 * This class represents a successful match result of any {@link Matchable} object.
 */
public class SuccessResult implements MatchResult {
    private static final SuccessResult INSTANCE = new SuccessResult();

    private SuccessResult() {}

    public static SuccessResult getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean fail() {
        return false;
    }

    @Override
    public void accept(MatchResultVisitor visitor) {
        // Must not be visited.
    }
}
