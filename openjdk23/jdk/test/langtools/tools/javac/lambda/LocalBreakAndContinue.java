/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that local break/continue is allowed in lambda expressions
 * @author  Maurizio Cimadamore
 * @compile LocalBreakAndContinue.java
 */

class LocalBreakAndContinue {

    static interface SAM {
       void m();
    }

    SAM s1 = ()-> { while (true) break; };
    SAM s2 = ()-> { while (true) continue; };
}
