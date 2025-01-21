/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4042259
 * @summary Check that a class can inherit multiple methods with conflicting throws clauses.
 * @author maddox
 *
 * @run compile/fail ThrowsIntersection_4.java
 */

package ThrowsIntersection_4;

// Note:  This is the test that actually failed for 4042259.  The others are for completeness.

class Ex1 extends Exception {}
class Ex2 extends Exception {}

interface a {
  int m1() throws Ex1;
}

interface b {
  int m1() throws Ex2;
}

// Should fail regardless of order in which superinterfaces appear.
abstract class c implements a, b {}
//abstract class c implements b, a {}

class d extends c  {
  public int m1() throws Ex1 {
    return 1;
  }
}
