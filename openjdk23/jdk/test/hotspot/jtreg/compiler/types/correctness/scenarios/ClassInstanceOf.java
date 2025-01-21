/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.types.correctness.scenarios;

import compiler.types.correctness.hierarchies.TypeHierarchy;
import jdk.test.lib.Asserts;

/**
 * Tests instanceof
 */
public class ClassInstanceOf<T extends TypeHierarchy.I> extends Scenario<T, Integer> {
    public ClassInstanceOf(ProfilingType profilingType,
                           TypeHierarchy<? extends T, ? extends T> hierarchy) {
        super("ClassInstanceOf", profilingType, hierarchy);
    }

    @Override
    public Integer run(T obj) {
        switch (profilingType) {
            case RETURN:
                T t = collectReturnType(obj);
                if (t instanceof TypeHierarchy.A) {
                    return inlinee(t);
                }
                return TypeHierarchy.TEMP;
            case ARGUMENTS:
                field = obj;
                if (field instanceof TypeHierarchy.A) {
                    return inlinee(field);
                }
                return TypeHierarchy.TEMP;
            case PARAMETERS:
                if (obj instanceof TypeHierarchy.A) {
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
        if (orig instanceof TypeHierarchy.A) {
            Asserts.assertEquals(result, orig.m(), "Results are not equal for TypeHierarchy.A");
        } else {
            Asserts.assertEquals(result, TypeHierarchy.TEMP, "Result differs from expected");
        }
    }
}
