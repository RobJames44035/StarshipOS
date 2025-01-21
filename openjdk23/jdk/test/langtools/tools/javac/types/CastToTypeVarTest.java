/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8144832
 * @summary cast conversion fails when converting a type-variable to primitive type
 * @compile -Werror -Xlint:all CastToTypeVarTest.java
 */

public class CastToTypeVarTest<X, Y extends X> {
    void foo(Y y) {
        X x = (X)y;
    }
}
