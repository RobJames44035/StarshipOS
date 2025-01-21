/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

// key: compiler.err.varargs.invalid.trustme.anno
// key: compiler.misc.varargs.trustme.on.non.varargs.accessor

record R(@SafeVarargs String... s) {}
