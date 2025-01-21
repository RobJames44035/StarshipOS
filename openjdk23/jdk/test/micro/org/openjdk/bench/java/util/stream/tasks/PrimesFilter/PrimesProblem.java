/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream.tasks.PrimesFilter;

import java.util.ArrayList;
import java.util.List;

public class PrimesProblem {

    /**
     * Factors n into its prime factors. If n is prime,
     * the result is a list of length 1 containing n.
     *
     * @param n the number to be factored
     * @return a list of prime factors
     */
    static List<Long> factor(long n) {
        List<Long> flist = new ArrayList<>();

        while (n % 2L == 0) {
            flist.add(2L);
            n /= 2L;
        }

        long divisor = 3L;
        while (n > 1L) {
            long quotient = n / divisor;
            if (n % divisor == 0) {
                flist.add(divisor);
                n = quotient;
            } else if (quotient > divisor) {
                divisor += 2L;
            } else {
                flist.add(n);
                break;
            }
        }

        return flist;
    }

    /**
     * Tests whether n is prime.
     *
     * @param n the number to be tested
     * @return true if n is prime, false if n is composite
     */
    public static boolean isPrime(long n) {
        List<Long> factors = factor(n);
        return (factors.size() == 1);
    }


}
