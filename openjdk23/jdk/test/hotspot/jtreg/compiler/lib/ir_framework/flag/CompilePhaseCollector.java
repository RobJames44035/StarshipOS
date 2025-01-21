/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.flag;

import compiler.lib.ir_framework.CompilePhase;
import compiler.lib.ir_framework.IR;
import compiler.lib.ir_framework.IRNode;
import compiler.lib.ir_framework.shared.TestFormatException;

import java.lang.reflect.Method;
import java.util.*;

/**
 * This class collects all unique compile phases associated with all {@link IR @IR} annotations of a method.
 * If {@link CompilePhase#DEFAULT} is found, then we look up the default compile phases of all {@link IRNode}
 * placeholder strings.
 *
 * @see FlagVM
 * @see CompilerDirectivesFlagBuilder
 */
class CompilePhaseCollector {

    /**
     * Returns a map "method name -> compile phases set" that can be used by {@link CompilerDirectivesFlagBuilder}.
     */
    public static Map<String, Set<CompilePhase>> collect(Class<?> testClass) {
        Map<String, Set<CompilePhase>> methodNameToCompilePhasesMap = new HashMap<>();
        List<Method> irAnnotatedMethods = getIRAnnotatedMethods(testClass);
        try {
            for (Method method : irAnnotatedMethods) {
                methodNameToCompilePhasesMap.put(testClass.getCanonicalName() + "::" + method.getName(),
                                                 collectCompilePhases(method));
            }
        } catch (TestFormatException e) {
            // Create default map and let the IR matcher report the format failures later in the driver VM.
            return createDefaultMap(testClass);
        }
        return methodNameToCompilePhasesMap;
    }

    private static Set<CompilePhase> collectCompilePhases(Method method) {
        return new MethodCompilePhaseCollector(method).collect();
    }

    private static List<Method> getIRAnnotatedMethods(Class<?> testClass) {
        return Arrays.stream(testClass.getDeclaredMethods()).filter(m -> m.getAnnotationsByType(IR.class).length > 0).toList();
    }

    /**
     * Creates a default map that just contains PrintIdeal and PrintOptoAssembly.
     */
    private static Map<String, Set<CompilePhase>> createDefaultMap(Class<?> testClass) {
        Map<String, Set<CompilePhase>> defaultMap = new HashMap<>();
        HashSet<CompilePhase> defaultSet = new HashSet<>();
        defaultSet.add(CompilePhase.PRINT_IDEAL);
        defaultSet.add(CompilePhase.PRINT_OPTO_ASSEMBLY);
        defaultMap.put(testClass.getCanonicalName() + "::*", defaultSet);
        return defaultMap;
    }
}
