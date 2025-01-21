/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.parser;

import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.driver.irmatching.irmethod.IRMethod;
import compiler.lib.ir_framework.driver.irmatching.parser.hotspot.LoggedMethod;

import java.lang.reflect.Method;

/**
 * This class represents a test method parsed by {@link IREncodingParser}. In combination with the associated
 * {@link LoggedMethod}, a new {@link IRMethod} is created to IR match on later.
 *
 * @see IREncodingParser
 * @see LoggedMethod
 * @see IRMethod
 */
public class TestMethod {
    private final Method method;
    private final IR[] irAnnos;
    private final int[] irRuleIds;

    public TestMethod(Method m, IR[] irAnnos, int[] irRuleIds) {
        this.method = m;
        this.irAnnos = irAnnos;
        this.irRuleIds = irRuleIds;
    }

    public Method method() {
        return method;
    }

    public IR[] irAnnos() {
        return irAnnos;
    }

    public int[] irRuleIds() {
        return irRuleIds;
    }
}
