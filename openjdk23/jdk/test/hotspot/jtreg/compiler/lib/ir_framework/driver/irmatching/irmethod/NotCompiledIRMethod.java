/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.irmethod;

import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.Run;
import compiler.lib.ir_framework.RunMode;

import java.lang.reflect.Method;

/**
 * This class represents a special IR method which was not compiled by the IR framework. This could happen when a
 * method is not invoked enough times by a {@link Run @Run} method in {@link RunMode#STANDALONE} mode to be C2 compiled.
 *
 * @see IR
 * @see Run
 */
public class NotCompiledIRMethod implements IRMethodMatchable {
    private final Method method;
    private final int ruleCount;

    public NotCompiledIRMethod(Method method, int ruleCount) {
        this.method = method;
        this.ruleCount = ruleCount;
    }

    @Override
    public String name() {
        return method.getName();
    }

    /**
     * Directly return a {@link NotCompiledIRMethodMatchResult} as we do not need to match IR rules individually.
     */
    @Override
    public NotCompiledIRMethodMatchResult match() {
        return new NotCompiledIRMethodMatchResult(method, ruleCount);
    }
}
