/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/* @test
 * @bug 5016938
 * @summary Test file operations with Unicode filenames
 * @author Martin Buchholz
 */

import java.io.*;

public class Unicode
{
    static int fail = 0;
    static void fail(String msg) {
        fail++;
        System.err.println(msg);
    }

    static boolean creat(File f) throws Exception {
        try {
            FileOutputStream out = new FileOutputStream(f);
            out.write(new byte[]{'a', 'b', 'c'});
            out.close();
            // Check that the file we tried to create has the expected name
            return find(f);
        } catch (Exception e) {
            return false;
        }
    }

    static boolean find(File f) throws Exception {
        String fn = f.getPath();
        String[] fns = new File(".").list();
        for (int i = 0; i < fns.length; i++)
            if (fns[i].equals(fn))
                return true;
        return false;
    }

    static void sanityCheck(File f) throws Exception {
        if (! f.exists())      fail("! f.exists()");
        if (  f.length() != 3) fail("  f.length() != 3");
        if (  f.isAbsolute())  fail("  f.isAbsolute()");
        if (! f.canRead())     fail("! f.canRead()");
        if (! f.canWrite())    fail("! f.canWrite()");
        if (  f.isHidden())    fail("  f.isHidden()");
        if (! f.isFile())      fail("! f.isFile()");
        if (  f.isDirectory()) fail("  f.isDirectory()");
    }

    public static void main(String [] args) throws Exception {
        final File f1 = new File("\u0411.tst");
        final File f2 = new File("\u0412.tst");

        try {
            if (! creat(f1))
                // Couldn't create file with Unicode filename?
                return;

            System.out.println("This system supports Unicode filenames!");
            sanityCheck(f1);

            f1.renameTo(f2);
            sanityCheck(f2);
            if (! f2.delete()) fail("! f2.delete()");
            if (  f2.exists()) fail("  f2.exists()");
            if (  f1.exists()) fail("  f1.exists()");
            if (  f1.delete()) fail("  f1.delete()");

            if (fail != 0) throw new Exception(fail + " failures");
        } finally {
            f1.delete();
            f2.delete();
        }
    }
}
