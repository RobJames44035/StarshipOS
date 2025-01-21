/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.types.correctness.scenarios;

import compiler.types.correctness.hierarchies.TypeHierarchy;
import jdk.test.lib.Asserts;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *  Base class for array scenarios
 */
public abstract class ArrayScenario extends Scenario<TypeHierarchy.I, TypeHierarchy.I> {
    protected final TypeHierarchy.I[] array;
    protected final TypeHierarchy.I[][] matrix;

    protected ArrayScenario(String name, ProfilingType profilingType,
                            TypeHierarchy<? extends TypeHierarchy.I, ? extends TypeHierarchy.I> hierarchy) {
        super(name, profilingType, hierarchy);
        final int x = 20;
        final int y = 10;

        TypeHierarchy.I prof = hierarchy.getM();
        TypeHierarchy.I confl = hierarchy.getN();

        this.array = (TypeHierarchy.I[]) Array.newInstance(hierarchy.getClassM(), y);
        Arrays.fill(array, prof);

        this.matrix = (TypeHierarchy.I[][]) Array.newInstance(hierarchy.getClassM(), x, y);
        for (int i = 0; i < x; i++) {
            this.matrix[i] = this.array;
        }

        Asserts.assertEquals(array.length, matrix[0].length, "Invariant");
    }

    @Override
    public boolean isApplicable() {
        return hierarchy.getClassM().isAssignableFrom(hierarchy.getClassN());
    }

    @Override
    public void check(TypeHierarchy.I res, TypeHierarchy.I orig) {
        Asserts.assertEquals(res, orig, "Check failed");
    }
}
