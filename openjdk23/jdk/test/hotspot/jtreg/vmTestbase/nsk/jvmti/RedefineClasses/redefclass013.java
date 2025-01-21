/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.RedefineClasses;

import java.io.*;

public class redefclass013 {

    final static int FAILED = 2;
    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("redefclass013");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load redefclass013 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int check(byte bytes[]);

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        // Read data from class
        byte[] bytes;
        String fileName =
            redefclass013.class.getName().replace('.', File.separatorChar) +
            ".class";
        try {
            ClassLoader cl = redefclass013.class.getClassLoader();
            InputStream in = cl.getSystemResourceAsStream(fileName);
            if (in == null) {
                out.println("# Class file \"" + fileName + "\" not found");
                return FAILED;
            }
            bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
        } catch (Exception ex) {
            out.println("# Unexpected exception while reading class file:");
            out.println("# " + ex);
            return FAILED;
        }

        return check(bytes);
    }
}
