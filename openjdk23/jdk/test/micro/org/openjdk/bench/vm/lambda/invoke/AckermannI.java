/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.vm.lambda.invoke;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;
import java.util.function.IntBinaryOperator;

/**
 * evaluates invocation costs in case of long recursive chains
 *
 * @author Sergey Kuksenko (sergey.kuksenko@oracle.com)
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class AckermannI {

    // ackermann(1,1748)+ ackermann(2,1897)+ ackermann(3,8); == 9999999 calls
    public static final int Y1 = 1748;
    public static final int Y2 = 1897;
    public static final int Y3 = 8;

    public static int ack(int x, int y) {
        return x == 0 ?
                y + 1 :
                (y == 0 ?
                        ack(x - 1, 1) :
                        ack(x - 1, ack(x, y - 1)));
    }

    @Benchmark
    @OperationsPerInvocation(9999999)
    public int func() {
        return ack(1, Y1) + ack(2, Y2) + ack(3, Y3);
    }

    public static final IntBinaryOperator inner_ack =
            new IntBinaryOperator() {
                @Override
                public int applyAsInt(int x, int y) {
                    return x == 0 ?
                            y + 1 :
                            (y == 0 ?
                                    inner_ack.applyAsInt(x - 1, 1) :
                                    inner_ack.applyAsInt(x - 1, inner_ack.applyAsInt(x, y - 1)));

                }
            };

    @Benchmark
    @OperationsPerInvocation(9999999)
    public int inner() {
        return inner_ack.applyAsInt(1, Y1) + inner_ack.applyAsInt(2, Y2) + inner_ack.applyAsInt(3, Y3);
    }

    public static final IntBinaryOperator lambda_ack =
            (x, y) -> x == 0 ?
                    y + 1 :
                    (y == 0 ?
                            AckermannI.lambda_ack.applyAsInt(x - 1, 1) :
                            AckermannI.lambda_ack.applyAsInt(x - 1, AckermannI.lambda_ack.applyAsInt(x, y - 1)));


    @Benchmark
    @OperationsPerInvocation(9999999)
    public int lambda() {
        return lambda_ack.applyAsInt(1, Y1) + lambda_ack.applyAsInt(2, Y2) + lambda_ack.applyAsInt(3, Y3);
    }

    public static final IntBinaryOperator mref_ack = AckermannI::mref_ack_helper;

    public static int mref_ack_helper(int x, int y) {
        return x == 0 ?
                y + 1 :
                (y == 0 ?
                        mref_ack.applyAsInt(x - 1, 1) :
                        mref_ack.applyAsInt(x - 1, mref_ack.applyAsInt(x, y - 1)));
    }

    @Benchmark
    @OperationsPerInvocation(9999999)
    public int mref() {
        return mref_ack.applyAsInt(1, Y1) + mref_ack.applyAsInt(2, Y2) + mref_ack.applyAsInt(3, Y3);
    }

}

