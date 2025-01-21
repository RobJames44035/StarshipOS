/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.action.CreateRawFailOnConstraintAction;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.action.DefaultPhaseFailOnConstraintAction;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.raw.RawConstraint;

import java.util.List;
import java.util.Set;

/**
 * This class offers different parsing operations on the raw strings as found in a {@link IR#failOn()} check attribute.
 *
 * @see IR#failOn()
 */
public class RawFailOn implements RawCheckAttribute {
    private final CheckAttributeStrings checkAttributeStrings;

    public RawFailOn(String[] checkAttributeStrings) {
        this.checkAttributeStrings = new CheckAttributeStrings(checkAttributeStrings);
    }

    @Override
    public List<RawConstraint> createRawConstraints() {
        return checkAttributeStrings.createRawConstraints(new CreateRawFailOnConstraintAction());
    }

    @Override
    public Set<CompilePhase> parseDefaultCompilePhases() {
        return checkAttributeStrings.parseDefaultCompilePhases(new DefaultPhaseFailOnConstraintAction());
    }
}
