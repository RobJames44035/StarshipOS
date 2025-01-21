/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irmethod;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.Test;
import compiler.lib.ir_framework.driver.irmatching.Compilation;
import compiler.lib.ir_framework.driver.irmatching.MatchResult;
import compiler.lib.ir_framework.driver.irmatching.Matchable;
import compiler.lib.ir_framework.driver.irmatching.MatchableMatcher;
import compiler.lib.ir_framework.driver.irmatching.irrule.IRRule;
import compiler.lib.ir_framework.driver.irmatching.parser.VMInfo;
import compiler.lib.ir_framework.shared.TestFormat;
import compiler.lib.ir_framework.shared.TestFormatException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a {@link Test @Test} annotated method that has an associated non-empty list of applicable
 * {@link IR @IR} rules.
 *
 * @see IR
 * @see IRRule
 * @see IRMethodMatchResult
 */
public class IRMethod implements IRMethodMatchable {
    private final Method method;
    private final MatchableMatcher matcher;

    public IRMethod(Method method, int[] ruleIds, IR[] irAnnos, Compilation compilation, VMInfo vmInfo) {
        this.method = method;
        this.matcher = new MatchableMatcher(createIRRules(method, ruleIds, irAnnos, compilation, vmInfo));
    }

    private List<Matchable> createIRRules(Method method, int[] ruleIds, IR[] irAnnos, Compilation compilation, VMInfo vmInfo) {
        List<Matchable> irRules = new ArrayList<>();
        for (int ruleId : ruleIds) {
            try {
                irRules.add(new IRRule(ruleId, irAnnos[ruleId - 1], compilation, vmInfo));
            } catch (TestFormatException e) {
                String postfixErrorMsg = " for IR rule " + ruleId + " at " + method + ".";
                TestFormat.failNoThrow(e.getMessage() + postfixErrorMsg);
            }
        }
        return irRules;
    }

    /**
     * Used only for sorting.
     */
    @Override
    public String name() {
        return method.getName();
    }

    /**
     * Apply all IR rules of this method for each of the specified (or implied in case of
     * {@link CompilePhase#DEFAULT}) compile phases.
     */
    @Override
    public MatchResult match() {
        return new IRMethodMatchResult(method, matcher.match());
    }
}
