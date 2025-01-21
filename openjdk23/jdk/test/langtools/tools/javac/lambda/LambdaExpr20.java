/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that default super call from lambda expression is compiled successfully
 * @compile LambdaExpr20.java
 */

class LambdaExpr20 {

    interface K {
        default void m() { }
    }

    static class Test implements K {
        @Override
        public void m() {
            Runnable r = () -> { K.super.m(); };
            r.run();
        }
    }
}
