/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package org.openjdk.bench.java.lang.invoke;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(3)
public class MethodHandlesTableSwitchConstant {

    // Switch combinator test for a single constant input index

    private static final MethodType callType = MethodType.methodType(int.class, int.class);

    private static final MutableCallSite cs = new MutableCallSite(callType);
    private static final MethodHandle target = cs.dynamicInvoker();

    private static final MutableCallSite csInput = new MutableCallSite(MethodType.methodType(int.class));
    private static final MethodHandle targetInput = csInput.dynamicInvoker();

    private static final MethodHandle MH_SUBTRACT;
    private static final MethodHandle MH_DEFAULT;
    private static final MethodHandle MH_PAYLOAD;

    static {
        try {
            MH_SUBTRACT = MethodHandles.lookup().findStatic(MethodHandlesTableSwitchConstant.class, "subtract",
                    MethodType.methodType(int.class, int.class, int.class));
            MH_DEFAULT = MethodHandles.lookup().findStatic(MethodHandlesTableSwitchConstant.class, "defaultCase",
                    MethodType.methodType(int.class, int.class));
            MH_PAYLOAD = MethodHandles.lookup().findStatic(MethodHandlesTableSwitchConstant.class, "payload",
                    MethodType.methodType(int.class, int.class, int.class));
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    // Using batch size since we really need a per-invocation setup
    // but the measured code is too fast. Using JMH batch size doesn't work
    // since there is no way to do a batch-level setup as well.
    private static final int BATCH_SIZE = 1_000_000;

    @Param({
        "5",
        "10",
        "25"
    })
    public int numCases;


    @Param({
        "0",
        "150"
    })
    public int offset;

    @Setup(Level.Trial)
    public void setupTrial() throws Throwable {
        MethodHandle[] cases = IntStream.range(0, numCases)
                .mapToObj(i -> MethodHandles.insertArguments(MH_PAYLOAD, 1, i))
                .toArray(MethodHandle[]::new);
        MethodHandle switcher = MethodHandles.tableSwitch(MH_DEFAULT, cases);
        if (offset != 0) {
            switcher = MethodHandles.filterArguments(switcher, 0, MethodHandles.insertArguments(MH_SUBTRACT, 1, offset));
        }
        cs.setTarget(switcher);

        int input = ThreadLocalRandom.current().nextInt(numCases) + offset;
        csInput.setTarget(MethodHandles.constant(int.class, input));
    }

    private static int payload(int dropped, int constant) {
        return constant;
    }

    private static int subtract(int a, int b) {
        return a - b;
    }

    private static int defaultCase(int x) {
        throw new IllegalStateException();
    }

    @Benchmark
    public void testSwitch(Blackhole bh) throws Throwable {
        for (int i = 0; i < BATCH_SIZE; i++) {
            bh.consume((int) target.invokeExact((int) targetInput.invokeExact()));
        }
    }

}
