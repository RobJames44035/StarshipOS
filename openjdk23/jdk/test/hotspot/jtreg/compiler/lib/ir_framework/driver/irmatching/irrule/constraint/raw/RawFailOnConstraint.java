/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.constraint.raw;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.TestFramework;
import compiler.lib.ir_framework.driver.SuccessOnlyConstraintException;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.parsing.RawIRNode;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.Constraint;
import compiler.lib.ir_framework.driver.irmatching.parser.VMInfo;
import compiler.lib.ir_framework.shared.Comparison;

/**
 * This class represents a raw constraint of a {@link IR#failOn()} attribute.
 *
 * @see IR#failOn()
 */
public class RawFailOnConstraint implements RawConstraint {
    private final RawIRNode rawIRNode;
    private final int constraintIndex;

    public RawFailOnConstraint(RawIRNode rawIRNode, int constraintIndex) {
        this.rawIRNode = rawIRNode;
        this.constraintIndex = constraintIndex;
    }

    @Override
    public CompilePhase defaultCompilePhase() {
        return rawIRNode.defaultCompilePhase();
    }

    @Override
    public Constraint parse(CompilePhase compilePhase, String compilationOutput, VMInfo vmInfo) {
        TestFramework.check(compilePhase != CompilePhase.DEFAULT, "must not be default");
        try {
            return Constraint.createFailOn(rawIRNode.regex(compilePhase, vmInfo, Comparison.Bound.UPPER), constraintIndex, compilationOutput);
        } catch (SuccessOnlyConstraintException e) {
            return Constraint.createSuccess();
        }
    }
}

