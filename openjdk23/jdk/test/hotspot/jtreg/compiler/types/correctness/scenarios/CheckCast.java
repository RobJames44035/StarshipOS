/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.types.correctness.scenarios;

import compiler.types.correctness.hierarchies.TypeHierarchy;
import jdk.test.lib.Asserts;

import java.util.Objects;

/**
 * Checkcast scenario
 * @param <T> profiling parameter
 */
public class CheckCast<T extends TypeHierarchy.I> extends Scenario<T, Integer> {
    public CheckCast(ProfilingType profilingType, TypeHierarchy<? extends T, ? extends T> hierarchy) {
        super("CheckCast", profilingType, hierarchy);
    }

    /**
     * Returns type profiling.
     * @param obj is a profiled parameter for the test
     * @return parameter casted to the type R
     */
    @Override
    public Integer run(T obj) {
        switch (profilingType) {
            case RETURN:
                T t = collectReturnType(obj);
                if (t != null) {
                    return t.m();
                }
                return null;
            case ARGUMENTS:
                field = obj;
                if (field != null) {
                    return field.m();
                }
                return null;
            case PARAMETERS:
                if (obj != null) {
                    return obj.m();
                }
                return null;
        }
        throw new RuntimeException("Should not reach here");
    }

    @Override
    public void check(Integer result, T orig) {
        if (result != null || orig != null) {
            Objects.requireNonNull(result);
            Objects.requireNonNull(orig);
            Asserts.assertEquals(result, orig.m(), "Results mismatch");
        }
    }
}
