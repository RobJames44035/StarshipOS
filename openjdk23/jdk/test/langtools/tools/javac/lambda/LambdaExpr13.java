/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that recursive lambda (through field ref) is accepted in all contexts
 *  but field initialization
 * @compile LambdaExpr13.java
 */

class LambdaExpr13 {

    Runnable ir;
    static Runnable sr;

    { ir = () -> { ir.run(); }; }
    static { sr = () -> { sr.run(); }; }

    static void m1() {
        sr = () -> { sr.run(); };
    }

    void m2() {
        ir = () -> { ir.run(); };
    }
}
