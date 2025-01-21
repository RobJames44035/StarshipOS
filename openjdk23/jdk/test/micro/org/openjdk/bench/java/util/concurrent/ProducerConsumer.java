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
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.infra.ThreadParams;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Tests the different blocking queues in the java.util.concurrent package.
 * The tests are done with a single producer and a variable number of consumers.
 * The tests are created from Doug Lea's concurrent test suite.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
public class ProducerConsumer {

    @Param("100") // Will be expanded to at least the number of threads used
    private int capacity;

    @Param
    private QueueType type;

    private BlockingQueue<Integer> q;
    private Producer prod;

    @Setup
    public void prepare(ThreadParams params) {
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

        prod = new Producer(q);
        prod.start();
    }

    @TearDown
    public void teardown() {
        prod.halt();
    }

    @Benchmark
    public void test() {
        try {
            int last = -1;
            int v = q.take();
            if (v < last) {
                throw new Error("Out-of-Order transfer");
            }
            Blackhole.consumeCPU(10);
        } catch (Exception ie) {
        }
    }

    public enum QueueType {
        LBQ,
        ABQ_NF,
        ABQ_F,
        PBQ,
    }

    private class Producer extends Thread {
        private final BlockingQueue<Integer> queue;
        private int i = 0;
        private volatile boolean running;

        public Producer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            running = true;
            try {
                while (running) {
                    queue.put(i++);
                }
            } catch (Exception ie) {
            }
        }

        public void halt() {
            running = false;
            this.interrupt();
        }
    }
}
