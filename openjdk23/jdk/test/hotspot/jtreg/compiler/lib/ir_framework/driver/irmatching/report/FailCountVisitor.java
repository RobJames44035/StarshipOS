/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.report;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.CheckAttributeType;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.CountsConstraintFailure;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.FailOnConstraintFailure;
import compiler.lib.ir_framework.driver.irmatching.visitor.AcceptChildren;
import compiler.lib.ir_framework.driver.irmatching.visitor.MatchResultVisitor;

import java.lang.reflect.Method;

/**
 * Visitor to collect the number of IR method and IR rule failures.
 */
class FailCountVisitor implements MatchResultVisitor {
    private int irMethodCount;
    private int irRuleCount;

    @Override
    public void visitTestClass(AcceptChildren acceptChildren) {
        acceptChildren.accept(this);
    }

    @Override
    public void visitIRMethod(AcceptChildren acceptChildren, Method method, int failedIRRules) {
        irMethodCount++;
        acceptChildren.accept(this);
    }

    @Override
    public void visitIRRule(AcceptChildren acceptChildren, int irRuleId, IR irAnno) {
        irRuleCount++;
        // Do not need to visit compile phase IR rules
    }

    @Override
    public void visitMethodNotCompiled(Method method, int failedIRRules) {
        irMethodCount++;
        irRuleCount += failedIRRules;
    }

    public int getIrRuleCount() {
        return irRuleCount;
    }

    public int getIrMethodCount() {
        return irMethodCount;
    }

    @Override
    public void visitCompilePhaseIRRule(AcceptChildren acceptChildren, CompilePhase compilePhase, String compilationOutput) {}

    @Override
    public void visitNoCompilePhaseCompilation(CompilePhase compilePhase) {}

    @Override
    public void visitCheckAttribute(AcceptChildren acceptChildren, CheckAttributeType checkAttributeType) {}

    @Override
    public void visitFailOnConstraint(FailOnConstraintFailure failOnConstraintFailure) {}

    @Override
    public void visitCountsConstraint(CountsConstraintFailure countsConstraintFailure) {}
}
