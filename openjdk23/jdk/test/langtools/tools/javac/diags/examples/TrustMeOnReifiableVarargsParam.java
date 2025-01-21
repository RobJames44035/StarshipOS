/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.varargs.redundant.trustme.anno
// key: compiler.misc.varargs.trustme.on.reifiable.varargs
// options: -Xlint:varargs

class TrustMeOnReifiableVarargsParam {
    @SafeVarargs static void m(String... args) { }
}
