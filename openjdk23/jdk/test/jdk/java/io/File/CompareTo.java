/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4131169
   @summary Test respecified compareTo method
 */

import java.io.*;


public class CompareTo {

    private static void testWin32() throws Exception {
        File f1 = new File("a");
        File f2 = new File("B");
        if (!(f1.compareTo(f2) < 0))
            throw new Exception("compareTo incorrect");
    }

    private static void testUnix() throws Exception {
        File f1 = new File("a");
        File f2 = new File("B");
        if (!(f1.compareTo(f2) > 0))
            throw new Exception("compareTo incorrect");
    }

    public static void main(String[] args) throws Exception {
        if (File.separatorChar == '\\') testWin32();
        if (File.separatorChar == '/') testUnix();
    }

}
