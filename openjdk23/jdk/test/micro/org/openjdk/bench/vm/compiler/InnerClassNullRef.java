/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.vm.compiler;

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

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class InnerClassNullRef {

    class Pickles {
        int count;

        public Pickles(int x) {
            count = x;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int x) {
            count = x;
        }
    }

    class BurgerStand {
        public void makeBurgers() {
            if (pickles != null) {
                // add a pickle
                pickles.setCount(pickles.getCount() - 1);
            }
        }
    }

    class BurgerStandNull {
        public void makeBurgers() {
            if (picklesNull != null) {
                // add a pickle
                picklesNull.setCount(picklesNull.getCount() - 1);
            }
        }
    }

    private BurgerStand bs;
    private BurgerStandNull bsNull;
    private Pickles pickles;
    private Pickles picklesNull;

    @Setup
    public void setup() {
        bs = new BurgerStand();
        bsNull = new BurgerStandNull();
        pickles = new Pickles(42);
    }

    @Benchmark
    public void refIsNull() {
        bsNull.makeBurgers();
    }

    @Benchmark
    public void refIsNotNull() {
        bs.makeBurgers();
    }
}
