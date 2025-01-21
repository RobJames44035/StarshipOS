/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.java.lang;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
public class DoubleClassCheck {

    static final int BUFFER_SIZE = 1024;
    double[] inputs;
    boolean[] storeOutputs;
    int[] cmovOutputs;
    int[] branchOutputs;

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    static int call() {
        return 1;
    }

    @Setup
    public void setup() {
        storeOutputs = new boolean[BUFFER_SIZE];
        cmovOutputs = new int[BUFFER_SIZE];
        branchOutputs = new int[BUFFER_SIZE];
        inputs = new double[BUFFER_SIZE];
        RandomGenerator rng = RandomGeneratorFactory.getDefault().create(0);
        double input;
        for (int i = 0; i < BUFFER_SIZE; i++) {
            if (i % 5 == 0) {
                input = (i%2 == 0) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }
            else if (i % 3 == 0) input = Double.NaN;
            else input = rng.nextDouble();
            inputs[i] = input;
        }
    }

    @Benchmark
    @OperationsPerInvocation(BUFFER_SIZE)
    public void testIsInfiniteStore() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            storeOutputs[i] = Double.isInfinite(inputs[i]);
        }
    }


    @Benchmark
    @OperationsPerInvocation(BUFFER_SIZE)
    public void testIsInfiniteCMov() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            cmovOutputs[i] = Double.isInfinite(inputs[i]) ? 9 : 7;
        }
    }


    @Benchmark
    @OperationsPerInvocation(BUFFER_SIZE)
    public void testIsInfiniteBranch() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            cmovOutputs[i] = Double.isInfinite(inputs[i]) ? call() : 7;
        }
    }

    @Benchmark
    @OperationsPerInvocation(BUFFER_SIZE)
    public void testIsFiniteStore() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            storeOutputs[i] = Double.isFinite(inputs[i]);
        }
    }


    @Benchmark
    @OperationsPerInvocation(BUFFER_SIZE)
    public void testIsFiniteCMov() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            cmovOutputs[i] = Double.isFinite(inputs[i]) ? 9 : 7;
        }
    }


    @Benchmark
    @OperationsPerInvocation(BUFFER_SIZE)
    public void testIsFiniteBranch() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            cmovOutputs[i] = Double.isFinite(inputs[i]) ? call() : 7;
        }
    }
}
