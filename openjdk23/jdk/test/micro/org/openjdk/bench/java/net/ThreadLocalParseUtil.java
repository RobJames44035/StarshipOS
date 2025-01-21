/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package org.openjdk.bench.java.net;

import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Warmup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static java.lang.invoke.MethodType.methodType;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(value = 1, jvmArgs = "--add-exports=java.base/sun.net.www=ALL-UNNAMED")
public class ThreadLocalParseUtil {

    private static final MethodHandle MH_DECODE;
    private static final MethodHandle MH_TO_URI;

    static {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            Class<?> c = Class.forName("sun.net.www.ParseUtil");
            MH_DECODE = lookup.findStatic(c, "decode", methodType(String.class, String.class));
            MH_TO_URI = lookup.findStatic(c, "toURI", methodType(URI.class, URL.class));
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @Benchmark
    public String decodeTest() throws Throwable {
        return (String) MH_DECODE.invokeExact("/xyz/\u00A0\u00A0");
    }

    @Benchmark
    public URI appendEncodedTest() throws Throwable {
        @SuppressWarnings("deprecation")
        URL url = new URL("https://example.com/xyz/abc/def?query=#30");
        return (URI) MH_TO_URI.invokeExact(url);
    }
}
