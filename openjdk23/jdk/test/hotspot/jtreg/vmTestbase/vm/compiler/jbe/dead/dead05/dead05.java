/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase runtime/jbe/dead/dead05.
 * VM Testbase keywords: [quick, runtime]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm vm.compiler.jbe.dead.dead05.dead05
 */

package vm.compiler.jbe.dead.dead05;

/* -- Test the elimination of dead assignment to static class field
In the example below, all the values assigned to i in struct except of the last one are never used,thus can be eliminated.

 */

class struct {
    static int i = 6;
}

public class dead05 {

  public static void main(String args[]) {
    dead05 dce = new dead05();

    System.out.println("f()="+dce.f()+"; fopt()="+dce.fopt());
    if (dce.f() == dce.fopt()) {
      System.out.println("Test dead05 Passed.");
    } else {
      throw new Error("Test dead05 Failed: f()=" + dce.f() + " != fopt()=" + dce.fopt());
    }
  }

  int f() {

    struct.i = 1;
    struct.i = 2;
    struct.i = 3;
    struct.i = 4;
    struct.i = 5;
    struct.i = 6;
    struct.i = 7;
    struct.i = 8;

    struct.i = 1;
    struct.i = 2;
    struct.i = 3;
    struct.i = 4;
    struct.i = 5;
    struct.i = 6;
    struct.i = 7;
    struct.i = 8;

    struct.i = 1;
    struct.i = 2;
    struct.i = 3;
    struct.i = 4;
    struct.i = 5;
    struct.i = 6;
    struct.i = 7;
    struct.i = 8;

    return struct.i;
  }

  // Code fragment after dead code elimination
  int fopt() {

    struct.i = 8;
    return struct.i;
  }
}
