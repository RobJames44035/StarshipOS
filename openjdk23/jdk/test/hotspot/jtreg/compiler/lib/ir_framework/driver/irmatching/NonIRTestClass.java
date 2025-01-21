/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching;

import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.irrule.constraint.SuccessResult;

/**
 * Test class that does not contain any applicable {@link IR @IR} annotations and therefore does not fail. It simply
 * returns a {@link SuccessResult} object when being matched.
 */
public class NonIRTestClass implements Matchable {

    @Override
    public MatchResult match() {
        return SuccessResult.getInstance();
    }
}
