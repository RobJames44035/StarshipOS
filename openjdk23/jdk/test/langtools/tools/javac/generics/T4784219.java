/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4784219
 * @summary generics: compiler crash after diagnostic
 * @author gafter
 *
 * @compile  T4683314.java
 */

public class T4784219 {
    static <T, A> T<A, A> genericNew() {
        genericNew();
    }
}
