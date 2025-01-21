/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.vm.lambda.invoke;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 * evaluates invocation costs.
 *
 * @author Sergey Kuksenko (sergey.kuksenko@oracle.com)
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class Function0 {

    public interface FunctionI {
        int foo();
    }

    public interface FunctionL {
        Integer foo();
    }

    private static int valueI = 40002; // bypass Integer.cache
    private static Integer valueL = Integer.valueOf(valueI);

    public int fooInstanceI() {
        return valueI;
    }

    public static int fooStaticI() {
        return valueI;
    }

    public Integer fooInstanceL() {
        return valueL;
    }

    public static Integer fooStaticL() {
        return valueL;
    }

    @Benchmark
    public int baselineI() {
        return fooInstanceI();
    }

    @Benchmark
    public Integer baselineL() {
        return fooInstanceL();
    }

    public final FunctionI anonymI =
            new FunctionI() {
                @Override
                public int foo() {
                    return valueI;
                }
            };

    public final FunctionL anonymL =
            new FunctionL() {
                @Override
                public Integer foo() {
                    return valueL;
                }
            };

    @Benchmark
    public int innerI() {
        return anonymI.foo();
    }

    @Benchmark
    public Integer innerL() {
        return anonymL.foo();
    }

    public final FunctionI lambdaI = () -> valueI;

    public final FunctionL lambdaL = () -> valueL;

    @Benchmark
    public int lambdaI() {
        return lambdaI.foo();
    }

    @Benchmark
    public Integer lambdaL() {
        return lambdaL.foo();
    }

    public final FunctionI mref_I2I  = Function0::fooStaticI;
    public final FunctionI mref_I2I_bound = this::fooInstanceI;

    public final FunctionL mref_I2L  = Function0::fooStaticI;
    public final FunctionL mref_I2L_bound = this::fooInstanceI;

    public final FunctionI mref_L2I  = Function0::fooStaticL;
    public final FunctionI mref_L2I_bound = this::fooInstanceL;

    public final FunctionL mref_L2L  = Function0::fooStaticL;
    public final FunctionL mref_L2L_bound = this::fooInstanceL;

    // mref naming
    // sig1_sig2 where:
    // sig1 - signature of the method referenced by method ref
    // sig2 - FuntionalInterface signature

    @Benchmark
    public int mrefI_I() {
        return mref_I2I.foo();
    }

    @Benchmark
    public int mref_bndI_I() {
        return mref_I2I_bound.foo();
    }

    @Benchmark
    public Integer mrefI_L() {
        return mref_I2L.foo();
    }

    @Benchmark
    public Integer mref_bndI_L() {
        return mref_I2L_bound.foo();
    }

    @Benchmark
    public int mrefL_I() {
        return mref_L2I.foo();
    }

    @Benchmark
    public int mref_bndL_I() {
        return mref_L2I_bound.foo();
    }

    @Benchmark
    public Integer mrefL_L() {
        return mref_L2L.foo();
    }

    @Benchmark
    public Integer mref_bndL_L() {
        return mref_L2L_bound.foo();
    }


}

