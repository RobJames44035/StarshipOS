/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4135795
   @summary Make sure that System.in.available() works
            when System.in is the keyboard
   @run ignore This test requires console (/dev/tty) input, which is not
               supported by the current harness
 */


import java.io.*;

public class SystemInAvailable {

    public static void main(String[] args) throws Exception {
        byte[] b = new byte[1024];
        System.out.print("Press <enter>: ");
        System.out.flush();
        System.in.read(b);
        int a = System.in.available();
        if (a != 0) throw new Exception("System.in.available() ==> " + a);
    }

}
