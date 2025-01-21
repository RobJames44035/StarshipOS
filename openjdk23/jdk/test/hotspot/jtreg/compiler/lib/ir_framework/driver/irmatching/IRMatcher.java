/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching;

import compiler.lib.ir_framework.driver.irmatching.parser.TestClassParser;
import compiler.lib.ir_framework.driver.irmatching.report.CompilationOutputBuilder;
import compiler.lib.ir_framework.driver.irmatching.report.FailureMessageBuilder;

/**
 * This class performs IR matching on the prepared {@link TestClass} object parsed by {@link TestClassParser}.
 * All applicable @IR rules are matched with all their defined compilation phases. If there are any IR matching failures,
 * an {@link IRViolationException} is reported which provides a formatted failure message and the compilation outputs
 * of the failed compilation phases.
 */
public class IRMatcher {
    private final Matchable testClass;

    public IRMatcher(Matchable testClass) {
        this.testClass = testClass;
    }

    /**
     * Do an IR matching of all methods with applicable @IR rules prepared with by the {@link TestClassParser}.
     */
    public void match() {
        MatchResult result = testClass.match();
        if (result.fail()) {
            reportFailures(result);
        }
    }

    /**
     * Report all IR violations in a pretty format to the user by throwing an {@link IRViolationException}. This includes
     * an exact description of the failure (method, rule, compile phase, check attribute, and constraint) and the
     * associated compile phase output of the failure.
     */
    private void reportFailures(MatchResult result) {
        String failureMsg = new FailureMessageBuilder(result).build();
        String compilationOutput = new CompilationOutputBuilder(result).build();
        throw new IRViolationException(failureMsg, compilationOutput);
    }
}
