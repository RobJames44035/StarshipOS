/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule.phase;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.IRNode;
import compiler.lib.ir_framework.driver.irmatching.MatchResult;
import compiler.lib.ir_framework.driver.irmatching.Matchable;
import compiler.lib.ir_framework.driver.irmatching.MatchableMatcher;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.Counts;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.FailOn;

import java.util.List;

/**
 * This class represents an IR rule of an IR method for a specific compile phase. It contains fully parsed (i.e.
 * all placeholder strings of {@link IRNode} replaced and composite nodes merged) {@link FailOn} and/or {@link Counts}
 * check attributes which are ready to be matched on.
 *
 * @see FailOn
 * @see Counts
 * @see CompilePhaseIRRuleMatchResult
 */
public class CompilePhaseIRRule implements CompilePhaseIRRuleMatchable {
    private final CompilePhase compilePhase;
    private final MatchableMatcher matcher;
    private final String compilationOutput;

    public CompilePhaseIRRule(CompilePhase compilePhase, List<Matchable> checkAttributes, String compilationOutput) {
        this.compilePhase = compilePhase;
        this.matcher = new MatchableMatcher(checkAttributes);
        this.compilationOutput = compilationOutput;
    }

    @Override
    public MatchResult match() {
        return new CompilePhaseIRRuleMatchResult(compilePhase, compilationOutput, matcher.match());
    }

    @Override
    public CompilePhase compilePhase() {
        return compilePhase;
    }
}
