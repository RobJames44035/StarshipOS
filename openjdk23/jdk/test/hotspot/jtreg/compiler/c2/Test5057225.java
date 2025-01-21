/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 5057225
 * @summary Remove useless I2L conversions
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 *
 * @run main/othervm -Xcomp
 *      -XX:CompileCommand=compileonly,compiler.c2.Test5057225::doload
 *      compiler.c2.Test5057225
 */

package compiler.c2;
import jdk.test.lib.Utils;

public class Test5057225 {
    static byte[]  ba = new byte[]  { -1 };
    static short[] sa = new short[] { -1 };
    static int[]   ia = new int[]   { -1 };

    static final long[] BYTE_MASKS = {
         0x0FL,
         0x7FL,  // 7-bit
         0xFFL,
    };

    static final long[] SHORT_MASKS = {
        0x000FL,
        0x007FL,  // 7-bit
        0x00FFL,
        0x0FFFL,
        0x3FFFL,  // 14-bit
        0x7FFFL,  // 15-bit
        0xFFFFL,
    };

    static final long[] INT_MASKS = {
        0x0000000FL,
        0x0000007FL,  // 7-bit
        0x000000FFL,
        0x00000FFFL,
        0x00003FFFL,  // 14-bit
        0x00007FFFL,  // 15-bit
        0x0000FFFFL,
        0x00FFFFFFL,
        0x7FFFFFFFL,  // 31-bit
        0xFFFFFFFFL,
    };

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < BYTE_MASKS.length; i++) {
            System.setProperty("value", "" + BYTE_MASKS[i]);
            loadAndRunClass(Test5057225.class.getName() + "$loadUB2L");
        }

        for (int i = 0; i < SHORT_MASKS.length; i++) {
            System.setProperty("value", "" + SHORT_MASKS[i]);
            loadAndRunClass(Test5057225.class.getName() + "$loadUS2L");
        }

        for (int i = 0; i < INT_MASKS.length; i++) {
            System.setProperty("value", "" + INT_MASKS[i]);
            loadAndRunClass(Test5057225.class.getName() + "$loadUI2L");
        }
    }

    static void check(long result, long expected) {
        if (result != expected)
            throw new InternalError(result + " != " + expected);
    }

    static void loadAndRunClass(String classname) throws Exception {
        Class cl = Class.forName(classname);
        ClassLoader apploader = cl.getClassLoader();
        ClassLoader loader
                = Utils.getTestClassPathURLClassLoader(apploader.getParent());
        Class c = loader.loadClass(classname);
        Runnable r = (Runnable) c.newInstance();
        r.run();
    }

    public static class loadUB2L implements Runnable {
        static final long MASK;
        static {
            long value = 0;
            try {
                value = Long.decode(System.getProperty("value"));
            } catch (Throwable e) {}
            MASK = value;
        }

        public void run() { check(doload(ba), MASK); }
        static long doload(byte[] ba) { return ba[0] & MASK; }
    }

    public static class loadUS2L implements Runnable {
        static final long MASK;
        static {
            long value = 0;
            try {
                value = Long.decode(System.getProperty("value"));
            } catch (Throwable e) {}
            MASK = value;
        }

        public void run() { check(doload(sa), MASK); }
        static long doload(short[] sa) { return sa[0] & MASK; }
    }

    public static class loadUI2L implements Runnable {
        static final long MASK;
        static {
            long value = 0;
            try {
                value = Long.decode(System.getProperty("value"));
            } catch (Throwable e) {}
            MASK = value;
        }

        public void run() { check(doload(ia), MASK); }
        static long doload(int[] ia) { return ia[0] & MASK; }
    }
}
