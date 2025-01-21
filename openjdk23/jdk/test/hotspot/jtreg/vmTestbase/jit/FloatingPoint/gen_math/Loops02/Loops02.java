/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 *
 * @summary converted from VM Testbase jit/FloatingPoint/gen_math/Loops02.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.FloatingPoint.gen_math.Loops02.Loops02
 */

package jit.FloatingPoint.gen_math.Loops02;

// Test working with  loops and random functions.
import nsk.share.TestFailure;

public class Loops02
{

   static final int N = 300;

   public static void main (String args[])
   {


        double x;
        double r1, r2, r3;

        double rn = N;
        double dx = 1/rn;
        double Min_count = rn/2;

        Loops02 ll;
        ll = new Loops02();

        x = 0.5;
        int count = 0;
        do
        {
                r1 = Math.random();
                r2 = Math.random();
                x = x + dx * (r1 - r2);
                count++;

        } while(x > 0 && x < 1);

        ll.Echeck(Min_count,count);

  }


   public void Echeck(double er, double ER)
   {

        if( er < ER)
                System.out.println("test PASS");
        else
        {
                System.out.println("expected error: " + ER);
                System.out.println("   found error: " + er);
                throw new TestFailure("test FAIL");
        }

   }





}
