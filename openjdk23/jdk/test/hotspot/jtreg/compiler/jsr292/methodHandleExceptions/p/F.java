/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package p;

/**
 * Test class -- implements I, which provides default for m, but this class
 * redeclares it so that all its non-overriding descendants should call its
 * method instead (with no error, assuming no descendant monkey business, which
 * of course is NOT usually the case in this test).
 *
 * Note that m is final -- one form of monkey business is attempting to redefine
 * m.
 *
 */
public abstract class F implements I {
       final public int m() {
           return 2;
       }
}
