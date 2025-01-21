/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package org.openjdk.bench.java.security;

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
import sun.security.util.DisabledAlgorithmConstraints;

import java.security.AlgorithmConstraints;
import java.security.CryptoPrimitive;
import java.util.concurrent.TimeUnit;
import java.util.EnumSet;
import java.util.Set;

import static sun.security.util.DisabledAlgorithmConstraints.PROPERTY_TLS_DISABLED_ALGS;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 3, jvmArgs = {"--add-exports", "java.base/sun.security.util=ALL-UNNAMED"})
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
public class AlgorithmConstraintsPermits {

    AlgorithmConstraints tlsDisabledAlgConstraints;
    Set<CryptoPrimitive> primitives = EnumSet.of(CryptoPrimitive.KEY_AGREEMENT);

    @Param({"SSLv3", "DES", "NULL", "TLS1.3"})
    String algorithm;

    @Setup
    public void setup() {
        tlsDisabledAlgConstraints = new DisabledAlgorithmConstraints(PROPERTY_TLS_DISABLED_ALGS);
    }

    @Benchmark
    public boolean permits() {
        return tlsDisabledAlgConstraints.permits(primitives, algorithm, null);
    }
}

