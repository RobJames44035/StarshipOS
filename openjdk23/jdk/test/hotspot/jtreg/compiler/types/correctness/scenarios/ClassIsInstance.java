/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.types.correctness.scenarios;

import compiler.types.correctness.hierarchies.TypeHierarchy;
import jdk.test.lib.Asserts;

/**
 * Tests {@link Class#isInstance(Object)}
 */
public class ClassIsInstance<T extends TypeHierarchy.I> extends Scenario<T, Integer> {
    private final Class<?> baseClass;

    public ClassIsInstance(ProfilingType profilingType,
                           TypeHierarchy<? extends T, ? extends T> hierarchy) {
        super("ClassIsInstance", profilingType, hierarchy);
        this.baseClass = hierarchy.getClassM();
    }

    @Override
    public Integer run(T obj) {
        switch (profilingType) {
            case RETURN:
                T t = collectReturnType(obj);
                if (baseClass.isInstance(t)) {
                    return inlinee(t);
                }
                return TypeHierarchy.TEMP;
            case ARGUMENTS:
                field = obj;
                if (baseClass.isInstance(field)) {
                    return inlinee(field);
                }
                return TypeHierarchy.TEMP;
            case PARAMETERS:
                if (baseClass.isInstance(obj)) {
                    return inlinee(obj);
                }
                return TypeHierarchy.TEMP;
        }
        throw new RuntimeException("Should not reach here");
    }

    public int inlinee(T obj) {
        return obj.m();
    }

    @Override
    public void check(Integer result, T orig) {
        if (baseClass.isInstance(orig)) {
            Asserts.assertEquals(result, orig.m(), "Results are not equal for base class");
        } else {
            Asserts.assertEquals(result, TypeHierarchy.TEMP, "Result differs from expected");
        }
    }
}
