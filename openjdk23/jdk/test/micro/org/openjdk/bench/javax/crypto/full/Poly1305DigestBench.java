/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.javax.crypto.full;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.annotations.Measurement;
import java.nio.ByteBuffer;

@Measurement(iterations = 3, time = 10)
@Warmup(iterations = 3, time = 10)
@Fork(value = 1, jvmArgs = {"--add-opens", "java.base/com.sun.crypto.provider=ALL-UNNAMED"})
public class Poly1305DigestBench extends CryptoBase {
    public static final int SET_SIZE = 128;

    @Param({"64", "256", "1024", "" + 16*1024, "" + 1024*1024})
    int dataSize;

    private byte[][] data;
    int index = 0;
    private static MethodHandle polyEngineInit, polyEngineUpdate, polyEngineUpdateBuf, polyEngineFinal;
    private static Object polyObj;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            Class<?> polyClazz = Class.forName("com.sun.crypto.provider.Poly1305");
            Constructor<?> constructor = polyClazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            polyObj = constructor.newInstance();

            Method m = polyClazz.getDeclaredMethod("engineInit", Key.class, AlgorithmParameterSpec.class);
            m.setAccessible(true);
            polyEngineInit = lookup.unreflect(m);

            m = polyClazz.getDeclaredMethod("engineUpdate", byte[].class, int.class, int.class);
            m.setAccessible(true);
            polyEngineUpdate = lookup.unreflect(m);

            m = polyClazz.getDeclaredMethod("engineUpdate", ByteBuffer.class);
            m.setAccessible(true);
            polyEngineUpdateBuf = lookup.unreflect(m);

            m = polyClazz.getDeclaredMethod("engineDoFinal");
            m.setAccessible(true);
            polyEngineFinal = lookup.unreflect(m);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    @Setup
    public void setup() throws Throwable {
        setupProvider();
        data = fillRandom(new byte[SET_SIZE][dataSize]);
        byte[] d = data[0];
        polyEngineInit.invoke(polyObj, new SecretKeySpec(d, 0, 32, "Poly1305"), null);
    }

    @Benchmark
    public byte[] digestBytes() {
        try {
            byte[] d = data[index];
            index = (index +1) % SET_SIZE;
            polyEngineInit.invoke(polyObj, new SecretKeySpec(d, 0, 32, "Poly1305"), null);
            polyEngineUpdate.invoke(polyObj, d, 0, d.length);
            return (byte[])polyEngineFinal.invoke(polyObj);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    @Benchmark
    public void updateBytes() {
        try {
            byte[] d = data[index];
            // index = (index +1) % SET_SIZE;
            polyEngineUpdate.invoke(polyObj, d, 0, d.length);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    @Benchmark
    public byte[] digestBuffer() {
        try {
            byte[] d = data[index];
            index = (index +1) % SET_SIZE;
            polyEngineInit.invoke(polyObj, new SecretKeySpec(d, 0, 32, "Poly1305"), null);
            polyEngineUpdateBuf.invoke(polyObj, ByteBuffer.wrap(d, 0, d.length));
            return (byte[])polyEngineFinal.invoke(polyObj);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }
}
