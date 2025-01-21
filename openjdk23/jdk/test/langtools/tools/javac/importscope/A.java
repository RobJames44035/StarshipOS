/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4912075 4979456
 * @summary static import of private field crashes compiler
 * @author gafter
 *
 * @compile A.java B.java
 */

package p1;

public class A {
    public static char c = 'A';
}
