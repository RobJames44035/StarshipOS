/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 6800154
 * @summary Add comments to long_by_long_mulhi() for better understandability
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 *
 * @run main/othervm -Xcomp
 *      -XX:CompileCommand=compileonly,compiler.c2.Test6800154::divcomp
 *      compiler.c2.Test6800154
 */


package compiler.c2;

import jdk.test.lib.Utils;

public class Test6800154 implements Runnable {
    static final long[] DIVIDENDS = {
        0,
        1,
        2,
        1423487,
        4444441,
        4918923241323L,
        -1,
        -24351,
        0x3333,
        0x0000000080000000L,
        0x7fffffffffffffffL,
        0x8000000000000000L
    };

    static final long[] DIVISORS = {
        1,
        2,
        17,
        12342,
        24123,
        143444,
        123444442344L,
        -1,
        -2,
        -4423423234231423L,
        0x0000000080000000L,
        0x7fffffffffffffffL,
        0x8000000000000000L
    };

    // Initialize DIVISOR so that it is final in this class.
    static final long DIVISOR;

    static {
        long value = 0;
        try {
            value = Long.decode(System.getProperty("divisor"));
        } catch (Throwable e) {
        }
        DIVISOR = value;
    }

    public static void main(String[] args) throws Exception
    {
        Class cl = Test6800154.class;
        ClassLoader apploader = cl.getClassLoader();

        // Iterate over all divisors.
        for (int i = 0; i < DIVISORS.length; i++) {
            System.setProperty("divisor", "" + DIVISORS[i]);
            ClassLoader loader
                    = Utils.getTestClassPathURLClassLoader(apploader.getParent());
            Class c = loader.loadClass(Test6800154.class.getName());
            Runnable r = (Runnable) c.newInstance();
            r.run();
        }
    }

    public void run()
    {
        // Iterate over all dividends.
        for (int i = 0; i < DIVIDENDS.length; i++) {
            long dividend = DIVIDENDS[i];

            long expected = divint(dividend);
            long result = divcomp(dividend);

            if (result != expected)
                throw new InternalError(dividend + " / " + DIVISOR + " failed: " + result + " != " + expected);
        }
    }

    static long divint(long a)  { return a / DIVISOR; }
    static long divcomp(long a) { return a / DIVISOR; }
}
