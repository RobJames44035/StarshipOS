/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

 /*
  @test
  @bug 4250768
  @summary tests that DataFlavor.toString() does not throw NPE
  @author prs@sparc.spb.su: area=
  @modules java.datatransfer
  @run main ToStringNullPointerTest
*/

import java.awt.datatransfer.DataFlavor;

public class ToStringNullPointerTest {

     static DataFlavor df1;

     public static void main(String[] args) {
         df1 = new DataFlavor();
         try {
             String thisDF = df1.toString();
         } catch (NullPointerException e) {
             throw new RuntimeException("Test FAILED: it still throws NPE!");
         }
     }
}

