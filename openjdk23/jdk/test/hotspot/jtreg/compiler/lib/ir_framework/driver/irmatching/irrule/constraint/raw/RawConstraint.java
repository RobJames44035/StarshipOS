/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.constraint.raw;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.IRNode;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.Constraint;
import compiler.lib.ir_framework.driver.irmatching.parser.VMInfo;

/**
 * Interface to represent a single raw constraint as found in the {@link IR @IR} annotation (i.e. {@link IRNode}
 * placeholder strings are not replaced by regexes, yet). A raw constraint can be parsed into a {@link Constraint} by
 * calling {@link #parse(CompilePhase, String)}. This replaces the IR node placeholder strings by actual regexes and
 * merges composite nodes together.
 *
 * @see Constraint
 */
public interface RawConstraint {
    CompilePhase defaultCompilePhase();
    Constraint parse(CompilePhase compilePhase, String compilationOutput, VMInfo vmInfo);
}
