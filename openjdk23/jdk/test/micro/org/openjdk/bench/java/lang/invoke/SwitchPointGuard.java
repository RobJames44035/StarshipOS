/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.lang.invoke;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.lang.invoke.*;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark assessing SwitchPoint performance.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class SwitchPointGuard {

    /*
     * Implementation notes:
     *   - this test asserts SwitchPoint performance in both valid and invalid cases
     *   - this test does not assert invalidation performance (hard to do with irreversible SwitchPoint)
     *   - raw baseline gives the idea for MethodHandle invocation cost
     *   - CS baseline gives the idea for additional dereference cost
     */

    private MethodHandle body1, body2;
    private int i;
    private SwitchPoint sw1, sw2;
    private CallSite cs;
    private MethodHandle guard1, guard2;

    @Setup
    public void setup() throws NoSuchMethodException, IllegalAccessException {
        sw1 = new SwitchPoint();
        sw2 = new SwitchPoint();
        SwitchPoint.invalidateAll(new SwitchPoint[]{sw2});
        body1 = MethodHandles.lookup().findVirtual(SwitchPointGuard.class, "body1", MethodType.methodType(int.class, int.class));
        body2 = MethodHandles.lookup().findVirtual(SwitchPointGuard.class, "body2", MethodType.methodType(int.class, int.class));
        guard1 = sw1.guardWithTest(body1, body2);
        guard2 = sw2.guardWithTest(body1, body2);
        cs = new MutableCallSite(body1);
    }

    @Benchmark
    public void baselineRaw() throws Throwable {
        i = (int) body1.invoke(this, i);
    }

    @Benchmark
    public void baselineCS() throws Throwable {
        i = (int) cs.getTarget().invoke(this, i);
    }

    @Benchmark
    public void testValid() throws Throwable {
        i = (int) guard1.invoke(this, i);
    }

    @Benchmark
    public void testInvalid() throws Throwable {
        i = (int) guard2.invoke(this, i);
    }

    public int body1(int i) {
        return i + 1;
    }

    public int body2(int i) {
        return i + 1;
    }


}
