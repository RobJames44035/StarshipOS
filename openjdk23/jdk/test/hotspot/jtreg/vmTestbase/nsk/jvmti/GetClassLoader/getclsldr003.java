/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetClassLoader;

import java.io.*;
import java.lang.reflect.Array;

public class getclsldr003 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("getclsldr003");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load getclsldr003 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void check(Class cls, ClassLoader cl);
    native static int getRes();

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        ClassLoader cl = getclsldr003.class.getClassLoader();
        check(getclsldr003.class, cl);
        check(new getclsldr003[1].getClass(), cl);

        try {
            TestClassLoader tcl = new TestClassLoader();
            Class c = tcl.loadClass("nsk.jvmti.GetClassLoader.getclsldr003a");
            Object a = Array.newInstance(c, 1);
            check(c, tcl);
            check(a.getClass(), tcl);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(out);
            return 2;
        }

        return getRes();
    }

    private static class TestClassLoader extends ClassLoader {
        protected Class findClass(String name) throws ClassNotFoundException {
            byte[] buf;
            try {
                InputStream in = getSystemResourceAsStream(
                    name.replace('.', File.separatorChar) + ".klass");
                if (in == null) {
                    throw new ClassNotFoundException(name);
                }
                buf = new byte[in.available()];
                in.read(buf);
                in.close();
            } catch (Exception ex) {
                throw new ClassNotFoundException(name, ex);
            }

            return defineClass(name, buf, 0, buf.length);
        }
    }
}
