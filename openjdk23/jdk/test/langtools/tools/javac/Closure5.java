/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4416605
 * @summary Incorrect order for initializers in nested class
 * @author gafter
 *
 * @compile Closure5.java
 * @run main Closure5
 */

// note that this test case was derived from 4466029, because it
// also checks other features of -target 1.4 simultaneously.
class A {
  int i = 12;
  abstract class B {
    { foo(); }
    abstract void foo();
  }
}
public class Closure5 extends A {
  int i;
  public static void main(String[] args) {
    new Closure5().new D();
  }
  class D extends B {
    int i;
    void foo() {
      if (Closure5.super.i != 12) throw new Error("4416605");
    }
  }
}
