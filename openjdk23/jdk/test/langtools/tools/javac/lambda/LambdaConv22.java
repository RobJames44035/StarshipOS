/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  inner class translator fails with spurious method clash errors
 * @compile LambdaConv22.java
 */

class LambdaConv22<U> {

    interface Factory<T> { T make(); }

    U make() { return null; }

    void test(U u) {
        Factory<U> fu1 = () -> u;
        Factory<U> fu2 = this::make;
    }
}
