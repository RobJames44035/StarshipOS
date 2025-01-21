/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.parser;

import compiler.lib.ir_framework.driver.irmatching.Compilation;
import compiler.lib.ir_framework.driver.irmatching.irmethod.IRMethod;
import compiler.lib.ir_framework.driver.irmatching.irmethod.IRMethodMatchable;
import compiler.lib.ir_framework.driver.irmatching.irmethod.NotCompiledIRMethod;
import compiler.lib.ir_framework.driver.irmatching.parser.hotspot.HotSpotPidFileParser;
import compiler.lib.ir_framework.driver.irmatching.parser.hotspot.LoggedMethod;
import compiler.lib.ir_framework.driver.irmatching.parser.hotspot.LoggedMethods;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Class to create {@link IRMethod} instances by combining the elements of {@link TestMethods} and {@link LoggedMethods}.
 */
class IRMethodBuilder {
    private final Map<String, LoggedMethod> loggedMethods;
    private final TestMethods testMethods;

    public IRMethodBuilder(TestMethods testMethods, LoggedMethods loggedMethods) {
        this.testMethods = testMethods;
        this.loggedMethods = loggedMethods.loggedMethods();
    }

    /**
     * Create IR methods for all test methods identified by {@link IREncodingParser} by combining them with the parsed
     * compilation output from {@link HotSpotPidFileParser}.
     */
    public SortedSet<IRMethodMatchable> build(VMInfo vmInfo) {
        SortedSet<IRMethodMatchable> irMethods = new TreeSet<>();
        testMethods.testMethods().forEach(
                (methodName, testMethod) -> irMethods.add(createIRMethod(methodName, testMethod, vmInfo)));
        return irMethods;
    }

    private IRMethodMatchable createIRMethod(String methodName, TestMethod testMethod, VMInfo vmInfo) {
        LoggedMethod loggedMethod = loggedMethods.get(methodName);
        if (loggedMethod != null) {
            return new IRMethod(testMethod.method(), testMethod.irRuleIds(), testMethod.irAnnos(),
                                new Compilation(loggedMethod.compilationOutput()), vmInfo);
        } else {
            return new NotCompiledIRMethod(testMethod.method(), testMethod.irRuleIds().length);
        }
    }
}
