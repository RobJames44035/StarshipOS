/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/deoptimization/test02.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.deoptimization.test02.test02
 */

package jit.deoptimization.test02;

import nsk.share.TestFailure;

/*
 *      Simple loop causes the optimizer to deoptimize
 *      foo methos when an instance of class B is created.
 *
 *      run with the -XX:TraceDeoptimization to observ the result.
 */

public class test02 {
  public static void main (String[] args) {
    A obj = new A();
    for (int index = 0; index < 100; index++) {
      obj.used_alot();
    }
  }
}

class A {
        protected final int counter = 25000;
        protected int count = 0;
        public void foo(int index) {
                count++;

                if (index == counter - 1) {
                        try {
                                ((B)Class.forName("B").newInstance()).foo(index);
                        }
                        catch(Exception e) {
                        }
                }
        }

        public synchronized void used_alot() {
                for (int index = 0; index < counter; index++) {
                        foo(index);
                }
        }
}

class B extends A {
  public void foo(int index) {
    count--;
  }
};
