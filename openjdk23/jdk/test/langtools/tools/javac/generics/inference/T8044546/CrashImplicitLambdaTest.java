/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8044546
 * @summary Crash on faulty reduce/lambda
 * @compile CrashImplicitLambdaTest.java
 */

abstract class CrashImplicitLambdaTest {
    boolean foo() {
        return bar(true, a -> {});
    }

    abstract <T1> T1 bar(T1 t1, S<T1> s);

    interface S<S1> {
        void baz(S1 s1);
    }
}
