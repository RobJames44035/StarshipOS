/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import jdk.incubator.vector.*;

import java.util.stream.IntStream;

/**
 * @test
 * @bug 8309727
 * @modules jdk.incubator.vector
 * @run main VectorRuns
 */

public class VectorRuns {

    public static void main(String[] args) {

        for (int i = 1; i < 1024; i++) {
            int[] a = IntStream.range(0, 1024).toArray();
            a[i] = -1;
            countRunAscending(a);

            assertEquals(countRunAscending(a), i);
            assertEquals(countRunAscendingVector(a), i);
        }
    }

    static void assertEquals(Object a, Object b) {
        if (!a.equals(b)) throw new AssertionError(a + " " + b);
    }

    // Count run of a[0] >  a[1] >  a[2] >  ...
    static int countRunAscending(int[] a) {
        int r = 1;
        if (r >= a.length)
            return a.length;

        while (r < a.length && a[r - 1] <= a[r]) {
            r++;
        }
        return r;
    }


    static int countRunAscendingVector(int[] a) {
        VectorSpecies<Integer> species = IntVector.SPECIES_256;

        int r = 1;
        if (r >= a.length)
            return a.length;

        int length = species.loopBound(a.length);
        if (length == a.length) length -= species.length();
        while (r < length) {
            IntVector vl = IntVector.fromArray(species, a, r - 1);
            IntVector vr = IntVector.fromArray(species, a, r);
            VectorMask<Integer> m = vl.compare(VectorOperators.GT, vr);
            if (m.anyTrue())
                return r + Long.numberOfTrailingZeros(m.toLong());
            r += species.length();
        }

        while (r < a.length && a[r - 1] <= a[r]) {
            r++;
        }
        return r;
    }
}
