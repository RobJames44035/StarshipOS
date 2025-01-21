/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.phase;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.driver.irmatching.MatchResult;

/**
 * This class represents a special compile phase IR rule where no compilation output was found for the associated
 * compile phase. This could happen, if the user-defined test code does not exercise the code path that emits this
 * compile phase.
 *
 * @see CompilePhaseNoCompilationIRRuleMatchResult
 */
public record CompilePhaseNoCompilationIRRule(CompilePhase compilePhase) implements CompilePhaseIRRuleMatchable {

    @Override
    public MatchResult match() {
        return new CompilePhaseNoCompilationIRRuleMatchResult(compilePhase);
    }
}

