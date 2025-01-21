/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.action.CreateRawCountsConstraintAction;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.action.DefaultPhaseCountsConstraintAction;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.raw.RawConstraint;

import java.util.List;
import java.util.Set;

/**
 * This class offers different parsing operations on the raw strings as found in a {@link IR#counts()} check attribute.
 *
 * @see IR#counts
 */
public class RawCounts implements RawCheckAttribute {
    private final CheckAttributeStrings checkAttributeStrings;

    public RawCounts(String[] checkAttributeStrings) {
        this.checkAttributeStrings = new CheckAttributeStrings(checkAttributeStrings);
    }

    @Override
    public List<RawConstraint> createRawConstraints() {
        return checkAttributeStrings.createRawConstraints(new CreateRawCountsConstraintAction());
    }

    @Override
    public Set<CompilePhase> parseDefaultCompilePhases() {
        return checkAttributeStrings.parseDefaultCompilePhases(new DefaultPhaseCountsConstraintAction());
    }
}
