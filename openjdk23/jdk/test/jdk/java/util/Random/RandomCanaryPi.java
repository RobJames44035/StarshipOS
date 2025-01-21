/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.util.Comparator;
import java.util.random.RandomGenerator;
import java.util.random.RandomGenerator.*;
import java.util.random.RandomGeneratorFactory;

/**
 * @test
 * @summary test bit sequences produced by clases that implement interface RandomGenerator
 * @bug 8248862
 * @run main RandomCanaryPi
 * @key randomness
 */

public class RandomCanaryPi {
   static double pi(RandomGenerator rng) {
        int N = 10000000;
        int k = 0;

        for (int i = 0; i < N; i++) {
            double x = rng.nextDouble();
            double y = rng.nextDouble();

            if (x * x + y * y <= 1.0) {
                k++;
            }
        }

        return 4.0 * (double)k / (double)N;
    }

    static int failed = 0;

    public static void main(String[] args) {
        RandomGeneratorFactory.all()
                .sorted(Comparator.comparing(RandomGeneratorFactory::name))
                .forEach(factory -> {
                    RandomGenerator rng = factory.create();
                    double pi = pi(rng);
                    double delta = Math.abs(Math.PI - pi);
                    boolean pass = delta < 1E-2;

                    if (!pass) {
                        System.err.println("Algorithm    = " + factory.name() + " failed");
                        System.err.println("Actual       = " + Math.PI);
                        System.err.println("Monte Carlo  = " + pi);
                        System.err.println("Delta        = " + delta);
                        System.err.println();

                        failed++;
                    }
                });
        if (failed != 0) {
            throw new RuntimeException(failed + " tests failed");
        }
    }
}
