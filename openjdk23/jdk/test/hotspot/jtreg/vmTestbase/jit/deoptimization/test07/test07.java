/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/deoptimization/test07.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.deoptimization.test07.test07
 */

package jit.deoptimization.test07;

import nsk.share.TestFailure;

/*
 *      Simple recursion that should cause hotspot to deoptimize
 *      used_alot and A.foo and A.bar methods
 *      Expected result for the test is: 0
 *
 *      run with the -XX:TraceDeoptimization to observ the result.
 */

public class test07 {
  public static void main (String[] args) {
    A obj = new A();
    for (int index = 0; index < 1; index++) {
      obj.used_alot();
    }
  }
}

class A {
        static int result = 0;

        synchronized public int foo(int index, int iter) {
                if (index == 0)
                        return result;
                else if (iter <= sIteration / 2) {
                        result = bar(index) * foo(--index, iter);
                } else {
                        try {
                                // halfway through the max iterations
                                // create a B. This should cause the
                                // used_alot to be deoptimized
                                // Call b.frame for the remaining part of the recursion
                                if (b == null)
                                        b = Class.forName("jit.deoptimization.test07.B").newInstance();
                                result *= ((B)b).foo(index, iter);
                        } catch (Exception x) {
                                throw new TestFailure("Class not found: B");
                        }
                }
                return result;
        }

        // Does nothing but it will let be over written in class C, See Below
        synchronized public int bar(int index) {
                synchronized (this) {
                        for (int i=0; i<5; i++)
                                index--;
                }
                return 0;
        }

        synchronized public void used_alot() {
                int result = 1;

                for (int index = 1; index <= sIteration; index++) {
                        result *= foo(index, index);
                }

                if (result != 0) {
                        throw new TestFailure("Result: " + result);
                }
        }

        protected Object b = null;
        protected static final int sIteration = 1000;
}

class B extends A {
        //      Override foo in order to force deoptimization.
        //      Also creates and instance of class C in the last
        //      iteration in order to force A.foo to get deoptimized
        //      otherwise just do something stupid.
  synchronized public int foo(int index, int iter) {
                //      Make sure that this class was created at least
                //      halfway through the iteration. Simple sanity check
        if (iter < sIteration /2) {
                System.out.println ("class B create to early");
                System.exit(1);
        }

                if (iter == sIteration) {
                        try {
                                result = ((C)(Class.forName("jit.deoptimization.test07.C").newInstance())).foo(index,iter);
                                result *= ((C)(Class.forName("jit.deoptimization.test07.C").newInstance())).bar(index);

                        } catch (Exception x) {
                                throw new TestFailure("Class not found: C");
                        }
                } else {
                        result = bar(index);
                        if (index != 0)
                                result += foo(--index, iter);
                }
        return result;
  }
}

class C extends B {
  synchronized public int foo(int index, int iter) {
        for (int i=0; i<iter; i++)
                result = bar(i);

                try {
                        result = ((D)(Class.forName("jit.deoptimization.test07.D").newInstance())).bar(index);
                } catch (Exception x) {
                        throw new TestFailure("Class not found: D");
                }
                return result;
        }
}

class D extends C {
  synchronized public int bar(int index) {
                return 1;
        }
}
