/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/wide/wide01.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.wide.wide01.wide01
 */

package jit.wide.wide01;

import nsk.share.TestFailure;

/*
     Check for intermediate results that are too wide.
     The wide.java test will fail if the result of the
     expression (f0+f24) is maintained in greater-than-double precision
     or if the result of the expression (d0+d53) is maintained in
     greater-than-double precision.
*/

strictfp public class wide01
{
   public static void main(String[] arg) {
       float  f1 = Float.MAX_VALUE;
       float  f2 = Float.MAX_VALUE;
       double d1 = Double.MAX_VALUE;
       double d2 = Double.MAX_VALUE;

       float f = f1 * f2;
       if(f == Float.POSITIVE_INFINITY)
           System.out.println("Float test PASSES.");
       else
           throw new TestFailure("Float test FAILS");

       double d = d1 * d2;
       if(d == Double.POSITIVE_INFINITY)
           System.out.println("Double test PASSES.");
       else
           throw new TestFailure("Double test FAILS");

   }
}
