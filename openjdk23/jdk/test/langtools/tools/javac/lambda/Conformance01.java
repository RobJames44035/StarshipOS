/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  lambda compiler regression with uninferred type-variables in generic constructor call
 * @compile Conformance01.java
 */

class Conformance01 {
    <T1, T2> Conformance01(T1 t) { }

    Conformance01 c01 = new Conformance01(null);
}
