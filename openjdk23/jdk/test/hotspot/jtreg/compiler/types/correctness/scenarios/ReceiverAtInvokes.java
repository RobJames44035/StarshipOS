/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.types.correctness.scenarios;

import compiler.types.correctness.hierarchies.TypeHierarchy;
import jdk.test.lib.Asserts;

/**
 * Receiver at invokes profiling and speculation
 *
 * @param <T> parameter to be returned
 */
public class ReceiverAtInvokes<T extends TypeHierarchy.I> extends Scenario<T, Integer> {
    public ReceiverAtInvokes(ProfilingType profilingType,
                             TypeHierarchy<? extends T, ? extends T> hierarchy) {
        super("ReceiverAtInvokes", profilingType, hierarchy);
    }

    @Override
    public boolean isApplicable() {
        return hierarchy.getM() != null && hierarchy.getN() != null;
    }

    /**
     * Receiver profiling
     *
     * @param obj is a profiled parameter for the test
     * @return parameter casted to the type R
     */
    @Override
    public Integer run(T obj) {
        switch (profilingType) {
            case RETURN:
                T t = collectReturnType(obj);
                return inlinee(t);
            case ARGUMENTS:
                field = obj;
                return inlinee(field);
            case PARAMETERS:
                return inlinee(obj);
        }
        throw new RuntimeException("Should not reach here");
    }

    private Integer inlinee(T obj) {
        return obj.m(); // should be inlined
    }

    @Override
    public void check(Integer result, T orig) {
        Asserts.assertEquals(result, orig.m(), "Results mismatch");
    }
}
