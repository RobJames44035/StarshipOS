/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.phase;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.driver.irmatching.MatchResult;
import compiler.lib.ir_framework.driver.irmatching.visitor.MatchResultVisitor;

/**
 * This class represents a special match result of {@link CompilePhaseNoCompilationIRRule} where the compilation output
 * for the compile phase was empty.
 *
 * @see CompilePhaseNoCompilationIRRule
 */
public record CompilePhaseNoCompilationIRRuleMatchResult(CompilePhase compilePhase) implements MatchResult {

    @Override
    public boolean fail() {
        return true;
    }

    @Override
    public void accept(MatchResultVisitor visitor) {
        visitor.visitNoCompilePhaseCompilation(compilePhase);
    }
}
