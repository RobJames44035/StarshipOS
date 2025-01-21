/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4039597
   @summary Check that pathnames containing double-byte characters are not
            corrupted by win32 path processing
   @author Mark Reinhold
*/

import java.io.*;


public class SJIS {

    private static void rm(File f) {
        if (!f.delete()) throw new RuntimeException("Can't delete " + f);
    }

    private static void touch(File f) throws IOException {
        OutputStream o = new FileOutputStream(f);
        o.close();
    }

    public static void main(String[] args) throws Exception {

        /* This test is only valid on win32 systems
           that use the SJIS encoding */
        if (File.separatorChar != '\\') return;
        String enc = System.getProperty("file.encoding");
        if ((enc == null) || !enc.equals("SJIS")) return;

        File f = new File("\u30BD");
        if (f.exists()) rm(f);

        System.err.println(f.getCanonicalPath());
        touch(f);
        System.err.println(f.getCanonicalPath());

        rm(f);

        if (!f.mkdir()) {
            throw new Exception("Can't create directory " + f);
        }
        File f2 = new File(f, "\u30BD");
        System.err.println(f2.getCanonicalPath());
        touch(f2);
        String cfn = f2.getCanonicalPath();
        if (!(new File(cfn)).exists()) {
            throw new Exception(cfn + " not found");
        }

        File d = new File(".");
        String[] fs = d.list();
        if (fs == null) System.err.println("No files listed");
        for (int i = 0; i < fs.length; i++) {
            System.err.println(fs[i]);
        }

    }

}
