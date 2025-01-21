/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.visitor;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.MatchResult;
import compiler.lib.ir_framework.driver.irmatching.irrule.checkattribute.CheckAttributeType;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.CountsConstraintFailure;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.FailOnConstraintFailure;

import java.lang.reflect.Method;

/**
 * This interface specifies visit methods for each {@link MatchResult} class must be implemented a by a concrete visitor.
 */
public interface MatchResultVisitor {
    void visitTestClass(AcceptChildren acceptChildren);
    void visitIRMethod(AcceptChildren acceptChildren, Method method, int failedIRRules);
    void visitMethodNotCompiled(Method method, int failedIRRules);
    void visitIRRule(AcceptChildren acceptChildren, int irRuleId, IR irAnno);
    void visitCompilePhaseIRRule(AcceptChildren acceptChildren, CompilePhase compilePhase, String compilationOutput);
    void visitNoCompilePhaseCompilation(CompilePhase compilePhase);
    void visitCheckAttribute(AcceptChildren acceptChildren, CheckAttributeType checkAttributeType);
    void visitFailOnConstraint(FailOnConstraintFailure failOnConstraintFailure);
    void visitCountsConstraint(CountsConstraintFailure countsConstraintFailure);
}

