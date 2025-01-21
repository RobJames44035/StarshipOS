/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.lang.reflect.proxy;

import org.openjdk.jmh.annotations.*;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 2)
@State(Scope.Benchmark)
public class ProxyBench {
    Interfaze implClass, implProxy;
    PpInterfaze ppImplClass, ppImplProxy;
    int a, b, c;

    @Setup(Level.Trial)
    public void setup() {
        implClass = new Clazz();
        implProxy = (Interfaze) Proxy.newProxyInstance(
                Interfaze.class.getClassLoader(),
                new Class<?>[]{Interfaze.class},
                new IHandler()
        );
        ppImplClass = new PpClazz();
        ppImplProxy = (PpInterfaze) Proxy.newProxyInstance(
                PpInterfaze.class.getClassLoader(),
                new Class<?>[]{PpInterfaze.class},
                new IHandler()
        );

        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        a = tlr.nextInt();
        b = tlr.nextInt();
        c = tlr.nextInt();
    }

    @Benchmark
    public int implClass() {
        return implClass.sum(a, b, c);
    }

    @Benchmark
    public int implProxy() {
        return implProxy.sum(a, b, c);
    }

    @Benchmark
    public int ppImplClass() {
        return ppImplClass.sum(a, b, c);
    }

    @Benchmark
    public int ppImplProxy() {
        return ppImplProxy.sum(a, b, c);
    }

    public interface Interfaze {
        default int sum(int a, int b, int c) {
            return a + b + c;
        }
    }

    static class Clazz implements Interfaze {
        @Override
        public int sum(int a, int b, int c) {
            return Interfaze.super.sum(a, b, c);
        }
    }

    interface PpInterfaze {
        default int sum(int a, int b, int c) {
            return a + b + c;
        }
    }

    static class PpClazz implements PpInterfaze {
        @Override
        public int sum(int a, int b, int c) {
            return PpInterfaze.super.sum(a, b, c);
        }
    }

    static class IHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return InvocationHandler.invokeDefault(proxy, method, args);
        }
    }
}