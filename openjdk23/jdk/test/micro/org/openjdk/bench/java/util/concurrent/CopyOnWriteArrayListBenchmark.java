/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package org.openjdk.bench.java.util.concurrent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class CopyOnWriteArrayListBenchmark {

    private static byte[] getSerializedBytes(CopyOnWriteArrayList<?> list) throws IOException {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(bytesOut);
        objectOut.writeObject(list);

        objectOut.close();
        return bytesOut.toByteArray();
    }

    private Collection<Object> emptyCollection = new ArrayList<>();
    private Object[] emptyArray = new Object[0];

    private Collection<Object> oneItemCollection = Arrays.asList("");
    private Object[] oneItemArray = new Object[] { "" };

    private CopyOnWriteArrayList<?> emptyInstance = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<?> oneItemInstance = new CopyOnWriteArrayList<>(oneItemArray);

    private byte[] emptyInstanceBytes;
    private byte[] oneInstanceBytes;

    public CopyOnWriteArrayListBenchmark() {
        try {
            emptyInstanceBytes = getSerializedBytes(emptyInstance);
            oneInstanceBytes = getSerializedBytes(oneItemInstance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Benchmark
    public void clear() {
        // have to create a new instance on each execution
        ((CopyOnWriteArrayList<?>) oneItemInstance.clone()).clear();
    }

    @Benchmark
    public void clearEmpty() {
        emptyInstance.clear();
    }

    @Benchmark
    public CopyOnWriteArrayList<?> createInstanceArray() {
        return new CopyOnWriteArrayList<>(oneItemArray);
    }

    @Benchmark
    public CopyOnWriteArrayList<?> createInstanceArrayEmpty() {
        return new CopyOnWriteArrayList<>(emptyArray);
    }

    @Benchmark
    public CopyOnWriteArrayList<?> createInstanceCollection() {
        return new CopyOnWriteArrayList<>(oneItemCollection);
    }

    @Benchmark
    public CopyOnWriteArrayList<?> createInstanceCollectionEmpty() {
        return new CopyOnWriteArrayList<>(emptyCollection);
    }

    @Benchmark
    public CopyOnWriteArrayList<?> createInstanceDefault() {
        return new CopyOnWriteArrayList<Object>();
    }

    @Benchmark
    public CopyOnWriteArrayList<?> readInstance() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(oneInstanceBytes))) {
            return (CopyOnWriteArrayList<?>) objIn.readObject();
        }
    }

    @Benchmark
    public CopyOnWriteArrayList<?> readInstanceEmpty() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(emptyInstanceBytes))) {
            return (CopyOnWriteArrayList<?>) objIn.readObject();
        }
    }

    @Benchmark
    public CopyOnWriteArrayList<?> removeObjectLastRemaining() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("");
        list.remove("");
        return list;
    }

    @Benchmark
    public CopyOnWriteArrayList<?> removeIndexLastRemaining() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("");
        list.remove(0);
        return list;
    }
    @Benchmark
    public CopyOnWriteArrayList<?> removeObject() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("");
        list.add("a");
        list.remove("");
        return list;
    }

    @Benchmark
    public CopyOnWriteArrayList<?> remove() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("");
        list.add("a");
        list.remove(0);
        return list;
    }
}
