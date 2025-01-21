/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that binary expression in lambda expression is parsed correctly
 * @author  Maurizio Cimadamore
 * @compile LambdaExpr05.java
 */

class LambdaExpr05 {

    interface SAM { int foo(int i); }

    SAM s1 = i -> i * 2;
    SAM s2 = i -> 2 * i;
}
