/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package org.openjdk.bench.java.security;

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

import java.security.Permissions;
import java.security.UnresolvedPermission;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark measuring Permissions.implies
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
@State(Scope.Thread)
public class PermissionsImplies {

    private Permissions withPermission = new Permissions();
    private Permissions withoutPermission = new Permissions();
    private Permissions withUnresolvedPermission = new Permissions();

    private RuntimePermission permission = new RuntimePermission("exitVM");

    @Setup
    public void setup() {
        withPermission.add(permission);
        withUnresolvedPermission.add(permission);
        withUnresolvedPermission.add(new UnresolvedPermission("java.lang.FilePermission", "foo", "write", null));
    }

    @Benchmark
    public boolean withoutPermission() {
        return withoutPermission.implies(permission);
    }

    @Benchmark
    public boolean withPermission() {
        return withPermission.implies(permission);
    }

    @Benchmark
    public boolean withUnresolvedPermission() {
        return withUnresolvedPermission.implies(permission);
    }
}
