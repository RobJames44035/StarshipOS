/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.concurrent;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.infra.ThreadParams;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
public class Queues {

    @Param("100") // Will be expanded to at least the number of threads used
    private int capacity;

    @Param
    private QueueType type;

    public enum QueueType {
        LBQ,
        ABQ_NF,
        ABQ_F,
        PBQ,
    }

    private BlockingQueue<Integer> q;

    @Setup
    public void setup(ThreadParams params) {
        capacity = Math.max(params.getThreadCount(), capacity);

        switch (type) {
            case ABQ_F:
                q = new ArrayBlockingQueue<>(capacity, true);
                break;
            case ABQ_NF:
                q = new ArrayBlockingQueue<>(capacity, false);
                break;
            case LBQ:
                q = new LinkedBlockingQueue<>(capacity);
                break;
            case PBQ:
                q = new PriorityBlockingQueue<>(capacity);
                break;
            default:
                throw new RuntimeException();
        }
    }

    @Benchmark
    public void test() {
        try {
            int l = (int) System.nanoTime();
            Integer item = q.poll();
            if (item != null) {
                Blackhole.consumeCPU(5);
            } else {
                Blackhole.consumeCPU(10);
                while (!q.offer(l)) {
                    Blackhole.consumeCPU(5);
                }
            }
        } catch (Exception ie) {
            throw new Error("iteration failed");
        }
    }

}
