/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase runtime/jbe/dead/dead02.
 * VM Testbase keywords: [quick, runtime]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm vm.compiler.jbe.dead.dead02.dead02
 */

package vm.compiler.jbe.dead.dead02;

/* -- Test the elimination of dead assignment to static variables
In the example below, the value assigned to i is never used, all dead stores to static global can be eliminated, and the last return statement in f() is unreachable; Both dead/unused stores and unreachable statement can be eliminated.

 */

public class dead02 {
  static int global;
  static int i;

  public static void main(String args[]) {
    dead02 dce = new dead02();

    System.out.println("f()="+dce.f()+"; fopt()="+dce.fopt());
    if (dce.f() == dce.fopt()) {
      System.out.println("Test dead02 Passed.");
    } else {
      throw new Error("Test dead02 Failed: f()=" + dce.f() + " != fopt()=" + dce.fopt());
    }
  }


  int f() {

    i = 1;           /* dead store */
    global = 8;      /* dead store */
    global = 7;      /* dead store */
    global = 6;      /* dead store */
    global = 5;      /* dead store */
    global = 4;      /* dead store */
    global = 3;      /* dead store */
    global = 2;      /* dead store */
    global = 1;      /* dead store */
    global = 0;      /* dead store */
    global = -1;     /* dead store */
    global = -2;     /* dead store */

    i = 1;           /* dead store */
    global = 8;      /* dead store */
    global = 7;      /* dead store */
    global = 6;      /* dead store */
    global = 5;      /* dead store */
    global = 4;      /* dead store */
    global = 3;      /* dead store */
    global = 2;      /* dead store */
    global = 1;      /* dead store */
    global = 0;      /* dead store */
    global = -1;     /* dead store */
    global = -2;     /* dead store */

    i = 1;           /* dead store */
    global = 8;      /* dead store */
    global = 7;      /* dead store */
    global = 6;      /* dead store */
    global = 5;      /* dead store */
    global = 4;      /* dead store */
    global = 3;      /* dead store */
    global = 2;      /* dead store */
    global = 1;      /* dead store */
    global = 0;      /* dead store */
    global = -1;     /* dead store */
    global = -2;

    if (Math.abs(global) >= 0)  /* always true */
      return global;
    return global;   /* unreachable */
  }

  // Code fragment after dead code elimination
  int fopt() {

    global = -2;
    return global;
  }
}
