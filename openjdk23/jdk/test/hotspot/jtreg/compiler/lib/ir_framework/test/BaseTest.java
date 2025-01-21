/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework.test;

import compiler.lib.ir_framework.*;
import compiler.lib.ir_framework.shared.TestRunException;

import java.lang.reflect.Method;

/**
 * A base test only consists of a single @Test method. See {@link Test} for more details and its precise definition.
 */
class BaseTest extends AbstractTest {
    private final DeclaredTest test;
    protected final Method testMethod;
    protected final TestInfo testInfo;
    protected final Object invocationTarget;
    private final boolean shouldCompile;
    private final boolean waitForCompilation;
    private int invocationCounter;

    public BaseTest(DeclaredTest test, boolean skip) {
        super(test.getWarmupIterations(), skip);
        this.test = test;
        this.testMethod = test.getTestMethod();
        this.testInfo = new TestInfo(testMethod, test.getCompLevel());
        this.invocationTarget = createInvocationTarget(testMethod);
        this.shouldCompile = shouldCompile(test);
        this.waitForCompilation = isWaitForCompilation(test);
        this.invocationCounter = 0;
    }

    @Override
    public String toString() {
        return "Base Test: @Test " + testMethod.getName();
    }

    @Override
    public String getName() {
        return testMethod.getName();
    }

    @Override
    public void onWarmupFinished() {
        testInfo.setWarmUpFinished();
    }

    @Override
    protected void invokeTest() {
        verify(invokeTestMethod());
    }

    /**
     * Compute arguments (and possibly set fields), and invoke the test method.
     */
    private Object invokeTestMethod() {
        Object[] arguments = test.getArguments(invocationTarget, invocationCounter++);
        try {
            return testMethod.invoke(invocationTarget, arguments);
        } catch (Exception e) {
            throw new TestRunException("There was an error while invoking @Test method " + testMethod +
                                       ". Target: " + invocationTarget +
                                       ". Arguments: " + test.formatArguments(arguments),
                                       e);
        }
    }

    @Override
    protected void compileTest() {
        if (shouldCompile) {
            if (waitForCompilation) {
                waitForCompilation(test);
            } else {
                compileMethod(test);
            }
        }
    }

    /**
     * Verify the result
     */
    public void verify(Object result) { /* no verification in BaseTests */ }
}
