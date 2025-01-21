/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

// key: compiler.err.invalid.canonical.constructor.in.record
// key: compiler.misc.canonical.must.not.have.stronger.access
// key: compiler.misc.canonical

public record CanonicalCantHaveStrongerAccessPrivileges() {
    private CanonicalCantHaveStrongerAccessPrivileges {}
}

