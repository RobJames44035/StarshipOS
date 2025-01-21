/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irrule;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.MatchResult;
import compiler.lib.ir_framework.driver.irmatching.irrule.phase.CompilePhaseIRRuleMatchResult;
import compiler.lib.ir_framework.driver.irmatching.visitor.AcceptChildren;
import compiler.lib.ir_framework.driver.irmatching.visitor.MatchResultVisitor;

import java.util.List;

/**
 * This class represents a match result of an {@link IRRule} (applied to all compile phases specified in
 * {@link IR#phase()}). The {@link CompilePhaseIRRuleMatchResult} are kept in the definition order of the compile phases
 * in {@link CompilePhase}.
 *
 * @see IRRule
 */
public class IRRuleMatchResult implements MatchResult {
    private final AcceptChildren acceptChildren;
    private final boolean failed;
    private final int irRuleId;
    private final IR irAnno;

    public IRRuleMatchResult(int irRuleId, IR irAnno, List<MatchResult> matchResults) {
        this.acceptChildren = new AcceptChildren(matchResults);
        this.failed = !matchResults.isEmpty();
        this.irRuleId = irRuleId;
        this.irAnno = irAnno;
    }

    @Override
    public boolean fail() {
        return failed;
    }

    @Override
    public void accept(MatchResultVisitor visitor) {
        visitor.visitIRRule(acceptChildren, irRuleId, irAnno);
    }
}
