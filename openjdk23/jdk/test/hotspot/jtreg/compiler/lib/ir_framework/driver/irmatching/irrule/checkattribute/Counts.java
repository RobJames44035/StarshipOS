/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute;

import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.MatchResult;
import compiler.lib.ir_framework.driver.irmatching.Matchable;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.Constraint;

import java.util.List;

/**
 * This class represents a fully parsed {@link IR#counts()} attribute of an IR rule for a compile phase that is ready
 * to be IR matched on.
 *
 * @see IR#counts()
 */
public class Counts implements Matchable {
    private final CheckAttribute checkAttribute;

    public Counts(List<Constraint> constraints) {
        this.checkAttribute = new CheckAttribute(CheckAttributeType.COUNTS, constraints);
    }

    @Override
    public MatchResult match() {
        return checkAttribute.match();
    }
}
