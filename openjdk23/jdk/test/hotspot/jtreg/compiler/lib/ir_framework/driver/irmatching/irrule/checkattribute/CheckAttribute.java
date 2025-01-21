/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute;

import compiler.lib.ir_framework.driver.irmatching.MatchResult;
import compiler.lib.ir_framework.driver.irmatching.Matchable;
import compiler.lib.ir_framework.driver.irmatching.MatchableMatcher;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.Constraint;

import java.util.List;

/**
 * This class represents a fully parsed check attribute of an IR rule for a compile phase that is ready to be IR matched
 * on. This class is part of {@link FailOn} and/or {@link Counts}.
 *
 * @see FailOn
 * @see Counts
 * @see Constraint
 * @see CheckAttributeMatchResult
 */
class CheckAttribute implements Matchable {
    private final MatchableMatcher matcher;
    private final CheckAttributeType checkAttributeType;

    public CheckAttribute(CheckAttributeType checkAttributeType, List<Constraint> constraints) {
        this.matcher = new MatchableMatcher(constraints);
        this.checkAttributeType = checkAttributeType;
    }

    @Override
    public MatchResult match() {
        return new CheckAttributeMatchResult(checkAttributeType, matcher.match());
    }
}
