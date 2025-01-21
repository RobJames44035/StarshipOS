/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/Robert.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.Robert.Robert
 */

package jit.Robert;

import java.io.*;
import nsk.share.TestFailure;

public class Robert
   {
   Robert()
      throws Exception
      {
      try{
         testSoftwareException();
         }
      catch (Exception e)
         {
         throw e;
         }
      }

   static void
   testHardwareException()
      throws Exception
      {
      int i = 1; int j = 0; int k = i / j;
      System.out.println(k);
      }

   static void
   testSoftwareException()
      throws Exception
      {
      Float f = Float.valueOf("abc");
      System.out.println(f);
      }

   static void
   testUserException()
      throws Exception
      {
      throw new IOException();
      }

   static void
   testRethrownException()
      throws Exception
      {
      new Robert();
      }

   static void
   trouble(int choice)
      throws Exception
      {
      if (choice == 2) testSoftwareException();
      if (choice == 3) testUserException();
      if (choice == 4) testRethrownException();
      }

   public static void main(String args[])
      throws Exception
      {
      boolean failed = false;
      System.out.println("Robert");
      for (int i = 2; i <= 4; ++i)
         {
         System.out.println("test " + i);
         try{
            trouble(i);
            }
         catch (Exception e)
            {
            System.out.println("caught " + e);
            e.printStackTrace(System.out);
            continue;
            }
         failed = true;
         }
      if (failed)
         throw new TestFailure("Test failed.");
      else
         System.out.println("Test passed.");

      }
   }
