/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test 8275233
 * @summary Incorrect line number reported in exception stack trace thrown from a lambda expression
 * @compile/ref=DeduplicationDebugInfo.out           -XDrawDiagnostics -XDdebug.dumpLambdaToMethodDeduplication -g:none        DeduplicationDebugInfo.java
 * @compile/ref=DeduplicationDebugInfo_none.out      -XDrawDiagnostics -XDdebug.dumpLambdaToMethodDeduplication                DeduplicationDebugInfo.java
 * @compile/ref=DeduplicationDebugInfo_none.out      -XDrawDiagnostics -XDdebug.dumpLambdaToMethodDeduplication -g:lines       DeduplicationDebugInfo.java
 * @compile/ref=DeduplicationDebugInfo_none.out      -XDrawDiagnostics -XDdebug.dumpLambdaToMethodDeduplication -g:vars        DeduplicationDebugInfo.java
 * @compile/ref=DeduplicationDebugInfo_none.out      -XDrawDiagnostics -XDdebug.dumpLambdaToMethodDeduplication -g:lines,vars  DeduplicationDebugInfo.java
 */

import java.util.function.Function;

class DeduplicationDebugInfoTest {
    void f() {
        Function<Object, Integer> f = x -> x.hashCode();
        Function<Object, Integer> g = x -> x.hashCode();
    }
}
