/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 6885584
 * @summary A particular class structure causes large allocation spike for jit
 *
 * @run main/othervm -Xbatch compiler.c2.Test6885584
 */

package compiler.c2;

public class Test6885584 {
   static private int i1;
   static private int i2;
   static private int i3;

    static int limit = Integer.MAX_VALUE - 8;

   public static void main(String args[]) {
       // Run long enough to trigger an OSR
       for(int j = 200000; j != 0; j--) {
       }

       // This must reference a field
       i1 = i2;

       // The resource leak is roughly proportional to this initial value
       for(int k = Integer.MAX_VALUE - 1; k != 0; k--) {
           // Make sure the body does some work
           if(i2 > i3)i1 = k;
           if (k <= limit) break;
       }
   }

}
