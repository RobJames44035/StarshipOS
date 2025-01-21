/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
  @test
  @bug 4212613
  @summary tests that DataFlavor(String) doesn't through Exception if no "class=" specified.
  @author prs@sparc.spb.su: area=
  @modules java.datatransfer
  @run main NoClassParameterTest
*/


import java.awt.datatransfer.DataFlavor;

public class NoClassParameterTest {
     static DataFlavor df = null;

     public static void main(String[] args) {
         boolean passed = true;
         try {
             df = new DataFlavor("application/postscript");
         } catch (ClassNotFoundException e1) {
             throw new RuntimeException("This should never happen.");
         } catch (IllegalArgumentException e2) {
             passed = false;
         }
         if (!passed)
             throw new RuntimeException("Test FAILED");
     }
}
