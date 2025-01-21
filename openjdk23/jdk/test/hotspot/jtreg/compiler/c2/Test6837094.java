/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * @test
 * @bug 6837094
 * @summary False positive for "meet not symmetric" failure
 *
 * @run main/othervm -Xbatch
 *    -XX:CompileCommand=compileonly,compiler.c2.Test6837094::collectIs
 *    -XX:CompileCommand=compileonly,compiler.c2.Test6837094$Factory$1::getArray
 *    -XX:CompileCommand=compileonly,compiler.c2.Test6837094$Factory$2::getArray
 *    compiler.c2.Test6837094
 */

package compiler.c2;

import java.util.HashSet;
import java.util.Set;

public class Test6837094 {

  private interface Factory<M extends Interface> {
    Factory<Child0> Zero = new Factory<Child0>() {
      public Child0[] getArray() { return new Child0[1]; }
    };

    Factory<Child1> One = new Factory<Child1>() {
      public Child1[] getArray() { return new Child1[1]; }
    };

    M[] getArray();
  }

  /**
   * C2 asserts when compiling this method. Bimorphic inlining happens at
   * getArray call site. A Phi in the catch block tries to join the meet type
   * from he inline site (Parent[]) with the type expected by CI (Interface[]).
   *
   * C2 throws an assert when it doesn't need to.
   */
  private static <I extends Interface> void collectIs(
      Factory<I> factory, Set<Interface> s) {
    for (I i : factory.getArray()) {
      try {
        s.add(i);
      } catch (Exception e) {
      }
    }
  }

  static public void main(String argv[]) {
    Set<Interface> s = new HashSet();

    for (int i = 0; i < 25000; i++) {
      collectIs(Factory.Zero, s);
      collectIs(Factory.One, s);
    }
  }

  /**
   * Establish necessary class hierarchy
   */

  static interface Interface {
  }

  static class Parent {
  }

  static class Child0 extends Parent implements Interface {
  }

  static class Child1 extends Parent implements Interface {
  }

  static class Child2 extends Parent implements Interface {
  }

}

