/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/FloatingPoint/gen_math/Loops06.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.FloatingPoint.gen_math.Loops06.Loops06
 */

package jit.FloatingPoint.gen_math.Loops06;

import nsk.share.TestFailure;

public class Loops06
{

   static final int N = 20;
   static final double pi = 3.14;

   public static void main (String args[])
   {

        double Error = 0.01;
        double xx[];
        double yy[];
        double zz[];

        xx = new double[N];
        yy = new double[N];
        zz = new double[N];

        double r1, r2, r3, r4, r5;
        double rN = N;

        Loops06 ll;
        ll = new Loops06();


        for(int i = 0; i < N; i++)
        {       r1 = i;
                xx[i] = Math.sin( 2 * pi * r1 / rN);
        }
        int i=0;
        while( i < N)
        {       r2 = i;
                yy[i] = xx[i] * Math.sin(r2);
                zz[i] = xx[i] * Math.cos(r2);
                i++;
        }

        for( i = 1; i < N - 1; i++)
        {
                for(int j = 1; j < N - 1; j++)
                {       zz[0] = 0;
                        for(int k = 1; k < N - 1; k++)
                        {
                                for(int n = 1; n < N - 1; n++)
                                {       yy[0] = 0;
                                        for(int m = 1; m < N - 1; m++)
                                        {
                                                for(int l = 1; l < N - 1; l++)
                                                {
                                                        xx[i] = xx[i - 1] + xx[i+1];
                                                }
                                        }
                                }
                        }
                }
        }

        double norma_x = ll.norma(N,xx);
        double norma_y = ll.norma(N,yy);
        double norma_z = ll.norma(N,zz);

        r5 = norma_x * norma_x + norma_y * norma_y + norma_z * norma_z;
        double total_norma = Math.sqrt(r5);


        double errrr = Math.abs(total_norma - 0.83);
        ll.Echeck(errrr,Error);
   }

   public double norma(int nn, double ww[])
   {
        double nor = 0;
        double r1 = nn;
        double r2 = r1 * r1;
        double r3;

        for(int i = 0; i < nn; i++)
        {
                r3 = ww[i] * ww[i];
                nor = nor + r3;
        }
        nor = nor/r2;
        nor = Math.sqrt(nor);
        return nor;
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
