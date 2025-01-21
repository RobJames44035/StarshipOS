/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4065189
   @summary Check that win32 pathnames of the form "C:\\"
            can be listed by the File.list method
   @author Mark Reinhold
 */

import java.io.*;


public class DriveSlash {

    public static void main(String[] args) throws Exception {

        /* This test is only valid on win32 systems */
        if (File.separatorChar != '\\') return;

        File f = new File("c:\\");
        System.err.println(f.getCanonicalPath());
        String[] fs = f.list();
        if (fs == null) {
            throw new Exception("File.list returned null");
        }
        for (int i = 0; i < fs.length; i++) {
            System.err.print(" " + fs[i]);
        }
        System.err.println();
    }

}
