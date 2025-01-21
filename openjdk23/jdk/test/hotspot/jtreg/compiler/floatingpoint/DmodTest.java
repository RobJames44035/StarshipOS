/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8308966
 * @summary Add intrinsic for float/double modulo for x86 AVX2 and AVX512
 * @run main compiler.floatingpoint.DmodTest
 */

 package compiler.floatingpoint;

 import java.lang.Double;

 public class DmodTest {
   static double [] op1 = { 1.2345d, 0.0d, -0.0d, 1.0d/0.0d, -1.0d/0.0d, 0.0d/0.0d };
   static double [] op2 = { 1.2345d, 0.0d, -0.0d, 1.0d/0.0d, -1.0d/0.0d, 0.0d/0.0d };
   static double [][] res = {
      {
        0.0d,
        Double.NaN,
        Double.NaN,
        1.2345d,
        1.2345d,
        Double.NaN,
      },
      {
        0.0d,
        Double.NaN,
        Double.NaN,
        0.0d,
        0.0d,
        Double.NaN,
      },
      {
        -0.0d,
        Double.NaN,
        Double.NaN,
        -0.0d,
        -0.0d,
        Double.NaN,
      },
      {
        Double.NaN,
        Double.NaN,
        Double.NaN,
        Double.NaN,
        Double.NaN,
        Double.NaN,
      },
      {
        Double.NaN,
        Double.NaN,
        Double.NaN,
        Double.NaN,
        Double.NaN,
        Double.NaN,
      },
      {
        Double.NaN,
        Double.NaN,
        Double.NaN,
        Double.NaN,
        Double.NaN,
        Double.NaN,
      },
   };
   public static void main(String[] args) throws Exception {
     double f1, f2, f3;
     boolean failure = false;
     boolean print_failure = false;
     for (int i = 0; i < 100_000; i++) {
       for (int j = 0; j < op1.length; j++) {
         for (int k = 0; k < op2.length; k++) {
           f1 = op1[j];
           f2 = op2[k];
           f3 = f1 % f2;

           if (Double.isNaN(res[j][k])) {
             if (!Double.isNaN(f3)) {
               failure = true;
               print_failure = true;
             }
           } else if (Double.isNaN(f3)) {
             failure = true;
             print_failure = true;
           } else if (f3 != res[j][k]) {
             failure = true;
             print_failure = true;
           }

           if (print_failure) {
             System.out.println( "Actual   " + f1 + " % " + f2 + " = " + f3);
             System.out.println( "Expected " + f1 + " % " + f2 + " = " + res[j][k]);
             print_failure = false;
           }
         }
       }
     }

    if (failure) {
      throw new RuntimeException("Test Failed");
    } else {
      System.out.println("Test passed.");
    }
 }
}

